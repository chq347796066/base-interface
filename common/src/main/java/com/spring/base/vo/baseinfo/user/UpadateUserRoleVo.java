package com.spring.base.vo.baseinfo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-31 19:02:26
 * @Desc类说明: 更新用户信息vo
 */
@ApiModel
@Data
@ToString
public class UpadateUserRoleVo {

    @ApiModelProperty(value = "用户ID")
    @NotNull(message = "用户id不能为空")
    private String userId;

    @ApiModelProperty(value = "角色")
    private String roleId;

}
