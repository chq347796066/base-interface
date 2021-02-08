package com.spring.common.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @author 作者：赵进华
 * @version 创建时间：2019年3月21日 上午9:36:54
 * @Desc类说明:分页基础类
 */
@ApiModel
@Data
@ToString
public class RequestPageVO<T> {

    //当前页
    @NotNull(message = "当前页不能为空！")
    @ApiModelProperty(value = "当前页")
    private Integer currentPage;


    //每页多少条
    @NotNull(message = "每页多少条不能为空！")
    @ApiModelProperty(value = "每页多少条")
    private Integer pageSize;

    //总页数
    @ApiModelProperty(value = "总页数")
    private Integer total;

    @ApiModelProperty(value = "实体类")
    private T entity;

}
