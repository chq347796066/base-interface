package com.spring.business.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * @description:维修完成VO
 * @author: 赵进华
 * @time: 2021/1/6 17:05
 */
@ApiModel
@Data
@ToString
public class RepairFinishVo {

    @ApiModelProperty(value = "报修id")
    private Long repairId;

    /**
     * 维修结果
     */
    @ApiModelProperty(value = "维修结果")
    private String repairResult;

    /**
     * 维修金额
     */
    @ApiModelProperty(value = "维修金额")
    private BigDecimal cost=new BigDecimal("0");

    /**
     * 图片路径
     */
    @ApiModelProperty(value = "图片路径")
    private List<String> picUrlList;
}
