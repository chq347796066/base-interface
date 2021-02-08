package com.spring.base.vo.saas;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 订单状况信息Vo
 *
 * @author WuJiaQuan
 */
@ApiModel
public class OrderStatusInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 累计成交金额
     */
    @ApiModelProperty(value = "累计成交金额")
    private BigDecimal amountByAll;

    /**
     * 当月成交金额
     */
    @ApiModelProperty(value = "当月成交金额")
    private BigDecimal amountByThisMonth;

    /**
     * 上个月成交金额
     */
    @ApiModelProperty(value = "上个月成交金额")
    private BigDecimal amountByLastMonth;

    /**
     * 订单状况信息
     */
    @ApiModelProperty(value = "订单状况信息")
    private Map<String, BigDecimal> orderStatusInfoMap;

    /**
     * 时间戳
     */
    @ApiModelProperty(value = "时间戳")
    private List<TimestampYearVo> timestampVoList;

    public OrderStatusInfoVo() {
    }

    public BigDecimal getAmountByAll() {
        return amountByAll;
    }

    public void setAmountByAll(BigDecimal amountByAll) {
        this.amountByAll = amountByAll;
    }

    public BigDecimal getAmountByThisMonth() {
        return amountByThisMonth;
    }

    public void setAmountByThisMonth(BigDecimal amountByThisMonth) {
        this.amountByThisMonth = amountByThisMonth;
    }

    public BigDecimal getAmountByLastMonth() {
        return amountByLastMonth;
    }

    public void setAmountByLastMonth(BigDecimal amountByLastMonth) {
        this.amountByLastMonth = amountByLastMonth;
    }

    public Map<String, BigDecimal> getOrderStatusInfoMap() {
        return orderStatusInfoMap;
    }

    public void setOrderStatusInfoMap(Map<String, BigDecimal> orderStatusInfoMap) {
        this.orderStatusInfoMap = orderStatusInfoMap;
    }

    public List<TimestampYearVo> getTimestampVoList() {
        return timestampVoList;
    }

    public void setTimestampVoList(List<TimestampYearVo> timestampVoList) {
        this.timestampVoList = timestampVoList;
    }
}
