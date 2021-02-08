package com.spring.common.request;

import com.alibaba.fastjson.JSON;
import com.spring.base.dto.RedisInfoDto;
import com.spring.common.constants.Constants;
import com.spring.common.redis.RedisCacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author 作者：赵进华
 * @version 创建时间：2019年5月26日 下午2:10:32
 * @Desc类说明:Request工具类
 */
@Component
public class RequestUtils {

    private static RequestUtils requestUtils;

    @Autowired
    private RedisCacheUtils redisUtils;

    /**
     * 初始化mongoTemplateStatic
     */
    @PostConstruct
    public void init() {
        requestUtils = this;
    }
    /**
     * 获取Request
     *
     * @return
     * @author 作者：熊锋
     * @version 创建时间：2019年5月26日 下午2:39:35
     */
    public static HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        return request;
    }

    /**
     * 获取Session
     *
     * @return
     * @author 作者：熊锋
     * @version 创建时间：2019年5月26日 下午2:39:52
     */
    public static HttpSession getSession() {
        HttpSession session = getRequest().getSession();
        return session;
    }

    /**
     * 从Request中获取用户id
     * @return
     * @author 作者：熊锋
     * @version 创建时间：2019年5月26日 下午2:40:04
     */
    public static String getUserId() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String userId="";
        Object obj = request.getHeader(Constants.Token.USER_ID);
        if (obj != null) {
            userId = (String) obj;
        }
        return userId;
    }

    /**
     * 从Request中获取用户名(header取出来会乱码 从redis中取)
     * @return
     * @author 作者：熊锋
     * @version 创建时间：2019年5月26日 下午2:40:04
     */
    public static String getUserName() {
        String userName="";
        String json=requestUtils.redisUtils.get("token-"+RequestUtils.getUserCode(),String.class);
        RedisInfoDto redisInfoDto= JSON.parseObject(json,RedisInfoDto.class);
        if (redisInfoDto!=null){
            userName=redisInfoDto.getUserName();
        }
        return userName;
    }

    /**
     * @description:从Request中获取用户Code
     * @return:
     * @author: 熊锋
     * @time: 2020/7/17 10:32
     */
    public static String getUserCode() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String userCode="";
        Object obj = request.getHeader(Constants.Token.USER_CODE);
        if (obj != null) {
            userCode = (String) obj;
        }
        return userCode;
    }

    /**
     * 从Request中获取用户类型(1平台管理员,2物业公司管理员
     * @return
     * @author 作者：熊锋
     * @version 创建时间：2019年6月12日 下午4:49:38
     */
    public static String getUserType() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String userType = "";
        Object obj = request.getHeader(Constants.Token.USER_TYPE);
        if (obj != null) {
            userType = (String) obj;
        }
        return userType;
    }

    /**
     * @description:获取租户id
     * @return:
     * @author: 熊锋
     * @time: 2020/4/1 14:00
     */
    public static String getTenantId() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String tenantId = "";
        Object obj = request.getHeader(Constants.Token.TENANT_ID);
        if (obj != null) {
            tenantId = (String) obj;
        }
        return tenantId;
    }

    /**
     * 从Request中获取公司id
     *
     * @return
     * @author 作者：熊锋
     * @version 创建时间：2019年6月12日 下午4:49:38
     */
    public static String getCompanyId() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String companyId = "";
        Object obj = request.getHeader(Constants.Token.COMPANY_ID);
        if (obj != null) {
            companyId = (String) obj;
        }
        return companyId;
    }

    /**
     * 从Request中获取小区id
     *
     * @return
     * @author 作者：熊锋
     * @version 创建时间：2019年6月12日 下午4:49:38
     */
    public static String getCommunityId() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String communityId = "";
        Object obj = request.getHeader(Constants.Token.COMMUNITY_ID);
        if (obj != null) {
            communityId = (String) obj;
        }
        return communityId;
    }

    /**
     * @description:是否是saas用户
     * @return:
     * @author: 熊锋
     * @time: 2020/7/9 11:23
     */
    public static boolean getIsSaas() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        boolean isSaas = false;
        Object obj = request.getHeader(Constants.Token.IS_SAAAS);
        if (obj != null) {
            String value= (String) obj;
            if("1".equals(value))
            {
                isSaas=true;
            }
        }
        return isSaas;
    }

    /**
     * @description:获取用户岗位id
     * @return:
     * @author: 熊锋
     * @time: 2020/7/9 11:23
     */
    public static String getJobId() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String jobId="";
        Object obj = request.getHeader(Constants.Token.JOB_ID);
        if (obj != null) {
            jobId=(String) obj;
        }
        return jobId;
    }

    /**
     * 从Request中获取职位名称(header取出来会乱码 从redis中取)
     * @return
     * @author 作者：熊锋
     * @version 创建时间：2019年5月26日 下午2:40:04
     */
    public static String getJobName() {
        String jobName="";
        String json=requestUtils.redisUtils.get("token-"+RequestUtils.getUserCode(),String.class);
        RedisInfoDto redisInfoDto= JSON.parseObject(json,RedisInfoDto.class);
        if (redisInfoDto!=null){
            jobName=redisInfoDto.getJobName();
        }
        return jobName;
    }

    /**
     * @description:获取用户角色id
     * @return:
     * @author: 熊锋
     * @time: 2020/7/9 11:23
     */
    public static String getRoleId() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String roleId="";
        Object obj = request.getHeader(Constants.Token.ROLE_ID);
        if (obj != null) {
            roleId=(String) obj;
        }
        return roleId;
    }
}
