package com.spring.model;

import com.spring.enums.BestPayPlatformEnum;
import lombok.Data;

/**
 * 支付订单查询
 * 2018-05-31 17:52
 * @author dell
 */
@Data
public class OrderQueryRequest {

    /**
     * 支付平台.
     */
    private BestPayPlatformEnum platformEnum;

    /**
     * 订单号(orderId 和 outOrderId 二选一，两个都传以outOrderId为准)
     */
    private String orderId = "";

    /**
     * 外部订单号(例如微信生成的)
     */
    private String outOrderId = "";
}
