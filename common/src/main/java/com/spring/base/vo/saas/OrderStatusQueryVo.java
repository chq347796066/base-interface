package com.spring.base.vo.saas;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 订单状况信息查询参数Vo
 *
 * @author WuJiaQuan
 */
public class OrderStatusQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 起始时间
     */
    @ApiModelProperty(value = "起始时间")
    private String startDate;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    private String endDate;

    /**
     * 统计时间格式类型
     */
    @ApiModelProperty(value = "统计时间格式类型（1：年；2：月；3：日）")
    private Integer statisticsTimeType;

    public OrderStatusQueryVo() {
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getStatisticsTimeType() {
        return statisticsTimeType;
    }

    public void setStatisticsTimeType(Integer statisticsTimeType) {
        this.statisticsTimeType = statisticsTimeType;
    }
}
