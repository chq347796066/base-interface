package com.spring.base.vo.saas;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 费用账单查询参数
 *
 * @author WuJiaQuan
 */
@ApiModel
@Data
@ToString
public class ExpenseBillQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 订单号
     */
    @ApiModelProperty(value = "订单号或租户编号")
    private String orderNumOrTenantCode;

    /**
     * 付款方式（1 微信支付；2：支付宝）
     */
    @ApiModelProperty(value = "付款方式（1 微信支付；2：支付宝）")
    private Integer payType;

    /**
     * 订单支付状态（1 待支付；2 支付成功;3 已取消；4 支付失败）
     */
    @ApiModelProperty(value = "订单支付状态（1 待支付；2 支付成功;3 已取消；4 支付失败）")
    private Integer orderStatus;

    /**
     * 下单时间-起始时间
     */
    @ApiModelProperty(value = "下单时间-起始时间")
    private String startDate;

    /**
     * 下单时间-结束时间
     */
    @ApiModelProperty(value = "下单时间-结束时间")
    private String endDate;

    public ExpenseBillQueryVo() {
    }

    public String getOrderNumOrTenantCode() {
        return orderNumOrTenantCode;
    }

    public void setOrderNumOrTenantCode(String orderNumOrTenantCode) {
        this.orderNumOrTenantCode = orderNumOrTenantCode;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
