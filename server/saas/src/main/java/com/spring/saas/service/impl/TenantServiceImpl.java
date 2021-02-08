package com.spring.saas.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.base.dao.BaseDao;
import com.spring.base.entity.saas.ApplicationEntity;
import com.spring.base.entity.saas.OrderEntity;
import com.spring.base.entity.saas.TenantEntity;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.base.vo.AppVo;
import com.spring.base.vo.TokenVo;
import com.spring.base.vo.baseinfo.company.AddSaasVo;
import com.spring.base.vo.baseinfo.user.LoginVo;
import com.spring.base.vo.saas.*;
import com.spring.common.util.ConvertUtils;
import com.spring.common.constants.Constants;
import com.spring.common.constants.MessageCode;
import com.spring.common.constants.SaasConstants;
import com.spring.common.enums.TenantStatusEnum;
import com.spring.common.exception.ServiceException;
import com.spring.common.feign.client.BaseInfoFeignClient;
import com.spring.common.page.RequestPageVO;
import com.spring.common.redis.RedisCacheUtils;
import com.spring.common.request.RequestUtils;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.token.SignToken;
import com.spring.common.util.aes.AesEncryptUtil;
import com.spring.common.util.date.DateTime;
import com.spring.common.util.id.UUIDFactory;
import com.spring.common.util.md5.Md5Util;
import com.spring.saas.dao.IApplicationDao;
import com.spring.saas.dao.IOrderDao;
import com.spring.saas.dao.ITenantDao;
import com.spring.saas.service.IOrderService;
import com.spring.saas.service.ITenantService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-01 17:10:11
 * @Desc类说明: 租户信息业务接口实现类
 */

@Slf4j
@Service("tenantService")
public class TenantServiceImpl extends BaseServiceImpl<TenantEntity, String> implements ITenantService {

    @Autowired
    private BaseInfoFeignClient baseInfoFeignClient;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private ITenantDao tenantDao;

    @Autowired
    private IApplicationDao applicationDao;

    @Autowired
    private IOrderDao orderDao;

    @Override
    public BaseDao getBaseMapper() {
        return tenantDao;
    }

    @Autowired
    private RedisCacheUtils redisUtils;

    /**
     * 新增租户信息
     *
     * @param vo
     * @return
     * @throws Exception
     * @author 作者：ZhaoJinHua
     * @version 创建时间：2020-07-01 17:10:11
     */
    @Override
    public ApiResponseResult registerTenant(TenantAddVo vo) throws Exception {
        // 返回的对象
        ApiResponseResult result = new ApiResponseResult();
        TenantEntity entity = new TenantEntity();
        BeanUtils.copyProperties(vo, entity);
        entity.setId(UUIDFactory.createId());
        entity.setPassword(Md5Util.md5Encode(vo.getPassword()));
        entity.setRegisterTime(LocalDateTime.now());
        entity.setTenantCode("PCID" + System.currentTimeMillis() + (long) ((Math.random() + 1) * 1000));
        entity.setCreateUser(RequestUtils.getUserId());
        entity.setCreateDate(new Date());
        // 新增
        int no = tenantDao.insert(entity);
        if (no > 0) {
            result.setCode(MessageCode.SUCCESS);
            result.setMsg("成功");
        } else {
            result.setCode(MessageCode.FAIL);
            result.setMsg("新增失败");
        }
        return result;
    }

    /**
     * @return com.spring.common.response.ApiResponseResult
     * @Author 熊锋
     * @Description: 查询租户列表（不分页）
     * @Date 2020/7/1 18:43
     * @Param [vo]
     */
    @Override
    public ApiResponseResult queryTenantList(TenantQueryVo vo) throws Exception {

        return createSuccessResult(tenantDao.queryTenantList(vo));
    }

    /**
     * @return com.spring.common.response.ApiResponseResult
     * @Author 熊锋
     * @Description: 查询租户列表（分页）
     * @Date 2020/7/1 18:43
     * @Param [vo]
     */
    @Override
    public ApiResponseResult queryTenantList(RequestPageVO<TenantQueryVo> requestPageVO) throws Exception {
        // 设置分页参数
        PageHelper.startPage(requestPageVO.getCurrentPage(), requestPageVO.getPageSize(), true);
        // 查询租户信息
        List<TenantEntity> tenantList = tenantDao.queryTenantList(requestPageVO.getEntity());
        // 转换Object
        List<TenantPageVo> tenantPageVoList = ConvertUtils.copyPropertiesList(tenantList, TenantPageVo.class);

        if (CollectionUtils.isNotEmpty(tenantPageVoList)) {
            // 获取租户Id
            List<String> tenantIdList = tenantPageVoList.stream().map(TenantPageVo::getId).collect(Collectors.toList());
            // 按租户Id查询租户应用关系
            List<TenantAppVo> tenantAppQueryVoList = orderService.queryTenantAppRelationByTenantIds(tenantIdList);
            if (CollectionUtils.isNotEmpty(tenantAppQueryVoList)) {
                // List转换Map
                Map<String, List<TenantAppVo>> tenantAppQueryVoMap = convertTenantAppList2Map(tenantAppQueryVoList);

                // 绑定租户与租户应用关系
                for (TenantPageVo vo : tenantPageVoList) {
                    if (tenantAppQueryVoMap.containsKey(vo.getId())) {
                        vo.setTenantAppVoList(tenantAppQueryVoMap.get(vo.getId()));
                    }
                }
            }
        }
        PageInfo<TenantPageVo> pageInfo = new PageInfo<>(tenantPageVoList);
        return createSuccessResult(pageInfo);
    }

    /**
     * 租户应用信息List To Map
     *
     * @param tenantAppQueryVoList
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @date 2020/7/9 15:43
     */
    private Map<String, List<TenantAppVo>> convertTenantAppList2Map(List<TenantAppVo> tenantAppQueryVoList) throws Exception {
        Map<String, List<TenantAppVo>> tenantAppQueryVoMap = new HashMap<>(16);
        for (TenantAppVo vo : tenantAppQueryVoList) {
            if (tenantAppQueryVoMap.containsKey(vo.getTenantId())) {
                tenantAppQueryVoMap.get(vo.getTenantId()).add(vo);
            } else {
                tenantAppQueryVoMap.put(vo.getTenantId(), new ArrayList<>());
                tenantAppQueryVoMap.get(vo.getTenantId()).add(vo);
            }
        }
        return tenantAppQueryVoMap;
    }

    /**
     * @return com.spring.common.response.ApiResponseResult
     * @Author 熊锋
     * @Description: 根据id查询租户详细信息
     * @Date 2020/7/1 18:43
     * @Param [vo]
     */
    @Override
    public ApiResponseResult queryTenantDetail(String id) throws Exception {
        ApiResponseResult result = new ApiResponseResult();
        TenantResponseVo tenantResponseVo = tenantDao.queryTenantDetail(id);
        if (tenantResponseVo != null) {
            result.setData(tenantResponseVo);
            result.setCode(MessageCode.SUCCESS);
            result.setMsg(MessageCode.SUCCESS_TEXT);
        } else {
            result.setCode(MessageCode.FAIL);
            result.setMsg(MessageCode.FAIL_TEXT);
            result.setData(null);
        }
        return result;
    }

    /**
     * @description:检测手机号码是否则注册
     * @return:
     * @author: 赵进华
     * @time: 2020/7/3 11:22
     */
    @Override
    public ApiResponseResult checkMobileExist(String mobile) {
        ApiResponseResult result = new ApiResponseResult();
        QueryWrapper<TenantEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", mobile);
        // 根据手机号码查租户
        List<TenantEntity> tenantList = tenantDao.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(tenantList)) {
            result.setCode(MessageCode.FAIL);
            result.setMsg("该手机号码已注册！");
        } else {
            result.setCode(MessageCode.SUCCESS);
            result.setMsg(MessageCode.SUCCESS_TEXT);
        }
        return result;
    }

    /**
     * @description:租户登录
     * @return:
     * @author: 赵进华
     * @time: 2020/7/3 15:44
     */
    @Override
    public ApiResponseResult tenantLogin(LoginVo loginVo) throws Exception {
        log.info("tenant login begin vo:" + loginVo.toString());
        // 返回的对象
        ApiResponseResult result = new ApiResponseResult();
        //进行解码,userNameBase64是你获得的加密后的用户名,下面进行解密
        String secretString = loginVo.getSecretString();
        byte[] userNameByte = Base64.decodeBase64(secretString.getBytes(StandardCharsets.UTF_8));
        String json = new String(userNameByte);
        // json字符串返序列化成对象
        AppVo vo = JSON.parseObject(json, AppVo.class);
        if (vo == null) {
            result.setCode(MessageCode.FAIL);
            result.setMsg(MessageCode.FAIL_TEXT);
            return result;
        }

        // 存redis中Token的key
        String tokenKey = Constants.RedisPrefix.TOKEN + vo.getAppId();

        // 用户名
        String userCode = vo.getAppId();
        // 密码
        String password = Md5Util.md5Encode(vo.getAppPassword());
        log.info("tenant login search user userCode:" + userCode + ",password:" + password);
        // 根据用户id和密码查询是否正确
        LoginTenantVo user = tenantDao.getUserByUserIdPassword(userCode, password);
        if (user != null) {
            log.info("user login user is not null:" + user.toString());
            //租户注册不审核不允许登录
            if (user.getTenantStatus() == SaasConstants.TenantType.CHECK_PEND) {
                result.setCode(MessageCode.FAIL);
                result.setMsg("帐号未审核，请等待！");
                return result;
            }

            // Token中包含信息的map
            Map<String, Object> claims = new HashMap<String, Object>();

            //Token中放用户id
            if (StringUtils.isEmpty(user.getUserId())) {
                claims.put(Constants.Token.USER_ID, "");
            } else {
                claims.put(Constants.Token.USER_ID, user.getUserId());
            }
            claims.put(Constants.Token.USER_CODE, user.getUserId());
            claims.put(Constants.Token.USER_TYPE, "saas");
            claims.put(Constants.Token.TENANT_ID, user.getTenantId());
            claims.put(Constants.Token.COMPANY_ID, "");
            claims.put(Constants.Token.COMMUNITY_ID, "");
            claims.put(Constants.Token.IS_SAAAS, "");
            // Token中包含时间,时间在变化，保证每次生成的Token不一样
            claims.put(Constants.Token.DATE, new Date());
            // 生成Token
            String token = SignToken.createJavaWebToken(claims);
            // 存的Token对象
            TokenVo tokenVo = new TokenVo();
            tokenVo.setUserId(vo.getAppId());
            tokenVo.setUserCode(vo.getAppId());
            tokenVo.setToken(token);
            tokenVo.setTokenDate(new Date());
            tokenVo.setUserName(user.getUserName());

            // 先删除key
            redisUtils.del(tokenKey);
            String userInformation = JSON.toJSONString(tokenVo);
            // Token存redis,设置过期时间
            redisUtils.set(tokenKey, userInformation, Constants.RedisExpire.TOKTN_EXPIRE);
            user.setUserCode(user.getUserId());
            user.setToken(token);

            result.setCode(MessageCode.SUCCESS);
            result.setMsg("成功");
            result.setData(user);
        } else {
            result.setCode(MessageCode.FAIL);
            result.setMsg("帐号密码不正确");
        }
        return result;
    }

    /**
     * @description:租户单独登录
     * @return:
     * @author: 赵进华
     * @time: 2020/7/3 15:44
     */
    @Override
    public ApiResponseResult tenantLoginAlone(LoginVo loginVo) throws Exception {
        log.info("tenant login begin vo:" + loginVo.toString());
        // 返回的对象
        ApiResponseResult result = new ApiResponseResult();
        // AES解密成json字符串
        String json = AesEncryptUtil.desEncrypt(loginVo.getSecretString()).trim();
        // json字符串返序列化成对象
        AppVo vo = JSON.parseObject(json, AppVo.class);
        if (vo == null) {
            result.setCode(MessageCode.FAIL);
            result.setMsg(MessageCode.FAIL_TEXT);
            return result;
        }

        // 存redis中Token的key
        String tokenKey = Constants.RedisPrefix.TOKEN + vo.getAppId();

        // 用户名
        String userCode = vo.getAppId();
        // 密码
        String password = Md5Util.md5Encode(vo.getAppPassword());
        log.info("tenant login search user userCode:" + userCode + ",password:" + password);
        // 根据用户id和密码查询是否正确
        LoginTenantVo user = tenantDao.getUserByUserIdPassword(userCode, password);
        if (user != null) {
            log.info("user login user is not null:" + user.toString());
            //租户注册不审核不允许登录
            if (user.getTenantStatus() == SaasConstants.TenantType.CHECK_PEND) {
                result.setCode(MessageCode.FAIL);
                result.setMsg("帐号未审核，请等待！");
                return result;
            }

            // Token中包含信息的map
            Map<String, Object> claims = new HashMap<String, Object>();

            //Token中放用户id
            if (StringUtils.isEmpty(user.getUserId())) {
                claims.put(Constants.Token.USER_ID, "");
            } else {
                claims.put(Constants.Token.USER_ID, user.getUserId());
            }
            claims.put(Constants.Token.USER_CODE, user.getUserId());
            claims.put(Constants.Token.USER_TYPE, "saas");
            claims.put(Constants.Token.TENANT_ID, user.getTenantId());
            claims.put(Constants.Token.COMPANY_ID, "");
            claims.put(Constants.Token.COMMUNITY_ID, "");
            claims.put(Constants.Token.IS_SAAAS, "");
            // Token中包含时间,时间在变化，保证每次生成的Token不一样
            claims.put(Constants.Token.DATE, new Date());
            // 生成Token
            String token = SignToken.createJavaWebToken(claims);
            // 存的Token对象
            TokenVo tokenVo = new TokenVo();
            tokenVo.setUserId(vo.getAppId());
            tokenVo.setUserCode(vo.getAppId());
            tokenVo.setToken(token);
            tokenVo.setTokenDate(new Date());
            tokenVo.setUserName(user.getUserName());

            // 先删除key
            redisUtils.del(tokenKey);
            String userInformation = JSON.toJSONString(tokenVo);
            // Token存redis,设置过期时间
            redisUtils.set(tokenKey, userInformation, Constants.RedisExpire.TOKTN_EXPIRE);

            user.setToken(userInformation);

            result.setCode(MessageCode.SUCCESS);
            result.setMsg("成功");
            result.setData(user);
        } else {
            result.setCode(MessageCode.FAIL);
            result.setMsg("帐号密码不正确");
        }
        return result;
    }

    @Override
    public ApiResponseResult auditTenant(TenantAuditVo vo) throws Exception {
        // 返回的对象
        ApiResponseResult result = new ApiResponseResult();
        TenantEntity entity = tenantDao.selectById(vo.getId());
        if (entity != null) {
            //获取租户试用版应用id
            String appTypeId = "";
            QueryWrapper<ApplicationEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("app_type", 1);
            // 根据用户id和密码查询是否正确
            List<ApplicationEntity> appList = applicationDao.selectList(queryWrapper);
            if (CollectionUtils.isNotEmpty(appList)) {
                ApplicationEntity applicationEntity = appList.get(0);
                if (applicationEntity != null) {
                    appTypeId = applicationEntity.getId();
                } else {
                    result.setCode(MessageCode.FAIL);
                    result.setMsg("没有设置应用试用版!");
                    return result;
                }
            }
            entity.setTenantStatus(vo.getStatus());
            //设置租户应用为试用版应用
            entity.setApplicationId(appTypeId);
            entity.setAuditTime(LocalDateTime.now());
            int no = tenantDao.updateById(entity);
            if (no > 0) {
                //往订单表插入一条记录
                //生成订单号
                String orderNum = DateTime.createOderNum();
                OrderEntity order = new OrderEntity();
                order.setId(UUIDFactory.createId());
                order.setOrderNum(orderNum);
                order.setBuyNum(0);
                order.setBuyTime(1);
                order.setAppId(appTypeId);
                order.setOrderType(1);
                order.setOrderStatus(1);
                order.setDescription("试用版应用");
                order.setTenantId(RequestUtils.getTenantId());
                order.setMakeInvoiceStatus(4);
                order.setOrderDate(new Date());
                order.setCreateUser(RequestUtils.getUserId());
                order.setCreateDate(new Date());
                //往订单表插入数据
                orderDao.insert(order);

                //调用base微服务创建租户,同时创建管理员，绑定试用角色
                AddSaasVo tenantVo = new AddSaasVo();
                tenantVo.setId(entity.getId());
                tenantVo.setCompanyCode(entity.getTenantCode());
                tenantVo.setCompanyName(entity.getCompanyName());
                tenantVo.setAddress(entity.getAddress());
                tenantVo.setMobile(entity.getMobile());
                tenantVo.setContactPeople(entity.getContactPeople());
                baseInfoFeignClient.addTenant(tenantVo);

                result.setCode(MessageCode.SUCCESS);
                result.setMsg("成功");
            } else {
                result.setCode(MessageCode.FAIL);
                result.setMsg(MessageCode.FAIL_TEXT);
            }
        }
        return result;
    }

    /**
     * @return com.spring.common.response.ApiResponseResult
     * @throws Exception
     * @author 熊锋
     * @date 2020/7/6 17:32
     * @description: 查询个人账户信息
     */
    @Override
    public ApiResponseResult queryMyApplication(String mobile) throws Exception {
        QueryWrapper<TenantEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("del_flag", 0).eq("mobile", mobile);
        TenantEntity entity = tenantDao.selectOne(queryWrapper);
        List<ApplicationVo> list = applicationDao.queryMyApplication(mobile);
        List<ApplicationVo> collect = new ArrayList<>();
        if (CollectionUtils.isEmpty(list) || entity == null) {
            return createSuccessResult(null);
        }
        if (entity.getTenantStatus().equals(TenantStatusEnum.TRY_OUT.getEnumCode())) {
            collect = list.stream().filter(e -> e.getOrderStatus() == 5).collect(Collectors.toList());
            collect.stream().forEach(e->{
                e.setAppStatus(new Integer[]{2});
            });
        }
        if (entity.getTenantStatus().equals(TenantStatusEnum.ENABLE.getEnumCode())) {
            collect = list.stream().filter(e -> e.getOrderStatus() == 2).collect(Collectors.toList());
            collect.stream().forEach(e->{
                e.setAppStatus(new Integer[]{3,4});
            });
        }
        return createSuccessResult(collect);
    }

    /**
     * @param mobile
     * @return com.spring.common.response.ApiResponseResult
     * @throws Exception
     * @author 熊锋
     * @date 2020/7/6 17:31
     * @description: 查询基本资料
     */
    @Override
    public ApiResponseResult queryBasicData(String mobile) throws Exception {
        ApiResponseResult result = new ApiResponseResult();
        QueryWrapper<TenantEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", mobile).eq("del_flag", 0);
        //根据手机号查询对应租户的唯一id
        TenantEntity tenantEntity = tenantDao.selectOne(queryWrapper);
        String id = tenantEntity.getId();
        //根据id查询租户资料
        return queryTenantDetail(id);
    }

    /**
     * 更新租户状态
     *
     * @param tenantId
     * @param tenantStatus
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @date 2020/7/13 19:27
     */
    @Override
    public ApiResponseResult updateTenantStatus(String tenantId, Integer tenantStatus) throws Exception {
        if (StringUtils.isBlank(tenantId)) {
            throw new ServiceException("租户Id不能为空");
        }

        if (tenantStatus == null) {
            throw new ServiceException("租户状态不能为空");
        }

        TenantEntity tenant = tenantDao.selectById(tenantId);
        if (tenant == null) {
            throw new ServiceException("没有找到相关的租户信息");
        }

        // 发票状态
        tenant.setTenantStatus(tenantStatus);
        // 更新用户
        tenant.setModifyUser(RequestUtils.getUserId());
        // 更新时间
        tenant.setModifyDate(new Date());

        Integer retStatus = tenantDao.updateById(tenant);

        return retStatus > 0 ? createSuccessResult(null) : createFailResult();
    }

    /**
     * 查询租户主应用信息
     *
     * @param mobile
     * @return com.spring.common.response.ApiResponseResult
     * @throws Exception
     * @author 熊锋
     * @date 2020/7/17 14:19
     */
    @Override
    public ApiResponseResult queryMainApplication(String mobile) throws Exception {
        QueryWrapper<TenantEntity> queryWrapper = new QueryWrapper<>();
        MyAccountVo myAccountVo=new MyAccountVo();
        queryWrapper.eq("del_flag", 0).eq("mobile", mobile);
        TenantEntity entity = tenantDao.selectOne(queryWrapper);
        if (entity!=null){
            if (entity.getTenantStatus().equals(TenantStatusEnum.TRY_OUT.getEnumCode())){

            }
        }
        return null;
    }
}
