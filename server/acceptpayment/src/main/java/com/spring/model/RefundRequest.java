package com.spring.model;

import com.spring.enums.BestPayTypeEnum;
import lombok.Data;

/**
 * 支付时请求参数
 * @author dell
 */
@Data
public class RefundRequest {

    /**
     * 支付方式.
     */
    private BestPayTypeEnum payTypeEnum;

    /**
     * 订单号.
     */
    private String orderId;

    /**
     * 订单金额.
     */
    private Double orderAmount;
}
