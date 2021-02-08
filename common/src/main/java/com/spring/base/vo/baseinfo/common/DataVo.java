package com.spring.base.vo.baseinfo.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @description:数据vo
 * @author: 赵进华
 * @time: 2020/4/9 14:31
 */
@ApiModel
@Data
@ToString
public class DataVo {

    @ApiModelProperty(value = "数据")
    private String data;
}
