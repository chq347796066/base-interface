package com.spring.base.vo.pay.queryrecord;


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
public class QueryRecordVo {

    //小区id
    @ApiModelProperty(value = "小区id")
    private String cid;

    //楼栋
    @ApiModelProperty(value = "楼栋")
    private String building;

    //房号id
    @ApiModelProperty(value = "房号id")
    private String hid;

    //客户id
    @ApiModelProperty(value = "客户id")
    private String pid;

    //客户名称
    @ApiModelProperty(value = "客户名称")
    private String pname;

    //付款方式
    @ApiModelProperty(value = "付款方式")
    private String paytype;

    //收费科目
    @ApiModelProperty(value = "收费科目")
    private String chargeType;

    //收费开始时间
    @ApiModelProperty(value = "收费开始时间")
    private String startDate;

    //收费结束时间
    @ApiModelProperty(value = "收费结束时间")
    private String endDate;

    //账单详情编号
    @ApiModelProperty(value = "账单详情编号")
    private String detailNo;

    //支付状态
    @ApiModelProperty(value = "支付状态")
    private String transStatus;

    //交易实时时间
    @ApiModelProperty(value = "交易实时时间")
    private String updateTime;

    //交易号
    @ApiModelProperty(value = "交易号")
    private String transId;

}
