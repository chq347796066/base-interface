package com.spring.base.vo.saas;

import com.spring.common.util.date.DateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 费用账单分页查询
 *
 * @author WuJiaQuan
 */
@ApiModel
@Data
@ToString
public class ExpenseBillPageVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 订单id
     */
    @ApiModelProperty(value = "订单id")
    private String orderId;

    /**
     * 订单编号
     */
    @ApiModelProperty(value = "订单编号")
    private String orderNum;

    /**
     * 订单类型（1.试用，2.订购，3.续费，4.升级）
     */
    @ApiModelProperty(value = "订单类型（1.试用，2.订购，3.续费，4.升级）")
    private Integer orderType;

    /**
     * 订单类型枚举值
     */
    @ApiModelProperty(value = "订单类型枚举值")
    private String orderTypeValue;

    /**
     * 订单金额
     */
    @ApiModelProperty(value = "订单金额")
    private BigDecimal orderAmount;

    /**
     * 下单时间
     */
    @ApiModelProperty(value = "下单时间")
    private String orderDate;

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
     * 订单支付状态（1 待支付；2 支付成功;3 已取消；4 支付失败）
     */
    @ApiModelProperty(value = "订单支付状态（1 待支付；2 支付成功;3 已取消；4 支付失败）")
    private Integer orderStatus;

    /**
     * 订单支付状态枚举值
     */
    @ApiModelProperty(value = "订单支付状态枚举值")
    private String orderStatusValue;

    /**
     * 付款方式（1 微信支付；2：支付宝）
     */
    @ApiModelProperty(value = "付款方式（1 微信支付；2：支付宝）")
    private Integer payType;

    /**
     * 付款方式枚举值
     */
    @ApiModelProperty(value = "付款方式枚举值")
    private String payTypeValue;

    /**
     * 支付流水号
     */
    @ApiModelProperty(value = "支付流水号")
    private String payNum;

    /**
     * 支付时间
     */
    @ApiModelProperty(value = "支付时间")
    private DateTime payDate;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;

    public ExpenseBillPageVo() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getOrderTypeValue() {
        return orderTypeValue;
    }

    public void setOrderTypeValue(String orderTypeValue) {
        this.orderTypeValue = orderTypeValue;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
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

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatusValue() {
        return orderStatusValue;
    }

    public void setOrderStatusValue(String orderStatusValue) {
        this.orderStatusValue = orderStatusValue;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getPayTypeValue() {
        return payTypeValue;
    }

    public void setPayTypeValue(String payTypeValue) {
        this.payTypeValue = payTypeValue;
    }

    public String getPayNum() {
        return payNum;
    }

    public void setPayNum(String payNum) {
        this.payNum = payNum;
    }

    public DateTime getPayDate() {
        return payDate;
    }

    public void setPayDate(DateTime payDate) {
        this.payDate = payDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
