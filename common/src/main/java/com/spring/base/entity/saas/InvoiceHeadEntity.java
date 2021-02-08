package com.spring.base.entity.saas;

import java.io.Serializable;

import com.spring.base.entity.SaasBaseEntity;
import com.spring.base.vo.Vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-09 10:48:56
 * @Desc类说明: 发票抬头实体类
 */
@ApiModel
@Data
@ToString(callSuper = true)
@TableName("s_invoice_head")
public class InvoiceHeadEntity extends SaasBaseEntity implements Vo<String>, Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 发票头id
	 */
	@ApiModelProperty(value = "发票头id")
	private String id;
	/**
	 * 登陆人手机号
	 */
	@ApiModelProperty(value = "登陆人手机号")
	@NotBlank(message ="登陆人手机号不能为空")
	private String mobile;
	/**
	 * 公司（发票抬头）
	 */
	@ApiModelProperty(value = "公司（发票抬头）")
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

	/**
	 * 租户Id
	 */
	@ApiModelProperty(value = "租户Id")
	 private String tenantId;
}
