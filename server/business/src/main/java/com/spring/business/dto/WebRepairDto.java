package com.spring.business.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 熊锋
 * @date 2021-01-07 16:16
 * @Describe:
 */
@ApiModel
@Data
@ToString
public class WebRepairDto {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 报修单号
     */
    @ApiModelProperty(value = "报修单号")
    private String repairOrder;

    /**
     * 报修单类型(1业主报修,2代客报修)
     */
    @ApiModelProperty(value = "报修单类型(1业主报修,2代客报修)")
    private Integer orderType;

    /**
     * 报修区域
     */
    @ApiModelProperty(value = "报修区域")
    private Integer repairRegion;

    /**
     * 报修类型
     */
    @ApiModelProperty(value = "报修类型")
    private String repairType;

    /**
     * 报修描述
     */
    @ApiModelProperty(value = "报修描述")
    private String content;

    /**
     * 业主id
     */
    @ApiModelProperty(value = "业主id")
    private String ownerId;

    /**
     * 业主姓名
     */
    @ApiModelProperty(value = "业主姓名")
    private String ownerName;

    /**
     * 业主电话
     */
    @ApiModelProperty(value = "业主电话")
    private String phone;

    /**
     * 业主地址
     */
    @ApiModelProperty(value = "业主地址")
    private String address;

    /**
     * 期望开始上门时间
     */
    @ApiModelProperty(value = "期望开始上门时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date hopeBeginTime;

    /**
     * 期望结束上门时间
     */
    @ApiModelProperty(value = "期望结束上门时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date hopeEndTime;

    /**
     * 维修人
     */
    @ApiModelProperty(value = "维修人")
    private String handleUser;

    /**
     * 维修金额
     */
    @ApiModelProperty(value = "维修金额")
    private BigDecimal cost;

    /**
     * 维修结果
     */
    @ApiModelProperty(value = "维修结果")
    private String repairResult;

    /**
     * 开始处理时间
     */
    @ApiModelProperty(value = "开始处理时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startDate;

    /**
     * 完成时间
     */
    @ApiModelProperty(value = "完成时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date finishDate;

    /**
     * 小区id
     */
    @ApiModelProperty(value = "小区id")
    private String communityId;

    /**
     * 公司id
     */
    @ApiModelProperty(value = "公司id")
    private String companyId;

    /**
     * 公司id
     */
    @ApiModelProperty(value ="1业主提交,2业主取消,3关闭工单,4派工,5维修接单,6待支付,7已完成,8已评价,9已转单")
    private Integer repairStatus;

    /**
     * 报修时间
     */
    @ApiModelProperty(value ="报修时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date repairDate;

}
