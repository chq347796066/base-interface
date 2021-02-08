package com.spring.base.vo.saas;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 租户订购情况Vo
 *
 * @author WuJiaQuan
 */
@ApiModel
public class TenantOrderSituationVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 有效租户
     */
    @ApiModelProperty(value = "有效租户数")
    private Long effectiveTenantCount;

    /**
     * 租户应用订购统计
     */
    @ApiModelProperty(value = "租户应用订购统计")
    private Map<String, TenantOrderSituationCountVo> tenantOrderSituationCountVoMap;

    /**
     * 时间戳
     */
    @ApiModelProperty(value = "时间戳")
    private List<TimestampYearVo> timestampVoList;

    public TenantOrderSituationVo() {
    }

    public Long getEffectiveTenantCount() {
        return effectiveTenantCount;
    }

    public void setEffectiveTenantCount(Long effectiveTenantCount) {
        this.effectiveTenantCount = effectiveTenantCount;
    }

    public Map<String, TenantOrderSituationCountVo> getTenantOrderSituationCountVoMap() {
        return tenantOrderSituationCountVoMap;
    }

    public void setTenantOrderSituationCountVoMap(Map<String, TenantOrderSituationCountVo> tenantOrderSituationCountVoMap) {
        this.tenantOrderSituationCountVoMap = tenantOrderSituationCountVoMap;
    }

    public List<TimestampYearVo> getTimestampVoList() {
        return timestampVoList;
    }

    public void setTimestampVoList(List<TimestampYearVo> timestampVoList) {
        this.timestampVoList = timestampVoList;
    }
}
