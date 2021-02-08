package com.spring.base.vo.baseinfo.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @description:
 * @author: 赵进华
 * @time: 2020/4/3 15:20
 */
@ApiModel
@Data
@ToString
public class GetRolePowerVo {

    //角色id
    @ApiModelProperty(value = "角色id")
    private String roleId;

    //系统代码
    @ApiModelProperty(value = "系统代码(manage-管理平台,payment-收费系统,app-物业app)")
    private String systemCode;
}
