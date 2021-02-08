package com.spring.base.vo.pay.paidstatis;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 缴费记录查询
 * @author dell
 */
@Data
public class PaymentRecordVo {
    //小区编号
    private String cid;
    //小区名称
    private String cname;
    //楼栋
    private String building;
    //房屋编号
    private String hid;
    //客户姓名
    private String custName;
    //交易
    private String transType;
    //收费科目编码
    private String chargeType;
    //收费科目名称
    private String chargeTypeName;
    //收费编码
    private String chargeNo;
    //收费名称
    private String chargeName;
    //收费时间
    private String transTime;
    //付款类型
    private String paytype;
    //收费金额
    private BigDecimal transAmount;
    //费项单笔交易金额
    private BigDecimal businessAmount;


}
