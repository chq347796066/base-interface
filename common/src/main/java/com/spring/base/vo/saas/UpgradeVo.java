package com.spring.base.vo.saas;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @description:升级应用vo
 * @author: 赵进华
 * @time: 2020/7/14 18:09
 */
@ApiModel
@Data
@ToString
public class UpgradeVo {

    @ApiModelProperty(value = "租户Id")
    @NotBlank(message = "租户Id不能为空")
    private String tenantId;

    @ApiModelProperty(value = "角色Id")
    @NotBlank(message = "角色Id不能为空")
    private String roleId;
}
