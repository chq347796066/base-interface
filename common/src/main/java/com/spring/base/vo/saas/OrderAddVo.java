package com.spring.base.vo.saas;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-08 15:12:04
 * @Desc类说明: 新增订单vo
 */
 
@ApiModel
@Data
@ToString
public class OrderAddVo {

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
	private Date orderDate;
	
	/**
	 * 订单金额
	 */
	@ApiModelProperty(value = "订单金额")
	@NotBlank(message = "订单金额不能为空")
	private String orderAmount;
	
	/**
	 * 购买数量
	 */
	@ApiModelProperty(value = "购买数量")
	@NotNull(message = "购买数量不能为空")
	private Integer buyNum;
	
	/**
	 * 应用购买时间（单位默认未月）
	 */
	@ApiModelProperty(value = "应用购买时间（单位默认未月）")
	@NotNull(message = "应用购买时间")
	private Integer buyTime;
	
	/**
	 * 订单类型（1.试用，2.订购，3.续费，4.升级）
	 */
	@ApiModelProperty(value = "订单类型（1.试用，2.订购，3.续费，4.升级）")
	private Integer orderType;
	
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
	 * 支付流水号
	 */
	@ApiModelProperty(value = "支付流水号")
	private String payNum;
	
	/**
	 * 支付时间
	 */
	@ApiModelProperty(value = "支付时间")
	private Date payDate;
	
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
	 @NotNull(message = "登陆人手机号不能为空")
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
	private Date expireDate;

}
