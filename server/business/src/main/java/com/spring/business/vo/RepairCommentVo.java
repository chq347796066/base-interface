package com.spring.business.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author 熊锋
 * @date 2021-01-07 15:28
 * @Describe:
 */
@ApiModel
@Data
@ToString
public class RepairCommentVo {

    /**
     * 评论id
     */
    @ApiModelProperty(value = "评论id")
    private String commentId;

    /**
     * 评论
     */
    @ApiModelProperty(value = "评论")
    private String commentName;

}
