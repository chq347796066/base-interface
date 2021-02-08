package com.spring.base.vo.pay.businessjournals;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;


/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-04-23 09:49:14
* @Desc类说明: 更新交流流水记录vo
*/
@ApiModel
@Data
@ToString
public class BusinessJournalsUpdateVo  {

   //主键
   @ApiModelProperty(value = "主键")
   private String id;

   //交易流水号
   @ApiModelProperty(value = "交易流水号")
   private String transId;

   //c000 无限制 c001 物业类 c002 车位类 c003 水费类 c004 电费类 c005 燃气类 c006 暖气类c007 一键缴费
   @ApiModelProperty(value = "c000 无限制 c001 物业类 c002 车位类 c003 水费类 c004 电费类 c005 燃气类 c006 暖气类c007 一键缴费")
   private String businessType;

   //业主姓名
   @ApiModelProperty(value = "业主姓名")
   private String pname;

   //业主编号
   @ApiModelProperty(value = "业主编号")
   private String pid;

   //房屋编号
   @ApiModelProperty(value = "房屋编号")
   private String hid;

   //车牌
   @ApiModelProperty(value = "车牌")
   private String plate;

    //楼栋
    @ApiModelProperty(value = "楼栋")
    private String building;

   //房号
   @ApiModelProperty(value = "房号")
   private String roomNo;

    //收费科目编码
    @ApiModelProperty(value = "收费科目编码")
    private String chargeType;

    //收费科目
    @ApiModelProperty(value = "收费科目")
    private String chargeTypeName;

   //费项编号
   @ApiModelProperty(value = "费项编号")
   private String chargeNo;

   //费项名称
   @ApiModelProperty(value = "费项名称")
   private String chargeName;

   //费项描述
   @ApiModelProperty(value = "费项描述")
   private String chargeDesc;

   //缴费周期
   @ApiModelProperty(value = "缴费周期")
   private String cycle;

   //滞纳金
   @ApiModelProperty(value = "滞纳金")
   private String lateFee;

   //单笔金额
   @ApiModelProperty(value = "单笔金额")
   private String businessAmount;

   //账单编号
   @ApiModelProperty(value = "账单编号")
   private String billNo;

   //账单详情编号
   @ApiModelProperty(value = "账单详情编号")
   private String billDetialNo;

   //周期类型 01-小时，02-天 03-月
   @ApiModelProperty(value = "周期类型 01-小时，02-天 03-月")
   private String cycleType;

   //最后更新时间
   @ApiModelProperty(value = "最后更新时间")
   private String updateTime;

   //费项标记
   @ApiModelProperty(value = "费项标记")
   private String chargeMark;

   //
   @ApiModelProperty(value = "")
   private Integer observation;

}
