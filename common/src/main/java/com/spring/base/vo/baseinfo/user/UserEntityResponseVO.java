package com.spring.base.vo.baseinfo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author dell
 */
@ApiModel
@Data
@ToString(callSuper = true)
public class UserEntityResponseVO {


    // 主键
    @ApiModelProperty(value = " 主键")
    private String id;

    // 租户id
    @ApiModelProperty(value = "租户id")
    private String tenantId;

    // 租户名称
    @ApiModelProperty(value = "租户名称")
    private String tenantName;

    //公司id
    @ApiModelProperty(value = "公司id")
    private String companyId;

    //公司名称
     @ApiModelProperty(value = "公司名称")
    private String companyName;

    //公司详细层级
    @ApiModelProperty(value = "公司详细层级")
    private String companyNameList;

    //小区id
    @ApiModelProperty(value = "小区id")
    private String communityId;

    //小区名称
    @ApiModelProperty(value = "小区名称")
    private String communityName;

    //用户名
    @ApiModelProperty(value = "用户名")
    private String userCode;

    @ApiModelProperty(value = "用户昵称")
    private String userNickname;

    //用户姓名
    @ApiModelProperty(value = "用户姓名")
    private String userName;

    //密码
    @ApiModelProperty(value = "密码")
    private String password;

    //手机
    @ApiModelProperty(value = "手机")
    private String mobile;

    //岗位
    @ApiModelProperty(value = "岗位")
    private String jobId;

    //性别(1男,2女)
    @ApiModelProperty(value = "性别(1男,2女)")
    private Integer sex;

    //生日
    @ApiModelProperty(value = "生日")
    private String birthday;

    //身份证
    @ApiModelProperty(value = "身份证")
    private String idCard;

    //地址
    @ApiModelProperty(value = "地址")
    private String address;

    //籍贯
    @ApiModelProperty(value = "籍贯")
    private String nativePlace;

    //角色id
    @ApiModelProperty(value = "角色id")
    private String roleId;

    //登录开关(1开,2关)
    @ApiModelProperty(value = "登录开关(1开,2关)")
    private Integer loginStatus;

    //用户类型(1平台管理员,2物业公司管理员)
    @ApiModelProperty(value = "住户类型（1:业主；2：亲属；3:租客；4：游客；5：未注册）")
    private String  houseHoldType;

    //登录时间
    @ApiModelProperty(value = "登录时间")
    private Date loginTime;

    //启用状态(1启用,2禁用)
    @ApiModelProperty(value = "启用状态(1启用,2禁用)")
    private Integer enableStatusFlag;

    //启用状态(1启用,2禁用)
    @ApiModelProperty(value = "启用状态(1启用,2禁用)")
    private Integer status;

    //用户类型(1平台管理员,2物业公司管理员)
    @ApiModelProperty(value = "用户类型(01平台管理员,02物业公司管理员,03业主app)")
    private String  userType;

    //平台权限(0管理平台,1物业APP,2收费系统)
    @ApiModelProperty(value = "平台权限(0管理平台,1物业APP,2收费系统)")
    private String platformAuthority;

    //渠道来源(1:android,2:ios,3:pc)
    @ApiModelProperty(value = "渠道来源(1:android,2:ios,3:pc)")
    private Integer sourceChannel;

    //用户图片
	 @ApiModelProperty(value = "用户图片")
	 private String url;

    //创建时间
    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    //状态名称(启用或禁用)
    @ApiModelProperty(value = "状态名称")
    private String statusName;

    //角色名称
    @ApiModelProperty(value = "角色名称")
    private String roleName;

    // 是否是超级管理员：0不是，1是
    @ApiModelProperty(value = "是否是超级管理员：0不是，1是")
    private Integer admin;

    //租户公司数组
    @ApiModelProperty(value = "租户公司数组")
    private String[] tenantCompanyArray;
}
