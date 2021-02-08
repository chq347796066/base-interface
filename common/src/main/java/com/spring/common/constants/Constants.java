package com.spring.common.constants;

/**
 * @author 作者：赵进华
 * @version 创建时间：2019年3月21日 上午9:36:54
 * @Desc类说明:基础常量类
 */
public interface Constants {
    /**
     * @description:用户初始密码
     * @return:
     * @author: 赵进华
     * @time: 2020/4/3 10:56
     */
    String INIT_PASSWORD = "8888";


    /**
     * 操作标示
     */
    String RESULT_CODE = "resultCode";
    /**
     * 操作提示
     */
    String RESULT_MSG = "resultMsg";
    /**
     * 返回数据
     */
    String DATA = "data";

    /**
     * 系统管理员
     */
    String SYSADMIN = "admin";

    /**
     * 创建人
     */
    String CREATE_BY = "createrBy";

    /**
     * SAAS
     */
    String SAAS = "saas";

    /**
     * SAAS试用时间
     */
    int SAAS_TRY_DATE = 1;

    /**
     * SAAS试用小区数量
     */
    int SAAS_COMMUNITY_NO = 10;

    /**
     * 是否是管理员
     */
    String ISADMIN = "1";

    /**
     * 创建时间
     */
    String CREATE_DATE = "createrDate";

    /**
     * 修改人
     */
    String UPDATE_BY = "modifyBy";

    /**
     * 修改时间
     */
    String UPDATE_DATE = "modifyDate";

    /**
     * where 条件
     */
    String WHERE = " where 1=1 ";

    /**
     * 用户类型
     **/
    interface UserType {
        //Web管理端
        String MANAGE = "1";
        //Web收费费端
        String PAYMENT = "2";
        //物业App
        String ESTATE_APP = "3";
        //业主App
        String OWNER_APP = "4";
    }


    /**
     * 状态
     *
     * @author Administrator
     */
    interface Status {
        /**
         * 禁用
         */
        int DISABLE = 2;

        /**
         * 启用
         */
        int ENABLE = 1;
    }

    /**
     * 状态
     *
     * @author Administrator
     */
    interface HouseHoldType {
        String YEZU = "1";

        String QINSHU = "2";

        String ZUKE = "3";

        String YOUKE = "4";

        String WEIZUCE = "5";
    }

    /**
     * token中信息
     *
     * @author Administrator
     */
    interface Token {
        /**
         * http请求head中的token
         */
        String ACCESS_TOKEN = "access_token";

        /**
         * 用户类型(1平台管理员,2物业公司管理员
         */
        String USER_TYPE = "userType";

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
         * 租户id
         */
        String TENANT_ID = "tenantId";

        /**
         * 租户名称
         */
        String TENANT_NAME = "tenantId";

        /**
         * 公司id
         */
        String COMPANY_ID = "companyId";

        /**
         * 公司名称
         */
        String COMPANY_NAME = "companyName";

        /**
         * 小区id
         */
        String COMMUNITY_ID = "communityId";

        /**
         * 小区名称
         */
        String COMMUNITY_NAME = "communityName";

        /**
         * 手机号码
         */
        String MOBILE = "mobile";

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
         * 角色id
         */
        String ROLE_ID = "roleId";

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
    }

    /**
     * redis中key前缀
     *
     * @author Administrator
     */
    interface RedisPrefix {
        /**
         * 网关忽略地址
         */
        String ZUUL_IGNORE_URL = "zuul-ignore-url";

        /**
         * token
         */
        String TOKEN = "token-";

        /**
         * clientToken
         */
        String CLIENT_TOKEN = "clientToken-";

        /**
         * 短信
         */
        String TEL_MSG = "telCode-";

        /**
         * 图片验证码
         */
        String IMG_VERIFY = "imgCode-";


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
        int TOKTN_EXPIRE = 1800;

        /**
         * 首页广告过期时间24小时:单位秒
         */
        int HOME_AD_EXPIRE = 86400;

        /**
         * 短信图片验证码过期时间:单位秒
         */
        int TEL_MSG_EXPIRE = 3600;
    }

    /**
     * @description:用户是否为saas用户
     * @return:
     * @author: 赵进华
     * @time: 2020/7/9 14:40
     */
    interface isSaaS {
        /**
         * 是
         */
        int OK = 1;

        /**
         * 否
         */
        int NO = 0;
    }

    /**
     * 枚举类型常量
     *
     * @author WuJiaQuan
     * @date 2020/7/14 10:18
     */
    interface EnumTypeConstant {
        /**
         * 发票状态枚举
         */
        String INVOICE_STATUS_ENUM_TYPE = "INVOICE_STATUS";

        /**
         * 订单支付类型枚举
         */
        String ORDER_PAYMENT_TYPE_ENUM_TYPE = "ORDER_PAYMENT_TYPE";

        /**
         * 订单状态枚举
         */
        String ORDER_STATUS_ENUM_TYPE = "ORDER_STATUS";

        /**
         * 订单类型枚举
         */
        String ORDER_TYPE_ENUM_TYPE = "ORDER_TYPE";

        /**
         * 租户状态枚举
         */
        String TENANT_STATUS_ENUM_TYPE = "TENANT_STATUS";
    }
}
