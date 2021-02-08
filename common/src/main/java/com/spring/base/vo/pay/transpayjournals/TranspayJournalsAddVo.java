package com.spring.base.vo.pay.transpayjournals;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import lombok.Data;
import lombok.ToString;


/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-04-23 09:49:14
* @Desc类说明: 新增支付记录vo
*/

@ApiModel
@Data
@ToString
public class TranspayJournalsAddVo {

   //交易流水号
   @ApiModelProperty(value = "交易流水号")
   private String transId;

   //cash 现金card 刷卡transfer 转账alipay 支付宝wechat 微信unionpay 银联
   @ApiModelProperty(value = "cash 现金card 刷卡transfer 转账alipay 支付宝wechat 微信unionpay 银联")
   private String paytype;

   //第三方支付商户 流水号
   @ApiModelProperty(value = "第三方支付商户 流水号")
   private String thirdTranspayId;

   //进账公司名称
   @ApiModelProperty(value = "进账公司名称")
   private String recordCompanyName;

   //进账公司编号
   @ApiModelProperty(value = "进账公司编号")
   private String recordCompanyId;

   //最后更新时间
   @ApiModelProperty(value = "最后更新时间")
   private String updateTime;

   //
   @ApiModelProperty(value = "")
   private String subPaytype;

   //支付账户名称
   @ApiModelProperty(value = "支付账户名称")
   private String payAccName;

   //
   @ApiModelProperty(value = "")
   private Integer observation;


}
