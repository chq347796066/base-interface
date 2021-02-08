package com.spring.base.vo.baseinfo.customer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;


/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-03 17:35:27
 * @Desc类说明: 更新客户信息vo
 */
@ApiModel
@Data
@ToString
public class CustomerUpdateVo  {
	
	// 主键
	@ApiModelProperty(value = " 主键")
	@NotNull(message = "主键id不能为空")
	private String id;
	
	//小区id
	@ApiModelProperty(value = "小区id")
	@NotNull(message = "小区id不能为空")
	private String communityId;
	
	//小区名称
	@ApiModelProperty(value = "小区名称")
	@NotNull(message = "小区名称不能为空")
	private String communityName;
	
	//客户类型 1 个人 2单位 3开发商
	@ApiModelProperty(value = "客户类型 1 个人 2单位 3开发商")
	@NotNull(message = "客户类型不能为空")
	private String customerType;
	
	//客户姓名
	@ApiModelProperty(value = "客户姓名")
	@NotNull(message = "客户姓名不能为空")
	private String customerName;
	
	//性别(1男,2女)
	@ApiModelProperty(value = "性别(1男,2女)")
	@NotNull(message = "客户性别不能为空")
	private Integer sex;
	
	//证件类型 1 身份证
	@ApiModelProperty(value = "证件类型 1 身份证")
	@NotNull(message = "证件类型不能为空")
	private Integer certificatesType;
	
	//证件号码
	@ApiModelProperty(value = "证件号码")
	@NotNull(message = "证件号码不能为空")
	private String certificatesNumber;
	
	//联系电话
	@ApiModelProperty(value = "联系电话")
	@NotNull(message = "联系电话不能为空")
	private String phone;
	
	//籍贯
	@ApiModelProperty(value = "籍贯")
	private String nativePlace;
	
	//学历
	@ApiModelProperty(value = "学历")
	private String education;
	
	//职业
	@ApiModelProperty(value = "职业")
	private String occupation;
	
	//客户地址
	@ApiModelProperty(value = "客户地址")
	private String communityAddress;
	
	//删除标志（0 未删除 1 已删除）
	@ApiModelProperty(value = "删除标志（0 未删除 1 已删除）")
	private Integer delFlag;
	
	//租户
	@ApiModelProperty(value = "租户")
	private String tenant;
	
}
