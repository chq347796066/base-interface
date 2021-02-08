package com.spring.common.enums;

/**
 * 订单类型枚举
 *
 * @author WuJiaQuan
 */
public enum OrderTypeEnum {

    /**
     * 试用
     */
    TRT_OUT(1, "试用"),

    /**
     * 订购
     */
    ORDER(2, "订购"),

    /**
     * 续费
     */
    RENEW(3, "续费"),

    /**
     * 升级
     */
    UPGRADE(4, "升级");

    /**
     * 枚举编号
     */
    private final int enumCode;

    /**
     * 枚举值
     */
    private final String enumValue;

    OrderTypeEnum(int enumCode, String enumValue) {
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
