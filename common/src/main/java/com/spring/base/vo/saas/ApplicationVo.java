package com.spring.base.vo.saas;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

/**
 * @author: 熊锋
 * @Date: 2020-07-06 17:12
 * @Description:
 */
@ApiModel
@Data
@ToString
public class ApplicationVo {

    /**
     * 应用名称
     */
    @ApiModelProperty(value = "应用名称")
    private String appName;

    /**
     * 公司名称
     */
    @ApiModelProperty(value = "应用图片")
    private String appPic;

    /**
     * 应用id
     */
    @ApiModelProperty(value = "应用id")
    private String applicationId;

    /**
     * 到期时间
     */
    @ApiModelProperty(value = "到期时间")
    private LocalDate expireDate;

    /**
     * 订单类型
     */
    @ApiModelProperty(value = "订单类型 1.试用，2.订购，3.续费，4.升级")
    private Integer[] appStatus;

    /**
     * 订单状态
     */
    @ApiModelProperty(value = "订单类型 1 待支付；2 支付成功;3 已取消；4 支付失败 5 无需支付")
    private Integer orderStatus;

    /**
     * 应用url
     */
    @ApiModelProperty(value = "应用url")
    private String url;

    /**
     * 应用价格
     */
    @ApiModelProperty(value = "应用价格")
    private String appPrice;

    /**
     * 应用id
     */
    @ApiModelProperty(value = "应用id")
    private String appId;
}
