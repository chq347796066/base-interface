package com.spring.base.entity.workflow;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-01-15 22:15:07
* @Desc类说明: 实体类
*/
@ApiModel
@Data
@ToString(callSuper = true)
public class BillRepeatDataEntity implements Serializable {
   private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Integer id;

   //流水号
   @ApiModelProperty(value = "流水号")
   private String transId;

   //费项编码
   @ApiModelProperty(value = "费项编码")
   private String chargeNo;

   //费项名称
   @ApiModelProperty(value = "费项名称")
   private String chargeName;

   //缴费金额
   @ApiModelProperty(value = "缴费金额")
   private String transAmount;

   //单笔金额
   @ApiModelProperty(value = "单笔金额")
   private String businessAmount;

   //支付方式
   @ApiModelProperty(value = "支付方式")
   private String payType;

   //交易类型
   @ApiModelProperty(value = "交易类型")
   private String businessType;

   //缴费时间
   @ApiModelProperty(value = "缴费时间")
   private String transTime;

   //支付状态
   @ApiModelProperty(value = "支付状态")
   private String transStatus;

   //终端
   @ApiModelProperty(value = "终端")
   private String terminIdentify;

   //操作员id
   @ApiModelProperty(value = "操作员id")
   private String operatorNo;

   //操作员
   @ApiModelProperty(value = "操作员")
   private String operatorName;

   //小区id
   @ApiModelProperty(value = "小区id")
   private String cid;

   //小区名称
   @ApiModelProperty(value = "小区名称")
   private String cname;

   //公司id
   @ApiModelProperty(value = "公司id")
   private String recordCompanyId;

   //公司名称
   @ApiModelProperty(value = "公司名称")
   private String recordCompanyName;

   //电话
   @ApiModelProperty(value = "电话")
   private String phone;

   //备注
   @ApiModelProperty(value = "备注")
   private String rmark;

   //交易时间
   @ApiModelProperty(value = "交易时间")
   private String updateTime;

   //业务ID
   @ApiModelProperty(value = "业务ID")
   private String businessId;

   //账单编号
   @ApiModelProperty(value = "账单编号")
   private String billNo;

   //业主姓名
   @ApiModelProperty(value = "业主姓名")
   private String pname;

   //客户号
   @ApiModelProperty(value = "客户号")
   private String custNo;

   //房号
   @ApiModelProperty(value = "房号")
   private String roomNo;

   //账单详情编号
   @ApiModelProperty(value = "账单详情编号")
   private String billDetialNo;



}
