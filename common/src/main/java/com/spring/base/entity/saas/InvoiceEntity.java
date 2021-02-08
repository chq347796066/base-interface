package com.spring.base.entity.saas;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

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
 * @date : 创建时间：2020-07-09 10:48:56
 * @Desc类说明: 发票信息实体类
 */
@ApiModel
@Data
@ToString(callSuper = true)
@TableName("s_invoice")
public class InvoiceEntity extends SaasBaseEntity implements Vo<String>, Serializable {
	private static final long serialVersionUID = 1L;
	
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
	 * 金额
	 */
	@ApiModelProperty(value = "金额")
	private String invoiceAmount;

	/**
	 * 状态（1 待审核 2 开票失败 3 已开票
	 */
	@ApiModelProperty(value = "状态（1 待审核 2 开票失败 3 已开票")
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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date applyTime;
	/**
	 * 开票时间（供应商提供发票时间）
	 */
	@ApiModelProperty(value = "开票时间（供应商提供发票时间）")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
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

	 /**
	  * 租户Id
	  */
	 @ApiModelProperty(value = "租户Id")
	 private String tenantId;
 }
