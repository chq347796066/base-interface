package com.spring.base.vo.baseinfo.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @description:saas角色新增vo
 * @author: 赵进华
 * @time: 2020/7/2 10:24
 */
@ApiModel
@Data
@ToString
public class SaasRoleVo {
    //角色Id
    @ApiModelProperty(value = "角色Id")
    private String roleId;

    //角色名称
    @ApiModelProperty(value = "角色名称")
    private String roleName;

    //角色描述
    @ApiModelProperty(value = "角色描述")
    private String roleDesc;

    //菜单列表
    @ApiModelProperty(value = "菜单列表")
    private String[] menuIdList;

    /**
     * 0 扩展应用,1 试用应用 , 2主应用
     */
    @ApiModelProperty(value = "0 扩展应用,1 试用应用 , 2主应用")
    private Integer appType;
}
