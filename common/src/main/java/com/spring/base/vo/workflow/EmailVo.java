package com.spring.base.vo.workflow;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * 邮件vo
 * @author dell
 */
@ApiModel
@Data
@ToString(callSuper = true)
public class EmailVo {

    @ApiModelProperty(value = "email")
    private String email;

    @ApiModelProperty(value = "项目编号")
    private String projectNo;
}
