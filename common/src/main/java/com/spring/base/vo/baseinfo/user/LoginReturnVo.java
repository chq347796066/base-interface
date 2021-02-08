package com.spring.base.vo.baseinfo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author 作者：赵进华
 * @version 创建时间：2019年5月23日 上午11:11:59
 * @Desc类说明:登录后返回vo
 */
@ApiModel
@Data
@ToString
public class LoginReturnVo {
    @ApiModelProperty(value = "主键ID")
    private String id;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "用户名")
    private String userCode;

    //用户昵称
    @ApiModelProperty(value = "用户昵称")
    private String userNickname;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "token")
    private String token;

    @ApiModelProperty(value = "用户权限")
    private String powerString;

    @ApiModelProperty(value = "角色id")
    private String roleId;

    @ApiModelProperty(value = "租户id")
    private String tenantId;

    @ApiModelProperty(value = "公司id")
    private String companyId;

    @ApiModelProperty(value = "小区ID")
    private String communityId;

    @ApiModelProperty(value = "用户LOGO头像图片")
    private String userLogoId;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "电话号码")
    private String mobile;

    @ApiModelProperty(value = "证件号码")
    private String idCard;

    @ApiModelProperty(value = "个人信息是否完善")
    private Integer statusTyeps;

    //删除标志（0 未删除 1 已删除）
    @ApiModelProperty(value = "删除标志")
    private Integer delFlag;

    //用户状态(1:启用或2:禁用)
    @ApiModelProperty(value = "用户状态")
    private Integer enableStatusFlag;

    // 是否是超级管理员：0不是，1是
    @ApiModelProperty(value = "是否是超级管理员：0不是，1是")
    private Integer admin;

    @ApiModelProperty(value = "用户类型(1平台管理员,2物业公司管理员)")
    private String  userType;

    /**
     * 是否是saas用户
     */
    @ApiModelProperty(value = "0非saas用户,1saas用户")
    private Integer isSaas;

    /**
     * 用户岗位id
     */
    @ApiModelProperty(value = "用户岗位id")
    private String jobId;

}
