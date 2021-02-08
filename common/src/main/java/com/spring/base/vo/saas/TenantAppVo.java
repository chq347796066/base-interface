package com.spring.base.vo.saas;

import com.spring.common.util.date.DateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * 租户应用关系查询
 *
 * @author WuJiaQuan
 */
@ApiModel
@Data
@ToString
public class TenantAppVo {

    /**
     * 租户应用Id（格式：租户主键Id + | + 应用主键Id）
     */
    @ApiModelProperty(value = "租户应用Id（格式：租户主键Id + | + 应用主键Id）")
    private String tenantAppId;

    /**
     * 租户Id
     */
    @ApiModelProperty(value = "租户Id")
    private String tenantId;

    /**
     * 租户名称
     */
    @ApiModelProperty(value = "租户名称")
    private String tenantName;

    /**
     * 应用Id
     */
    @ApiModelProperty(value = "应用Id")
    private String applicationId;

    /**
     * 应用名称
     */
    @ApiModelProperty(value = "应用名称")
    private String applicationName;

    /**
     * 租户应用到期时间
     */
    @ApiModelProperty(value = "到期时间")
    private DateTime expireDate;

    public TenantAppVo() {
    }

    public String getTenantAppId() {
        return tenantAppId;
    }

    public void setTenantAppId(String tenantAppId) {
        this.tenantAppId = tenantAppId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public DateTime getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(DateTime expireDate) {
        this.expireDate = expireDate;
    }
}
