package com.spring.common.enums;

/**
 * 开票状态枚举
 *
 * @author WuJiaQuan
 * @date 2020/7/13 19:41
 */
public enum InvoiceStatusEnum {

    /**
     * 待审批
     */
    PENDING_REVIEW(1, "待审核"),

    /**
     * 申请中（审批通过）
     */
    INVOICING_FAILED(2, "开票失败"),

    /**
     * 已开票
     */
    INVOICED(3, "已开票");

    /**
     * 枚举编号
     */
    private final int enumCode;

    /**
     * 枚举值
     */
    private final String enumValue;

    InvoiceStatusEnum(int enumCode, String enumValue) {
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
