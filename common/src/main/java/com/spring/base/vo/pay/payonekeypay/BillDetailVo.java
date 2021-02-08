package com.spring.base.vo.pay.payonekeypay;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author dell
 */
@ApiModel
@Data
@ToString
public class BillDetailVo {

    //账单详情编号
    @ApiModelProperty(value = "账单详情编号")
    private String detailNo;
    //账单编号
    @ApiModelProperty(value = "账单编号")
    private String billNo;
    //费用月份
    @ApiModelProperty(value = "费用月份")
    private String billDate;
    //未缴金额
    @ApiModelProperty(value = "未缴金额")
    private String unpaidAmount;
    //费项类别
    @ApiModelProperty(value = "费项类别")
    private String feeCategory;
    //费项科目
    @ApiModelProperty(value = "费项科目")
    private String chargeType;
    //费项科目名称
    @ApiModelProperty(value = "费项科目名称")
    private String chargeTypeName;
    //费项编码
    @ApiModelProperty(value = "费项编码")
    private String chargeNo;
    //费项名称
    @ApiModelProperty(value = "费项名称")
    private String chargeName;
    //单价
    @ApiModelProperty(value = "单价")
    private String unitPrice;
    //数量
    @ApiModelProperty(value = "数量")
    private String num;
    //计费开始日期
    @ApiModelProperty(value = "计费开始日期")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    //计费结束日期
    @ApiModelProperty(value = "计费结束日期")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date endDate;
    //违约金
    @ApiModelProperty(value = "违约金")
    private String dPenalty;
    //应收总额
    @ApiModelProperty(value = "应收总额")
    private String paymentAmount;
    //已缴金额
    @ApiModelProperty(value = "已缴金额")
    private String receivedAmount;
    //计费方式
    @ApiModelProperty(value = "计费方式")
    private String chargeWay;

}
