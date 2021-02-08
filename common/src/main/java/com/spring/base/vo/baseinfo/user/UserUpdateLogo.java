package com.spring.base.vo.baseinfo.user;

import com.spring.base.entity.baseinfo.PicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * @date : 创建时间：2020-03-31 19:02:26
 * @Desc类说明: 更新用户信息vo
 * @author dell
 */
@ApiModel
@Data
public class UserUpdateLogo {
	
	// 主键
	@ApiModelProperty(value = " 主键")
	@NotNull(message = "主键id不能为空")
	private String id;

	@ApiModelProperty(value = "用户LOGO头像图片")
	private String userLogoId;

	//用户名
	@ApiModelProperty(value = "用户名")
	private String userCode;

	@ApiModelProperty(value = "公司id")
	private String companyId;
	
	//小区id
	@ApiModelProperty(value = "小区id")
	private String communityId;

	//用户昵称
	@ApiModelProperty(value = "用户昵称")
	private String userNickname;

	//用户姓名
	@ApiModelProperty(value = "用户姓名")
	private String userName;

	//手机
	@ApiModelProperty(value = "手机")
	private String mobile;
	
	//性别(1男,2女)
	@ApiModelProperty(value = "性别(1男,2女)")
	private Integer sex;
	
	//身份证
	@ApiModelProperty(value = "身份证")
	private String idCard;

	//用户LOGO头像图片
	@ApiModelProperty(value = "用户LOGO头像图片)")
	private List<PicEntity> picEntityList;
	
}
