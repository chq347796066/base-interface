package com.spring.gateway.constants;

/**
 * @author 作者：赵进华
 * @version 创建时间：2019年3月21日 上午9:36:54
 * @Desc类说明:基础常量类
 */
public interface Constants {
    /**
     * token中信息
     *
     * @author Administrator
     */
    interface Token {
        /**
         * http请求head中的token
         */
        String ACCESS_TOKEN = "accessToken";

        /**
         * 用户类型(1平台管理员,2物业公司管理员
         */
        String USER_TYPE ="userType";

        /**
         * 小区id
         */
        String COMMUNITY_ID = "communityId";

        /**
         * token中用户id
         */
        String USER_ID = "userId";

        /**
         * token中用户Code
         */
        String USER_CODE = "userCode";

        /**
         * token中用户名
         */
        String USER_NAME = "userName";

        /**
         * 公司id
         */
        String COMPANY_ID = "companyId";

        /**
         * 公司Code
         */
        String COMPANY_CODE = "companyCode";

        /**
         * 是否是管理员
         */
        String IS_ADMIN = "isAdmin";

        /**
         * 是否是saas用户
         */
        String IS_SAAAS = "isSaas";

        /**
         * 用户岗位id
         */
        String JOB_ID = "jobId";

        /**
         * 部门id
         */
        String DEPT_ID = "deptId";

        /**
         * 角色id
         */
        String ROLE_ID = "roleId";

        /**
         * 租户id
         */
        String TENANT_ID = "tenantId";

        /**
         * clientToken中用户id
         */
        String CLIENT_USER_ID = "clientUserId";

        /**
         * token中日期
         */
        String DATE = "date";

        /**
         * IP
         */
        String IP = "ip";

        /**
         * token中数据
         */
        String DATA = "data";

        /**
         * 鉴权类型
         */
        String TYPE = "type";

        /**
         * 内部调用鉴权
         */
        String IN = "in";

        /**
         * 外部调用鉴权
         */
        String OUT = "out";

    }

    /**
     * redis中key前缀
     *
     * @author Administrator
     */
    interface RedisPrefix {

        /**
         * token
         */
        String TOKEN = "token-";
    }
    /**
     * redis中key过期时间
     *
     * @author Administrator
     */
    interface RedisExpire {
        /**
         * token过期时间:单位秒
         */
        int TOKEN_EXPIRE = 1800;

        /**
         * 首页广告过期时间24小时:单位秒
         */
        int HOME_AD_EXPIRE = 86400;

        /**
         * 短信图片验证码过期时间:单位秒
         */
        int TEL_MSG_EXPIRE = 3600;
    }

}
