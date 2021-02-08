package com.spring.base.vo.pay.transjournals;

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
public class TodayResult {

    //总收入
    @ApiModelProperty(value = "总收入")
    private String totalIncome ;

    //总收入较前一日
    @ApiModelProperty(value = "总收入较前一日")
    private String  incomeRate;

    //收费最高小区
    @ApiModelProperty(value = "收费最高小区")
    private String  highestPlotIncome;

    //小区名称
    @ApiModelProperty(value = "小区名称")
    private String  highestPlotName;

    //总收费订单
    @ApiModelProperty(value = "总收费订单")
    private String  totalChargeOrder;

    //总收费订单较前一日
    @ApiModelProperty(value = "总收费订单较前一日")
    private String  totalChargeOrderRate;

    //收费最多渠道金额
    @ApiModelProperty(value = "收费最多渠道金额")
    private String  highestChannelMoney;

    //收费最多渠道
    @ApiModelProperty(value = "收费最多渠道")
    private String  highestChannel;

    //本期收缴率
    @ApiModelProperty(value = "本期收缴率")
    private String  currentCollectionRate;

    //本期收往期欠费
    @ApiModelProperty(value = "本期收往期欠费")
    private String  overdueBillAmount;

}
