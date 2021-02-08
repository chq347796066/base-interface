package com.spring.base.vo.baseinfo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;


/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-31 19:02:26
 * @Desc类说明: 更新用户信息vo
 */
@ApiModel
@Data
@ToString
public class UserResetPasswordVo {
	
	// 主键
	@ApiModelProperty(value = "用户ID")
	@NotNull(message = "用户ID")
	private String id;
	
	//手机
	@ApiModelProperty(value = "手机")
	@NotNull(message = "联系电话不能为空")
	private String mobile;


	 //用户类型(1平台管理员,2物业公司管理员)
	 @ApiModelProperty(value = "用户类型(1平台管理员,2物业公司管理员)")
	 @NotNull(message = "用户类型不能为空")
	 private Integer userType;

	//身份证
	@ApiModelProperty(value = "身份证")
	@NotNull(message = "身份证号不能为空")
	private String idCard;


	
}
