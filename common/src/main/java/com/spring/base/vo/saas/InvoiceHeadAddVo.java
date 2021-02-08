package com.spring.base.vo.saas;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-09 10:48:56
 * @Desc类说明: 新增发票抬头vo
 */
 
@ApiModel
@Data
@ToString
public class InvoiceHeadAddVo {
	
	/**
	 * 登陆人手机号
	 */
	@ApiModelProperty(value = "登陆人手机号")
	@NotBlank(message ="请输入登陆人手机号")
	private String mobile;
	
	/**
	 * 公司（发票抬头）
	 */
	@ApiModelProperty(value = "公司（发票抬头）")
	@NotBlank(message ="请输入发票抬头")
	private String companyName;
	
	/**
	 * 发票类型(0:增值税普通发票；1:增值税专用发票)
	 */
	@ApiModelProperty(value = "发票类型(0:增值税普通发票；1:增值税专用发票)")
	private Integer invoiceType;
	
	/**
	 * 发票税号
	 */
	@ApiModelProperty(value = "发票税号")
	private String taxNum;
	
	/**
	 * 开户银行
	 */
	@ApiModelProperty(value = "开户银行")
	private String openBank;
	
	/**
	 * 开户帐号
	 */
	@ApiModelProperty(value = "开户帐号")
	private String openAccount;
	
	/**
	 * 开户地址
	 */
	@ApiModelProperty(value = "开户地址")
	private String address;
	
	/**
	 * 邮箱
	 */
	@ApiModelProperty(value = "邮箱")
	@NotBlank(message = "请输入邮箱")
	private String mail;
	
	/**
	 * 电话
	 */
	@ApiModelProperty(value = "电话")
	private String tel;
	
	/**
	 * 开票类型(0 个人；1 企业)
	 */
	@ApiModelProperty(value = "开票类型(0 个人；1 企业)")
	private Integer makeInvoiceType;

	/**
	 * 是否默认
	 */
	@ApiModelProperty(value = "是否默认(0 默认；1 非默认)")
	private Integer isDefault;

}
