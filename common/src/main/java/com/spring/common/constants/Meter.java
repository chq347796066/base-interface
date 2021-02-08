package com.spring.common.constants;

/**
 * @author 作者：赵进华
 * @version 创建时间：2019年3月21日 上午9:36:54
 * @Desc类说明:基础常量类
 */
public interface Meter {

    /**
     * 状态
     *
     * @author Administrator
     */
    interface READ_CHECK {
        /**
         * 未抄表
         */
        int NO = 0;

        /**
         * 已抄表
         */
        int YES = 1;
    }

}
