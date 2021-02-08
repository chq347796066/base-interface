package com.spring.base.vo.pay.payonekeypay;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@ApiModel
@Data
@ToString
public class TemporaryPayVo {

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
    private Date startDate;
    //计费结束日期
    @ApiModelProperty(value = "计费结束日期")
    private Date endDate;
    //计费方式
    @ApiModelProperty(value = "计费方式")
    private String chargeWay;

}
