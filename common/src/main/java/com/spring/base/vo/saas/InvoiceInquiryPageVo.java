package com.spring.base.vo.saas;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 开票查询分页查询
 *
 * @author WuJiaQuan
 */
@ApiModel
@Data
@ToString
public class InvoiceInquiryPageVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 发票Id
     */
    @ApiModelProperty(value = "发票Id")
    private String invoiceId;

    /**
     * 申请开票时间
     */
    @ApiModelProperty(value = "申请开票时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date invoiceDate;

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
     * 发票抬头Id
     */
    @ApiModelProperty(value = "发票抬头Id")
    private String invoiceHeadId;

    /**
     * 发票抬头
     */
    @ApiModelProperty(value = "发票抬头（开票公司名称）")
    private String invoiceRise;

    /**
     * 开票性质
     */
    @ApiModelProperty(value = "发开票性质（发票类型）")
    private Integer invoiceType;

    /**
     * 开票性质枚举值
     */
    @ApiModelProperty(value = "开票性质枚举值")
    private String invoiceTypeValue;

    /**
     * 开票总额
     */
    @ApiModelProperty(value = "开票总额")
    private BigDecimal invoiceAmount;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态（1 待审核 2 开票失败 3 已开票）")
    private Integer invoiceStatus;

    /**
     * 状态枚举值
     */
    @ApiModelProperty(value = "状态枚举值（1 待审核 2 开票失败 3 已开票）")
    private String invoiceStatusValue;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String description;

    public InvoiceInquiryPageVo() {
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
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

    public String getInvoiceHeadId() {
        return invoiceHeadId;
    }

    public void setInvoiceHeadId(String invoiceHeadId) {
        this.invoiceHeadId = invoiceHeadId;
    }

    public String getInvoiceRise() {
        return invoiceRise;
    }

    public void setInvoiceRise(String invoiceRise) {
        this.invoiceRise = invoiceRise;
    }

    public Integer getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Integer invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceTypeValue() {
        return invoiceTypeValue;
    }

    public void setInvoiceTypeValue(String invoiceTypeValue) {
        this.invoiceTypeValue = invoiceTypeValue;
    }

    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public Integer getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(Integer invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public String getInvoiceStatusValue() {
        return invoiceStatusValue;
    }

    public void setInvoiceStatusValue(String invoiceStatusValue) {
        this.invoiceStatusValue = invoiceStatusValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
