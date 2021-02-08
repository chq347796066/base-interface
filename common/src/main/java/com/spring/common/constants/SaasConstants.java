package com.spring.common.constants;

/**
 * @author 作者：赵进华
 * @version 创建时间：2019年3月21日 上午9:36:54
 * @Desc类说明:saas基础常量类
 */
public interface SaasConstants {


    /**
     * 租户类型
     **/
    interface TenantType {
        //待审核
        int  CHECK_PEND = 1;
        //试用
        int TRY = 2;
        //拒绝
        int REJECT = 3;
        //启用
        int START = 4;
        //停用
        int STOP = 5;
    }

}
