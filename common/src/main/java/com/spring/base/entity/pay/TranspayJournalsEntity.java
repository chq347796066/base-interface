package com.spring.base.entity.pay;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.base.vo.Vo;
import com.spring.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.ToString;

/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-04-23 09:49:14
* @Desc类说明: 支付记录实体类
*/
@ApiModel
@Data
@ToString(callSuper = true)
@TableName("p_transpay_journals")
public class TranspayJournalsEntity extends BaseEntity implements Vo<String>, Serializable {
   private static final long serialVersionUID = 1L;

   //主键
   @ApiModelProperty(value = "主键")
   private String id;

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
   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
   private Date updateTime;

   //
   @ApiModelProperty(value = "")
   private String subPaytype;

   //支付账户名称
   @ApiModelProperty(value = "支付账户名称")
   private String payAccName;

   //
   @ApiModelProperty(value = "")
   private Integer observation;

    //状态(1启用,2禁用)
    @ApiModelProperty(value = "状态(1启用,2禁用)")
    private Integer status;

}
