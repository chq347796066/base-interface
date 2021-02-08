package com.spring.common.enums;

/**
 * 订单支付类型枚举
 *
 * @author WuJiaQuan
 */
public enum OrderPaymentTypeEnum {
    /**
     * 微信支付
     */
    WECHAT_PAYMENT(1, "微信支付"),


    /**
     * 支付宝支付
     */
    ALIPAY_PAYMENT(2, "支付宝支付");

    /**
     * 枚举编号
     */
    private final int enumCode;

    /**
     * 枚举值
     */
    private final String enumValue;

    OrderPaymentTypeEnum(int enumCode, String enumValue) {
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
