package com.spring.base.entity.saas;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.base.entity.SaasBaseEntity;
import com.spring.base.vo.Vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.ToString;

 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-08 15:12:04
 * @Desc类说明: 订单实体类
 */
@ApiModel
@Data
@ToString(callSuper = true)
@TableName("s_order")
public class OrderEntity extends SaasBaseEntity implements Vo<String>, Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 订单id
	 */
	@ApiModelProperty(value = "订单id")
	private String id;

	 /**
	  * 开票状态(1 待开票 2 已开票 3 已驳回 4 待索取)
	  */
	 @ApiModelProperty(value = "开票状态(1 待开票 2 已开票 3 已驳回 4 待索取)")
	 private Integer makeInvoiceStatus;

	/**
	 * 订单号
	 */
	@ApiModelProperty(value = "订单号")
	private String orderNum;

	/**
	 * 下单日期
	 */
	@ApiModelProperty(value = "下单日期")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date orderDate;

	/**
	 * 订单金额
	 */
	@ApiModelProperty(value = "订单金额")
	private String orderAmount;

	/**
	 * 购买数量
	 */
	@ApiModelProperty(value = "购买数量")
	private Integer buyNum;

	/**
	 * 应用购买时间（单位默认未月）
	 */
	@ApiModelProperty(value = "应用购买时间（单位默认未月）")
	private Integer buyTime;

	/**
	 * 订单类型（1.试用，2.订购，3.续费，4.升级）
	 */
	@ApiModelProperty(value = "订单类型（1.试用，2.订购，3.续费，4.升级）")
	private Integer orderType;

	/**
	 * 付款方式（：微信支付；1：支付宝）
	 */
	@ApiModelProperty(value = "付款方式（1 微信支付；2 支付宝）")
	private Integer payType;

	/**
	 * 订单状态（1 待支付；2 支付成功;3 已取消；4 支付失败）
	 */
	@ApiModelProperty(value = "订单状态（1 待支付；2 支付成功;3 已取消；4 支付失败）")
	private Integer orderStatus;

	/**
	 * 支付流水号
	 */
	@ApiModelProperty(value = "支付流水号")
	private String payNum;

	/**
	 * 支付时间
	 */
	@ApiModelProperty(value = "支付时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private LocalDateTime payDate;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
	private String description;

	/**
	 * 付款公司名
	 */
	@ApiModelProperty(value = "付款公司名")
	private String payCompany;

	/**
	 * 联系人
	 */
	@ApiModelProperty(value = "联系人")
	private String contactMan;

	/**
	 * 联系人手机
	 */
	@ApiModelProperty(value = "联系人手机")
	private String contactMobile;


	 /**
	  * 登陆人手机号
	  */
	 @ApiModelProperty(value = "登陆人手机号")
	 private String mobile;

	 /**
	  * 租户Id
	  */
	 @ApiModelProperty(value = "租户Id")
	 private String tenantId;

	 /**
	  * 应用Id
	  */
	 @ApiModelProperty(value = "应用Id")
	 private String appId;

	 /**
	  * 到期时间
	  */
	 @ApiModelProperty(value = "到期时间")
	 @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	 private LocalDateTime expireDate;

}
