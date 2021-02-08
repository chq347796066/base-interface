package com.spring.base.vo.pay.paybills;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import lombok.Data;
import lombok.ToString;


/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-04-23 09:49:14
* @Desc类说明: 新增缴费账单vo
*/

@ApiModel
@Data
@ToString
public class PayBillsAddVo {

   //账单编号
   @ApiModelProperty(value = "账单编号")
   private String billNo;

   //账单金额
   @ApiModelProperty(value = "账单金额")
   private String billAmount;

   //账单日期
   @ApiModelProperty(value = "账单日期")
   private String billDate;

   //客户号
   @ApiModelProperty(value = "客户号")
   private String custNo;

   //房屋编号
   @ApiModelProperty(value = "房屋编号")
   private String hid;

   //单元号
   @ApiModelProperty(value = "单元号")
   private String unitNo;

   //房号
   @ApiModelProperty(value = "房号")
   private String roomNo;

   //区域楼栋
   @ApiModelProperty(value = "区域楼栋")
   private String areaNo;

   //小区编号
   @ApiModelProperty(value = "小区编号")
   private String plotId;

   //01未缴 02已缴 03欠费
   @ApiModelProperty(value = "01未缴 02已缴 03欠费")
   private String billStatus;

   //已缴金额
   @ApiModelProperty(value = "已缴金额")
   private String receivedAmount;

   //最后更新时间
   @ApiModelProperty(value = "最后更新时间")
   private String updateTime;

   //业主编号
   @ApiModelProperty(value = "业主编号")
   private String ownerNo;

   //业主姓名
   @ApiModelProperty(value = "业主姓名")
   private String ownerName;

   //业主手机号
   @ApiModelProperty(value = "业主手机号")
   private String ownerMoble;

   //小区名称
   @ApiModelProperty(value = "小区名称")
   private String plotName;

   //公司id
   @ApiModelProperty(value = "公司id")
   private String companyId;

   //01历史账单 02系统账单
   @ApiModelProperty(value = "01历史账单 02系统账单")
   private String billType;

   //
   @ApiModelProperty(value = "")
   private Integer observation;


}
