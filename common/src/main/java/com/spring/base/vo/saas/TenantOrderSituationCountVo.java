package com.spring.base.vo.saas;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 租户订购情况应用订购统计Vo
 *
 * @author WuJiaQuan
 */
@ApiModel
public class TenantOrderSituationCountVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 试用租户统计
     */
    @ApiModelProperty(value = "试用统计")
    private Long trialCount;

    /**
     * 订购租户统计
     */
    @ApiModelProperty(value = "订购统计")
    private Long orderCount;

    /**
     * 续费租户统计
     */
    @ApiModelProperty(value = "订购统计")
    private Long renewalCount;

    /**
     * 升级统计
     */
    @ApiModelProperty(value = "升级统计")
    private Long upgradeCount;

    public TenantOrderSituationCountVo() {
    }

    public Long getTrialCount() {
        return trialCount;
    }

    public void setTrialCount(Long trialCount) {
        this.trialCount = trialCount;
    }

    public Long getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Long orderCount) {
        this.orderCount = orderCount;
    }

    public Long getRenewalCount() {
        return renewalCount;
    }

    public void setRenewalCount(Long renewalCount) {
        this.renewalCount = renewalCount;
    }

    public Long getUpgradeCount() {
        return upgradeCount;
    }

    public void setUpgradeCount(Long upgradeCount) {
        this.upgradeCount = upgradeCount;
    }
}
