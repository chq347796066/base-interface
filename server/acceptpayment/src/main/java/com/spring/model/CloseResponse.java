package com.spring.model;

import lombok.Data;

/**
 * 退款返回的参数
 * 2017-07-08 23:40
 * @author dell
 */
@Data
public class CloseResponse {

    /**
     * 订单号.
     */
    private String orderId;

    /**
     * 第三方支付流水号.
     */
    private String outTradeNo;
}
