package com.spring.business.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * @description:确认支付VO
 * @author: 赵进华
 * @time: 2021/1/6 19:13
 */
@ApiModel
@Data
@ToString
public class ConfirmPayVo {
    @ApiModelProperty(value = "报修id")
    private Long repairId;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 图片路径
     */
    @ApiModelProperty(value = "图片路径")
    private List<String> picUrlList;
}
