package com.spring.baseinfo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @description:
 * @author: 赵进华
 * @time: 2021/1/8 17:32
 */
@ApiModel
@Data
@ToString
public class UserJobVo {

    @ApiModelProperty(value = "岗位")
    private String job;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "用户Id")
    private String userId;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "岗位id")
    private String jobId;
}
