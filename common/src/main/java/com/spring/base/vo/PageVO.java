package com.spring.base.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Min;

/**
 * @author 熊锋
 * @date 2021-01-11 13:52
 * @Describe:
 */@ApiModel
@Data
@ToString(callSuper = true)
public class PageVO {

    /**
     * 当前页
    */
    @ApiModelProperty(value = "当前页")
    @Min(value = 1,message = "当前页必须大于等于1")
    private Integer currentPage;

    /**
     * 每页显示多少条
     */
    @ApiModelProperty(value = "每页多少条")
    @Min(value = 1,message="每页显示条数必须大于等于1")
    private Integer pageSize;
}
