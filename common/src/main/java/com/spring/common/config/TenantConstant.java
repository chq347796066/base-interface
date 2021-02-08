package com.spring.common.config;

/**
 * 多租户常量
 * @author zwb
 * @date 2020-11-7
 */
public interface TenantConstant {

    /**
     * header 中租户ID
     */
    String MATE_TENANT_ID = "tenantId";

    /**
     * 租户id参数
     */
    String MATE_TENANT_ID_PARAM = "tenantId";

    /**
     * 租户ID
     */
    String TENANT_ID_DEFAULT = "1";

    /**
     * 超级管理员
     */
    String TENANT_ADMIN= "admin";

}
