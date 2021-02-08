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
 * 开票摘要查询
 *
 * @author WuJiaQuan
 */
@ApiModel
@Data
@ToString
public class InvoiceSummaryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 发票Id
     */
    @ApiModelProperty(value = "发票Id")
    private String invoiceId;

    /**
     * 发票号码
     */
    @ApiModelProperty(value = "发票号码")
    private String invoiceNum;

    /**
     * 发票状态
     */
    @ApiModelProperty(value = "发票状态")
    private Integer invoiceStatus;

    /**
     * 发票状态枚举值
     */
    @ApiModelProperty(value = "发票状态枚举值")
    private String invoiceStatusValue;

    /**
     * 创建时间（开票时间）
     */
    @ApiModelProperty(value = "创建时间（开票时间）")
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
     * 开票金额
     */
    @ApiModelProperty(value = "开票金额")
    private BigDecimal invoiceAmount;

    /**
     * 发票抬头
     */
    @ApiModelProperty(value = "发票抬头")
    private String invoiceRise;

    /**
     * 发票税号
     */
    @ApiModelProperty(value = "发票税号")
    private String invoiceTaxNo;

    /**
     * 发票类型（发票性质）
     */
    @ApiModelProperty(value = "发票类型（发票性质）")
    private Integer invoiceType;

    /**
     * 发票类型枚举值
     */
    @ApiModelProperty(value = "发票类型枚举值")
    private String invoiceTypeValue;

    /**
     * 开户行
     */
    @ApiModelProperty(value = "开户行")
    private String openBank;

    /**
     * 开户行账号
     */
    @ApiModelProperty(value = "开户行账号")
    private String openBankAccount;

    /**
     * 地址
     */
    @ApiModelProperty(value = "地址")
    private String address;

    /**
     * 电话
     */
    @ApiModelProperty(value = "电话")
    private String tel;

    public InvoiceSummaryVo() {
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getInvoiceNum() {
        return invoiceNum;
    }

    public void setInvoiceNum(String invoiceNum) {
        this.invoiceNum = invoiceNum;
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

    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public String getInvoiceRise() {
        return invoiceRise;
    }

    public void setInvoiceRise(String invoiceRise) {
        this.invoiceRise = invoiceRise;
    }

    public String getInvoiceTaxNo() {
        return invoiceTaxNo;
    }

    public void setInvoiceTaxNo(String invoiceTaxNo) {
        this.invoiceTaxNo = invoiceTaxNo;
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

    public String getOpenBank() {
        return openBank;
    }

    public void setOpenBank(String openBank) {
        this.openBank = openBank;
    }

    public String getOpenBankAccount() {
        return openBankAccount;
    }

    public void setOpenBankAccount(String openBankAccount) {
        this.openBankAccount = openBankAccount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
