package com.spring.business.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.gs618.sprouts.beans.BeanMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 熊锋
 * @date 2021-01-05 20:39
 * @Describe:
 */
@ApiModel
@Data
@ToString
public class RepairDetailDto {

    /**
     * 报修单号
     */
    @ApiModelProperty(value = "报修单号")
    private String repairOrder;

    /**
     * 报修类型
     */
    @ApiModelProperty(value = "报修类型 ")
    private String repairType;

    /**
     * 报修单类型(1业主报修,2代客报修)
     */
    @ApiModelProperty(value = "报修单类型(1业主报修,2代客报修)")
    private Integer orderType;

    /**
     * 报修状态
     */
    @ApiModelProperty(value = "报修状态(1业主提交,2业主取消,3关闭工单,4派工,5转单,6维修接单,7待支付,8已完成,9已评价)")
    private Integer repairStatus;
    /**
     * 报修描述
     */
    @ApiModelProperty(value = "报修描述")
    private String content;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date hopeBeginTime;

    /**
     * 处理时间
     */
    @ApiModelProperty(value = "处理时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startDate;

    /**
     * 完成时间
     */
    @ApiModelProperty(value = "完成时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date finishDate;

    /**
     * 提交时间
     */
    @ApiModelProperty(value = "完成时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @BeanMapper(targets = {"createDate"})
    private Date submitDate;


    /**
     * 期望上门结束时间
     */
    @ApiModelProperty(value = "期望上门结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date hopeEndTime;

    /**
     * 报修图片
     */
    @ApiModelProperty(value = "报修图片")
    private List<String> picPath=new ArrayList<>();

    /**
     * 报修流程
     */
    @ApiModelProperty(value = "报修流程信息")
    private List<RepairProcessDto> repairProcessDtoList=new ArrayList<>();

    /**
    * 评论列表
    */
    @ApiModelProperty(value = "报修评论信息")
    private List<RepairCommentDto> repairCommentList=new ArrayList<>();
}
