package com.spring.base.vo.pay.paidstatis;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 总计
 * @author dell
 */
@Data
public class SummaryVo {
    private static String  summary="总计";
    //现金
    private BigDecimal cashTotal=BigDecimal.ZERO;
    //支付宝
    private BigDecimal alipayTotal=BigDecimal.ZERO;
    //微信
    private BigDecimal wechatTotal=BigDecimal.ZERO;
    //银联
    private BigDecimal unionpayTotal=BigDecimal.ZERO;
    //退款
    private BigDecimal refundTotal=BigDecimal.ZERO;
    // 小计总计
    private BigDecimal subtotalTotal=BigDecimal.ZERO;

}
