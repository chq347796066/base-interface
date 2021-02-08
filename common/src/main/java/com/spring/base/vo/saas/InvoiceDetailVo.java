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
 * 开票详情查询
 *
 * @author WuJiaQuan
 */
@ApiModel
@Data
@ToString
public class InvoiceDetailVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 发票与订单关系Id
     */
    @ApiModelProperty(value = "发票与订单关系Id")
    private String invoiceOrderId;

    /**
     * 发票Id
     */
    @ApiModelProperty(value = "发票Id")
    private String invoiceId;

    /**
     * 订单Id
     */
    @ApiModelProperty(value = "订单Id")
    private String orderId;

    /**
     * 订单号
     */
    @ApiModelProperty(value = "订单号")
    private String orderNo;

    /**
     * 订单日期
     */
    @ApiModelProperty(value = "订单日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date orderDate;

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
     * 购买数量
     */
    @ApiModelProperty(value = "购买数量")
    private Integer buyNum;

    /**
     * 订单金额
     */
    @ApiModelProperty(value = "订单金额")
    private BigDecimal orderAmount;

    public InvoiceDetailVo() {
    }

    public String getInvoiceOrderId() {
        return invoiceOrderId;
    }

    public void setInvoiceOrderId(String invoiceOrderId) {
        this.invoiceOrderId = invoiceOrderId;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
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

    public Integer getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(Integer buyNum) {
        this.buyNum = buyNum;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }
}
