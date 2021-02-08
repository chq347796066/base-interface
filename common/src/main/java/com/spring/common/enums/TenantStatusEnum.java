package com.spring.common.enums;

/**
 * 租户状态枚举
 *
 * @author WuJiaQuan
 */
public enum TenantStatusEnum {
    /**
     * 待审核
     */
    PENDING_REVIEW(1, "待审核"),

    /**
     * 试用
     */
    TRY_OUT(2, "试用"),

    /**
     * 启用
     */
    REJECT(3, "审核拒绝"),

    /**
     * 启用
     */
    ENABLE(4, "启用"),

    /**
     * 停用
     */
    DISABLE(5, "停用");

    /**
     * 枚举编号
     */
    private final int enumCode;

    /**
     * 枚举值
     */
    private final String enumValue;

    TenantStatusEnum(int enumCode, String enumValue) {
        this.enumCode = enumCode;
        this.enumValue = enumValue;
    }

    public int getEnumCode() {
        return this.enumCode;
    }

    public String getEnumValue() {
        return enumValue;
    }
}
