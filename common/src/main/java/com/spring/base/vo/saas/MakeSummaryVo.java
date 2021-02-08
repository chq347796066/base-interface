package com.spring.base.vo.saas;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 熊锋
 * @version 1.0.0
 * CreateDate 2020/7/22 10:30
 * description 租户后台发票摘要vo
 */
@Data
public class MakeSummaryVo {

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
     * 创建时间（开票时间）
     */
    @ApiModelProperty(value = "创建时间（开票时间）")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date applyTime;

    /**
     * 租户名称
     */
    @ApiModelProperty(value = "租户名称")
    private String companyName;

    /**
     * 开票金额
     */
    @ApiModelProperty(value = "开票金额")
    private String invoiceAmount;

    /**
     * 发票税号
     */
    @ApiModelProperty(value = "发票税号")
    private String taxNum;

    /**
     * 发票类型（发票性质）
     */
    @ApiModelProperty(value = "发票类型（发票性质）0:增值税普通发票；1:增值税专用发票")
    private Integer invoiceType;

    /**
     * 开户行
     */
    @ApiModelProperty(value = "开户行")
    private String openBank;

    /**
     * 开户行账号
     */
    @ApiModelProperty(value = "开户行账号")
    private String openAccount;

    /**
     * 地址
     */
    @ApiModelProperty(value = "地址")
    private String address;

    /**
     * 开票描述
     */
    @ApiModelProperty(value = "开票描述")
    private String description;

    /**
     * 电话
     */
    @ApiModelProperty(value = "电话")
    private String tel;

    /**
     * 拒绝原因
     */
    @ApiModelProperty(value = "拒绝原因")
    private String reason;

}
