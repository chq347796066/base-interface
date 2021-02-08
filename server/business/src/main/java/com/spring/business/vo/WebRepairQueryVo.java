package com.spring.business.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.base.vo.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * @author 熊锋
 * @date 2021-01-06 17:42
 * @Describe:
 */
@ApiModel
@Data
@ToString
public class WebRepairQueryVo extends PageVO {

    /**
     * 报修状态
     */
    @ApiModelProperty(value = "报修状态")
    private Integer repairStatus;

    /**
     * 项目id
     */
    @ApiModelProperty(value = "项目id集合")
    private List<String> communityIds;

    /**
     * 项目id
     */
    @ApiModelProperty(value = "项目id")
    private String communityId;

    /**
     * 模糊搜索条件（工单号/姓名/手机号）
     */
    @ApiModelProperty(value = "模糊搜索条件（工单号/姓名/手机号）")
    private String dimConditions;

    /**
     * 报修区域
     */
    @ApiModelProperty(value = "报修区域")
    private Integer repairRegion;

    /**
     * 报修开始时间
     */
    @ApiModelProperty(value = "报修开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date repairStartTime;

    /**
     * 报修结束时间
     */
    @ApiModelProperty(value = "报修结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date repairEndTime;

    /**
     * 查询周期/日期
     */
    @ApiModelProperty(value = "周期标识(0 今日,1 昨日,2 本周)")
    private Integer periodFlag;

    /**
     * 本周开始时间
     */
    @ApiModelProperty(value = "周期")
    private String periodStartTime;

    /**
     * 本周结束时间
     */
    @ApiModelProperty(value = "周期")
    private String periodEndTime;

}
