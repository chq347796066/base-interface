package com.spring.base.vo.pay.paidstatis;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 费项分组类
 * @author dell
 */
@Data
public class ChargeNameGroup {
    //费项编码
    private String chargeNo;
    //费项名称
    private String chargeName;
    //现金
    private BigDecimal cash=BigDecimal.ZERO;
    //支付宝
    private BigDecimal alipay=BigDecimal.ZERO;
    //微信
    private BigDecimal wechat=BigDecimal.ZERO;
    //银联
    private BigDecimal unionpay=BigDecimal.ZERO;
    //退款
    private BigDecimal refund=BigDecimal.ZERO;
    // 小计
    private BigDecimal subtotal=BigDecimal.ZERO;


}
