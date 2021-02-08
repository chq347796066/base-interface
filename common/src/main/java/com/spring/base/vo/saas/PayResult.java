package com.spring.base.vo.saas;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;


/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-07-08 15:12:04
* @Desc类说明: 微信回调返回
*/

@ApiModel
@Data
@ToString
public class PayResult {

   /**
    *返回状态码
    */
   @ApiModelProperty(value = "返回状态码")
   private String return_code;

   /**
    *公众账号ID
    */
   @ApiModelProperty(value = "公众账号ID")
   private String appid;

   /**
    *商户号
    */
   @ApiModelProperty(value = "商户号")
   private String mch_id;

   /**
    *随机字符串
    */
   @ApiModelProperty(value = "随机字符串")
   private String nonce_str;

   /**
    *签名
    */
   @ApiModelProperty(value = "签名")
   private String sign;

   /**
    *业务结果
    */
   @ApiModelProperty(value = "业务结果")
   private String result_code;

   /**
    *用户标识
    */
   @ApiModelProperty(value = "用户标识")
   private String openid;

   /**
    *交易类型
    */
   @ApiModelProperty(value = "交易类型")
   private String trade_type;

   /**
    *付款银行
    */
   @ApiModelProperty(value = "付款银行")
   private String bank_type;

   /**
    *总金额
    */
   @ApiModelProperty(value = "总金额")
   private String total_fee;

   /**
    *现金支付金额
    */
   @ApiModelProperty(value = "现金支付金额")
   private String cash_fee;

   /**
    *微信支付订单号
    */
   @ApiModelProperty(value = "微信支付订单号")
   private String transaction_id;

   /**
    *商户订单号
    */
   @ApiModelProperty(value = "商户订单号")
   private String out_trade_no;

   /**
    *trade_state
    */
   @ApiModelProperty(value = "trade_state")
   private String trade_state;
   /**
    *支付完成时间
    */
   @ApiModelProperty(value = "支付完成时间")
   private String time_end;

   /**
    *返回信息
    */
   @ApiModelProperty(value = "返回信息")
   private String return_msg;

   /**
    *设备号
    */
   @ApiModelProperty(value = "设备号")
   private String device_info;

   /**
    *错误代码
    */
   @ApiModelProperty(value = "错误代码")
   private String err_code;

   /**
    *错误代码描述
    */
   @ApiModelProperty(value = "错误代码描述")
   private String err_code_des;

   /**
    *是否关注公众账号
    */
   @ApiModelProperty(value = "是否关注公众账号")
   private String is_subscribe;

   /**
    *返回状态码
    */
   @ApiModelProperty(value = "返回状态码")
   private String fee_type;

   /**
    *现金支付货币类型
    */
   @ApiModelProperty(value = "现金支付货币类型")
   private String cash_fee_type;

   /**
    *代金券或立减优惠金额
    */
   @ApiModelProperty(value = "代金券或立减优惠金额")
   private String coupon_fee;

   /**
    *代金券或立减优惠使用数量
    */
   @ApiModelProperty(value = "代金券或立减优惠使用数量")
   private String coupon_count;

   /**
    *代金券或立减优惠ID
    */
   @ApiModelProperty(value = "代金券或立减优惠ID")
   private String coupon_id_$n;

   /**
    *单个代金券或立减优惠支付金额
    */
   @ApiModelProperty(value = "单个代金券或立减优惠支付金额")
   private String coupon_fee_$n;

   /**
    *商家数据包
    */
   @ApiModelProperty(value = "商家数据包")
   private String attach;

   /**
    *交易状态描述
    */
   @ApiModelProperty(value = "交易状态描述")
   private String trade_state_desc;

}
