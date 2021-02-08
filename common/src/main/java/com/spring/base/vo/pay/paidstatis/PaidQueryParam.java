package com.spring.base.vo.pay.paidstatis;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author dell
 */
@ApiModel
@Data
@ToString
public class PaidQueryParam {
    @ApiModelProperty(value = "小区")
    private String cid;
    @ApiModelProperty(value = "楼栋")
    private String building;
    @ApiModelProperty(value = "房屋编号")
    private String hid;
    @ApiModelProperty(value = "客户姓名")
    private String custName;
    @ApiModelProperty(value = "支付方式")
    private String paytype;
    @ApiModelProperty(value = "费项科目")
    private String chargeType;
    @ApiModelProperty(value = "收费开始时间")
    private String startTransTime;
    @ApiModelProperty(value = "收费结束时间")
    private String startEndTime;
}
