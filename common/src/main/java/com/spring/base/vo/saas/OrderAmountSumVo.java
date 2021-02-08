package com.spring.base.vo.saas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 订单金额统计Vo
 *
 * @author WuJiaQuan
 */
public class OrderAmountSumVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 订单Id
     */
    private String orderId;

    /**
     * 订单支付时间
     */
    private LocalDateTime paymentDate;

    /**
     * 订单金额
     */
    private BigDecimal orderAmount;

    public OrderAmountSumVo() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }
}
