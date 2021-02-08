package com.spring.common.enums;

/**
 * 订单状态枚举
 *
 * @author WuJiaQuan
 */
public enum OrderStatusEnum {
    /**
     * 待支付
     */
    TO_BE_PAID(1, "待支付"),

    /**
     * 支付成功
     */
    PAYMENT_SUCCESSFUL(2, "支付成功"),

    /**
     * 已取消
     */
    CANCELLED(3, "已取消"),

    /**
     * 支付失败
     */
    PAYMENT_FAILED(4, "支付失败");

    /**
     * 枚举编号
     */
    private final int enumCode;

    /**
     * 枚举值
     */
    private final String enumValue;

    OrderStatusEnum(int enumCode, String enumValue) {
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
