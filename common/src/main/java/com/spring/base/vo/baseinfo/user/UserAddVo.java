package com.spring.base.vo.baseinfo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;


/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-31 19:02:26
 * @Desc类说明: 新增用户信息vo
 */
 
@ApiModel
@Data
@ToString
public class UserAddVo {
	
	//公司id
	@ApiModelProperty(value = "公司id")
	private String companyId;
	
	//小区id
	@ApiModelProperty(value = "小区id")
	private String communityId;
	
	//用户名
	@ApiModelProperty(value = "用户Code码")
	private String userCode;

	@ApiModelProperty(value = "用户昵称")
	private String userNickname;
	//用户姓名
	@ApiModelProperty(value = "用户姓名")
	@NotNull(message = "用户姓名不能为空")
	private String userName;
	
	//手机
	@ApiModelProperty(value = "手机")
	@NotNull(message = "联系电话不能为空")
	private String mobile;
	
	//性别(1男,2女)
	@ApiModelProperty(value = "性别(1男,2女)")
	@NotNull(message = "性别不能为空")
	private Integer sex;
	
	//生日
	@ApiModelProperty(value = "生日")
	private String birthday;
	
	//身份证
	@ApiModelProperty(value = "身份证")
	@NotNull(message = "身份证号码不能为空")
	private String idCard;
	
	//地址
	@ApiModelProperty(value = "地址")
	private String address;
	
	//籍贯
	@ApiModelProperty(value = "籍贯")
	private String nativePlace;
	
	//角色
	@ApiModelProperty(value = "角色")
	private String roleId;
	
	//登录开关(1开,2关)
	@ApiModelProperty(value = "登录开关(1开,2关)")
	private Integer loginStatus;

	//状态(1在职,2离职)
	@ApiModelProperty(value = "状态(1在职,2离职)")
	private Integer status;

	 //用户类型(1平台管理员,2物业公司管理员)
	 @ApiModelProperty(value = "用户类型(1平台管理员,2物业公司管理员)")
	 private String userType;

	 //平台权限(0管理平台,1物业APP,2收费系统)
	 @ApiModelProperty(value = "平台权限(0管理平台,1物业APP,2收费系统)")
	 private String platformAuthority;

	@ApiModelProperty(value = "用户状态(1:启用或2:禁用)")
	private Integer enableStatusFlag;

	//租户公司数组
	@ApiModelProperty(value = "租户公司数组")
	private String[] tenantCompanyArray;

	//职位
	@ApiModelProperty(value = "职位id")
	private String jobId;
 }
