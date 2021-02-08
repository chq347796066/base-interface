package com.spring.business.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author 熊锋
 * @date 2021-01-11 9:47
 * @Describe:报修评论dto
 */
@ApiModel
@Data
@ToString
public class RepairCommentDto {

    /**
     * 报修id
     */
    @ApiModelProperty(value = "报修id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long repairId;

    /**
     * 评论星级
     */
    @ApiModelProperty(value = "评论星级")
    private Integer commentStar;

    /**
     * 评论信息
     */
    @ApiModelProperty(value = "评论信息")
    private String commentName;
}
