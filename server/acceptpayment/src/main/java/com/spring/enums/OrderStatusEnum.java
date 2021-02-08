package com.spring.enums;

import lombok.Getter;

/**
 * 订单状态
 * 2018-06-04 16:58
 * @author dell
 */
@Getter
public enum OrderStatusEnum {

    /**
     * 支付成功
     */
    SUCCESS("支付成功"),

    /**
     * 转入退款
     */
    REFUND("转入退款"),

    /**
     * 未支付
     */
    NOTPAY("未支付"),

    /**
     * 已关闭
     */
    CLOSED("已关闭"),

    /**
     * 已撤销（刷卡支付）
     */
    REVOKED("已撤销（刷卡支付）"),

    /**
     * 用户支付中
     */
    USERPAYING("用户支付中"),

    /**
     * 支付失败
     */
    PAYERROR("支付失败"),


    /**
     * 未知状态
     */
    UNKNOW("未知状态"),
    ;

    /**
     * 描述 微信退款后有内容
     */
    private final String desc;

    OrderStatusEnum(String desc) {
        this.desc = desc;
    }

    public static OrderStatusEnum findByName(String name) {
        for (OrderStatusEnum orderStatusEnum : OrderStatusEnum.values()) {
            if (name.toLowerCase().equals(orderStatusEnum.name().toLowerCase())) {
                return orderStatusEnum;
            }
        }
        throw new RuntimeException("错误的微信支付状态");
    }
}
