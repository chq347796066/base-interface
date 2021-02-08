package com.spring.base.vo.saas;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;


/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-07-01 17:10:11
* @Desc类说明: 订单查询列表
*/

@ApiModel
@Data
@ToString
public class OrderQueryVo {

   /**
    * 订单号
    */
   @ApiModelProperty(value = "订单号")
   private String orderNum;

   /**
    * 付款方式（：微信支付；1：支付宝）
    */
   @ApiModelProperty(value = "付款方式（：微信支付；1：支付宝）")
   private Integer payType;

   /**
    * 订单状态（1 待支付；2 支付成功;3 已取消；4 支付失败）
    */
   @ApiModelProperty(value = "订单状态（1 待支付；2 支付成功;3 已取消；4 支付失败）")
   private Integer orderStatus;

   /**
    * 订单下单开始时间
    */
   @ApiModelProperty(value = "订单下单开始时间")
   private String startTime;

   /**
    * 订单下单结束时间
    */
   @ApiModelProperty(value = "订单下单结束时间")
   private String endTime;

   /**
    * 用户手机号码
    */
   @ApiModelProperty(value = "用户手机号码")
   @NotNull(message = "手机号码不能为空")
   private String mobile;

}
