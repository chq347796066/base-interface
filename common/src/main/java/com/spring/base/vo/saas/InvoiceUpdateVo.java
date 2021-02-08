package com.spring.base.vo.saas;

import java.util.Date;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;


 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-09 10:48:56
 * @Desc类说明: 更新发票信息vo
 */
@ApiModel
@Data
@ToString
public class InvoiceUpdateVo  {
	
	/**
	 * 发票id
	 */
	@ApiModelProperty(value = "发票id")
	private String id;
	
	/**
	 * 发票抬头id
	 */
	@ApiModelProperty(value = "发票抬头id")
	private String invoiceHeadId;

	 /**
	  * 订单id
	  */
	 @ApiModelProperty(value = "订单id")
	 private String orderId;
	
	/**
	 * 金额
	 */
	@ApiModelProperty(value = "金额")
	private BigDecimal invoiceAmount;
	
	/**
	 * 状态（1 待审核 2 开票失败 3 已开票 4 待索取)
	 */
	@ApiModelProperty(value = "状态（1 待审核 2 开票失败 3 已开票 4 待索取")
	private Integer invoiceStatus;
	
	/**
	 * 发票号码
	 */
	@ApiModelProperty(value = "发票号码")
	private String invoiceNum;
	
	/**
	 * 开票申请时间
	 */
	@ApiModelProperty(value = "开票申请时间")
	private Date applyTime;
	
	/**
	 * 开票时间（供应商提供发票时间）
	 */
	@ApiModelProperty(value = "开票时间（供应商提供发票时间）")
	private Date invoiceDate;
	
	/**
	 * 开票描述
	 */
	@ApiModelProperty(value = "开票描述")
	private String description;
	
	/**
	 * 开票审核不通过原因
	 */
	@ApiModelProperty(value = "开票审核不通过原因")
	private String reason;

	 /**
	  * 用户手机号
	  */
	 @ApiModelProperty(value = "用户手机号")
	 private String mobile;

}
