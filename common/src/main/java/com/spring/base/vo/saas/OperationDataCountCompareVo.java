package com.spring.base.vo.saas;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 运营数据统计环比Vo
 *
 * @author WuJiaQuan
 */
public class OperationDataCountCompareVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 本月新增租户数
     */
    @ApiModelProperty(value = "本月新增租户数")
    private Integer newTenantNum;

    /**
     * 本月新增租户数环比上月百分比
     */
    @ApiModelProperty(value = "本月新增租户数环比上月百分比")
    private Integer newTenantComparePer;

    /**
     * 本月续费租户数
     */
    @ApiModelProperty(value = "本月续费租户数")
    private Integer renewTenementNum;

    /**
     * 本月续费租户数环比上月百分比
     */
    @ApiModelProperty(value = "本月续费租户数环比上月百分比")
    private Integer renewTenementComparePer;

    /**
     * 本月订单金额
     */
    @ApiModelProperty(value = "本月订单金额")
    private Integer orderAmount;

    /**
     * 本月订单金额相比上月百分比
     */
    @ApiModelProperty(value = "本月订单金额相比上月百分比")
    private Integer orderAmountComparePer;

    /**
     * 本月到期租户数
     */
    @ApiModelProperty(value = "本月到期租户数")
    private Integer tenantDueNum;

    /**
     * 本月到期租户数环比上月百分比
     */
    @ApiModelProperty(value = "本月到期租户数环比上月百分比")
    private Integer tenantDueComparePer;

    public OperationDataCountCompareVo() {

    }

    public Integer getNewTenantNum() {
        return newTenantNum;
    }

    public void setNewTenantNum(Integer newTenantNum) {
        this.newTenantNum = newTenantNum;
    }

    public Integer getNewTenantComparePer() {
        return newTenantComparePer;
    }

    public void setNewTenantComparePer(Integer newTenantComparePer) {
        this.newTenantComparePer = newTenantComparePer;
    }

    public Integer getRenewTenementNum() {
        return renewTenementNum;
    }

    public void setRenewTenementNum(Integer renewTenementNum) {
        this.renewTenementNum = renewTenementNum;
    }

    public Integer getRenewTenementComparePer() {
        return renewTenementComparePer;
    }

    public void setRenewTenementComparePer(Integer renewTenementComparePer) {
        this.renewTenementComparePer = renewTenementComparePer;
    }

    public Integer getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Integer orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Integer getOrderAmountComparePer() {
        return orderAmountComparePer;
    }

    public void setOrderAmountComparePer(Integer orderAmountComparePer) {
        this.orderAmountComparePer = orderAmountComparePer;
    }

    public Integer getTenantDueNum() {
        return tenantDueNum;
    }

    public void setTenantDueNum(Integer tenantDueNum) {
        this.tenantDueNum = tenantDueNum;
    }

    public Integer getTenantDueComparePer() {
        return tenantDueComparePer;
    }

    public void setTenantDueComparePer(Integer tenantDueComparePer) {
        this.tenantDueComparePer = tenantDueComparePer;
    }
}
