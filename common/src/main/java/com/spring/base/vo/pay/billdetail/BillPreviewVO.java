package com.spring.base.vo.pay.billdetail;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;


/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-04-23 09:49:13
* @Desc类说明: 新增账单详情vo
*/

@ApiModel
@Data
@ToString
public class BillPreviewVO {

   //小区编号
   @ApiModelProperty(value = "小区编号")
   private String cid;

   //详情编号
   @ApiModelProperty(value = "详情编号")
   private String hid;

   //费项编号
   @ApiModelProperty(value = "费项科目")
   private String chargeType;

   //费项名称编号
   @ApiModelProperty(value = "费项名称编号")
   private String chargeNo;

   //开始时间
   @ApiModelProperty(value = "开始时间")
   private String startDate;

   //结束时间
   @ApiModelProperty(value = "结束时间")
   private String endDate;

  //输入金额
   @ApiModelProperty(value = "输入金额")
   private String amount;

}
