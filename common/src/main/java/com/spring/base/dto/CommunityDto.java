package com.spring.base.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author 熊锋
 * @date 2021-01-11 18:00
 * @Describe: 项目信息dto
 */
@ApiModel
@Data
@ToString
public class CommunityDto {

    /**
     * 项目id
     */
    @ApiModelProperty(value = "项目id")
    private String communityId;

    /**
     * 项目名称
     */
    @ApiModelProperty(value = "项目名称")
    private String communityName;
}
