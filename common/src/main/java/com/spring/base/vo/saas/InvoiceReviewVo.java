package com.spring.base.vo.saas;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 开票审核Vo
 *
 * @author WuJiaQuan
 */
public class InvoiceReviewVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 发票Id
     */
    @ApiModelProperty(value = "发票Id")
    private String invoiceId;

    /**
     * 审核状态（1 待审核 2 开票失败（驳回） 3 已开票（通过））
     */
    @ApiModelProperty(value = "审核状态（1 待审核 2 开票失败（驳回） 3 已开票（通过））")
    private Integer invoiceStatus;

    /**
     * 原因
     */
    @ApiModelProperty(value = "原因")
    private String reason;

    public InvoiceReviewVo() {
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Integer getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(Integer invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
