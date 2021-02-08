package com.spring.enums;

/**
 * 支付平台
 *
 * @author null
 * @date 2019/9/19
 */
public enum BestPayPlatformEnum {
    /**
     支付宝
     */
    ALIPAY("alipay", "支付宝"),
    /**
     微信
     */
    WX("wx", "微信"),
    ;

    private final String code;

    private final String name;

    BestPayPlatformEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
