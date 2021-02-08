package com.spring.common.feign;

import com.spring.common.constants.Constants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:feign上下文参数的传递配置
 * @return:
 * @author: 赵进华
 * @time: 2020/7/2 11:25
 */  
@Configuration
public class FeignConfiguration implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        if(attributes!=null){
            HttpServletRequest request = attributes.getRequest();
            String token = request.getHeader(Constants.Token.ACCESS_TOKEN);
            //传递token
            requestTemplate.header(Constants.Token.ACCESS_TOKEN, token);
            //传递用户id
            requestTemplate.header(Constants.Token.USER_ID, request.getHeader(Constants.Token.USER_ID));
            //传递公司id
            requestTemplate.header(Constants.Token.COMPANY_ID, request.getHeader(Constants.Token.COMPANY_ID));
            //传递小区id
            requestTemplate.header(Constants.Token.COMMUNITY_ID, request.getHeader(Constants.Token.COMMUNITY_ID));
            //传递租户id
            requestTemplate.header(Constants.Token.TENANT_ID, request.getHeader(Constants.Token.TENANT_ID));
        }
    }
}