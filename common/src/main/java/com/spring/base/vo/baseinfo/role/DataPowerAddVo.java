package com.spring.base.vo.baseinfo.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @description:
 * @author: 赵进华
 * @time: 2020/4/10 15:39
 */
@ApiModel
@Data
@ToString
public class DataPowerAddVo {
    //id
    @ApiModelProperty(value = "字典id")
    private String dictId;

    //代码
    @ApiModelProperty(value = "字典代码")
    private String dictCode;

    //名称
    @ApiModelProperty(value = "字典名称")
    private String dictName;

    //父ID
    @ApiModelProperty(value = "父ID")
    private String dictParentId;

    //角色id
    @ApiModelProperty(value = "角色id")
    private String roleId;

    //权限类型
    @ApiModelProperty(value = "权限类型")
    private String powerType;
}
