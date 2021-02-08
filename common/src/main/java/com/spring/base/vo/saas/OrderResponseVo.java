package com.spring.base.vo.saas;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-08 15:12:04
 * @Desc类说明: 查询订单返回vo
 */
 
@ApiModel
@Data
@ToString
public class OrderResponseVo {

	/**
	 * 订单id
	 */
	@ApiModelProperty(value = "订单id")
	private String id;

	 /**
	  * 应用名称
	  */
	 @ApiModelProperty(value = "应用名称")
	 private String appName;

	/**
	 * 应用id
	 */
	@ApiModelProperty(value = "应用id")
	private String appId;

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
	 * 支付时间
	 */
	@ApiModelProperty(value = "支付时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date payDate;
	
	/**
	 * 订单金额
	 */
	@ApiModelProperty(value = "订单金额")
	private double orderAmount;
	
	/**
	 * 购买数量
	 */
	@ApiModelProperty(value = "购买数量")
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
	 * 过期时间
	 */
	@ApiModelProperty(value = "过期时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date expireDate;

}
