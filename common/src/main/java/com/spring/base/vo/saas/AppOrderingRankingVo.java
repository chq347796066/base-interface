package com.spring.base.vo.saas;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 应用订购排行榜Vo
 *
 * @author WuJiaQuan
 */
public class AppOrderingRankingVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 应用Id
     */
    private String appId;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 应用订购数量
     */
    private Long appBuyCount;

    /**
     * 占比
     */
    private BigDecimal proportion;

    public AppOrderingRankingVo() {
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Long getAppBuyCount() {
        return appBuyCount;
    }

    public void setAppBuyCount(Long appBuyCount) {
        this.appBuyCount = appBuyCount;
    }

    public BigDecimal getProportion() {
        return proportion;
    }

    public void setProportion(BigDecimal proportion) {
        this.proportion = proportion;
    }
}
