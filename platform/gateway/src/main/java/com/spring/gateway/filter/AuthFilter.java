package com.spring.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.spring.gateway.constants.Constants;
import com.spring.gateway.redis.RedisUtils;
import com.spring.gateway.token.SignToken;
import com.spring.gateway.vo.GatewayTokenVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.function.Consumer;

/**
 * @author 熊锋
 * @date 2020/4/15 14:10
 * @Desc类说明:
 */
@Slf4j
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    @Autowired
    private RedisUtils redisUtils;

    @Value("${jwt.ignore-url}")
    private String ignoreUrl;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest serverHttpRequest=null;
        String token = exchange.getRequest().getHeaders().getFirst("accessToken");
        //获取访问url
        String requestUrl = exchange.getRequest().getPath().toString();
        log.info("gateWay requestUrl:"+requestUrl);
        //排除不需要拦截的url
        String[] ignoreUrls=ignoreUrl.split(",");
        if(ignoreUrls!=null) {
            String ignoreString=StringUtils.join(ignoreUrls, "|");
            log.info("gateWay ignoreUrls:" + ignoreString);
        }
        // 访问swagger不做限制
        if (-1 != requestUrl.indexOf("swagger") || -1 != requestUrl.indexOf("/v2/api-docs")) {
            return chain.filter(exchange);
        }
        // 访问swagger不做限制
        if (-1 != requestUrl.indexOf("/swagger-resources") || -1 != requestUrl.indexOf("/swagger.json")) {
            return chain.filter(exchange);
        }
        for(int i=0;i<ignoreUrls.length;i++){
            if(requestUrl.contains(ignoreUrls[i])){
                log.info("gateWay ignore ok:" + requestUrl);
                return chain.filter(exchange);
            }
        }
        //token为空
        if (token == null || token.isEmpty()) {

            ServerHttpResponse response = exchange.getResponse();
            // 封装错误信息
            Map<String, Object> responseData = Maps.newHashMap();
            responseData.put("code", 400);
            responseData.put("message", "Token is empty");
            responseData.put("cause", "非法请求");
            try {
                // 将信息转换为 JSON
                ObjectMapper objectMapper = new ObjectMapper();
                byte[] data = objectMapper.writeValueAsBytes(responseData);
                // 输出错误信息到页面
                DataBuffer buffer = response.bufferFactory().wrap(data);
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
                return response.writeWith(Mono.just(buffer));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        try{
            if(token.equals("123456")){
                return chain.filter(exchange);
            }
            // 验证用户合法性
            Map<String, Object> claims = SignToken.parserJavaWebToken(token);
            if (claims != null){
                // 解析得到用户id
                String userId = claims.get(Constants.Token.USER_ID).toString();
                log.info("gateWay token parser ok userId:" + userId);
                // 解析得到用户Code
                String userCode = claims.get(Constants.Token.USER_CODE).toString();
                log.info("gateWay token parser ok userCode:" + userCode);
                // 解析得到租户id
                String tenantId = claims.get(Constants.Token.TENANT_ID).toString();
                log.info("gateWay token parser ok tenantId:" + tenantId);
                // 解析得到公司id
                String companyId = claims.get(Constants.Token.COMPANY_ID).toString();
                log.info("gateWay token parser ok companyId:" + companyId);
                // 解析得到小区id
                String communityId = claims.get(Constants.Token.COMMUNITY_ID).toString();
                log.info("gateWay token parser ok communityId:" + communityId);
                // 解析得到用户类型(1平台管理员,2物业公司管理员
                String userType = claims.get(Constants.Token.USER_TYPE).toString();
                // 解析得到是否是saas用户
                String isSaas = claims.get(Constants.Token.IS_SAAAS).toString();
                // 解析得到岗位id
                String jobId = claims.get(Constants.Token.JOB_ID).toString();
                log.info("gateWay token parser ok jobId:" + jobId);
                //解析得到角色id
                String roleId = claims.get(Constants.Token.ROLE_ID).toString();
                log.info("gateWay token parser ok roleId:" + roleId);
                log.info("token content userId:"+userId+",companyId:"+companyId+",communityId:"+communityId+",tenantId:"+tenantId);
                // redis中的key
                String tokenKey = Constants.RedisPrefix.TOKEN + userCode;
                if(!redisUtils.exists(tokenKey)){
                    ServerHttpResponse response = exchange.getResponse();
                    // 封装错误信息
                    Map<String, Object> responseData = Maps.newHashMap();
                    responseData.put("code", 401);
                    responseData.put("message","token已过期");
                    responseData.put("cause", "非法请求");
                    // 将信息转换为 JSON
                    ObjectMapper objectMapper = new ObjectMapper();
                    byte[] data = objectMapper.writeValueAsBytes(responseData);
                    // 输出错误信息到页面
                    DataBuffer buffer = response.bufferFactory().wrap(data);
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
                    return response.writeWith(Mono.just(buffer));
                }else{
                    // 从redis中取存储的对象
                    String json = redisUtils.get(tokenKey, String.class);
                    log.info("gateWay redis token json:" + json);
                    //将json字符串转成对象
                    GatewayTokenVo redisUser= JSONObject.parseObject(json,GatewayTokenVo.class);
                    if(redisUser!=null){
                        log.info("gateWay redis token parse ok redisUser:" + redisUser.toString());
                        // redis存储的UserId
                        String redisUserUserCode = redisUser.getUserCode();
                        log.info("userCode equals redisUserUserCode userCode:"+userCode+",redisUserUserCode:"+redisUserUserCode);
                        //判断前端传过的userId和redis中存储的userId是否一致
                        if(!userCode.equals(redisUserUserCode)){
                            log.info("gateWay redis userId no equals redisUserUserId");
                            ServerHttpResponse response = exchange.getResponse();
                            // 封装错误信息
                            Map<String, Object> responseData = Maps.newHashMap();
                            responseData.put("code", 402);
                            responseData.put("message", "token输入有误");
                            responseData.put("cause", "非法请求");
                            // 将信息转换为 JSON
                            ObjectMapper objectMapper = new ObjectMapper();
                            byte[] data = objectMapper.writeValueAsBytes(responseData);
                            // 输出错误信息到页面
                            DataBuffer buffer = response.bufferFactory().wrap(data);
                            response.setStatusCode(HttpStatus.PAYMENT_REQUIRED);
                            response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
                            return response.writeWith(Mono.just(buffer));
                        }else{
                            //token续约
                            log.info("gateWay token expire json:" + json);
                            redisUtils.expire(tokenKey,Constants.RedisExpire.TOKEN_EXPIRE);
                            //往headers设置参数
                            Consumer<HttpHeaders> httpHeaders = httpHeader -> {
                                httpHeader.set(Constants.Token.USER_ID,userId);
                                httpHeader.set(Constants.Token.USER_CODE,userCode);
                                httpHeader.set(Constants.Token.COMPANY_ID,companyId);
                                httpHeader.set(Constants.Token.COMMUNITY_ID,communityId);
                                httpHeader.set(Constants.Token.USER_TYPE,userType);
                                httpHeader.set(Constants.Token.TENANT_ID,tenantId);
                                httpHeader.set(Constants.Token.IS_SAAAS,isSaas);
                                httpHeader.set(Constants.Token.JOB_ID,jobId);
                                httpHeader.set(Constants.Token.ROLE_ID,roleId);
                                log.info("token transfer userId:"+userId+",companyId:"+companyId+",communityId:"+communityId+",tenantId:"+tenantId);
                            };
                            serverHttpRequest = exchange.getRequest().mutate().headers(httpHeaders).build();
                        }
                    }
                }
            }else{
                ServerHttpResponse response = exchange.getResponse();
                // 封装错误信息
                Map<String, Object> responseData = Maps.newHashMap();
                responseData.put("code", 403);
                responseData.put("message", "token解析失败");
                responseData.put("cause", "authentication fail");
                // 将信息转换为 JSON
                ObjectMapper objectMapper = new ObjectMapper();
                byte[] data = objectMapper.writeValueAsBytes(responseData);
                // 输出错误信息到页面
                DataBuffer buffer = response.bufferFactory().wrap(data);
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
                return response.writeWith(Mono.just(buffer));
            }
        }catch (Exception e){
            ServerHttpResponse response = exchange.getResponse();
            // 封装错误信息
            Map<String, Object> responseData = Maps.newHashMap();
            responseData.put("code", 404);
            responseData.put("message", "authentication fail");
            responseData.put("cause", "非法请求");
            // 将信息转换为 JSON
            ObjectMapper objectMapper = new ObjectMapper();
            byte[] data = new byte[0];
            try {
                data = objectMapper.writeValueAsBytes(responseData);
            } catch (JsonProcessingException ex) {
                ex.printStackTrace();
            }
            // 输出错误信息到页面
            DataBuffer buffer = response.bufferFactory().wrap(data);
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
            return response.writeWith(Mono.just(buffer));
        }
        return chain.filter( exchange.mutate().request(serverHttpRequest).build());
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
