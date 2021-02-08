package com.spring.base.vo.pay.user;

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

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "token")
    private String token;

    @ApiModelProperty(value = "用户权限")
    private String powerString;

    @ApiModelProperty(value = "角色id")
    private String roleId;

    @ApiModelProperty(value = "公司id")
    private String companyId;
    
    @ApiModelProperty(value = "公司名称")
    private String companyName;
    
    @ApiModelProperty(value = "公司Code")
    private String companyCode;

    @ApiModelProperty(value = "部门id")
    private String deptId;
    
    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "是否是超级管理员")
    private Integer admin;

}
