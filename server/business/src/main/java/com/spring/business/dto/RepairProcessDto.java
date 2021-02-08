package com.spring.business.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 熊锋
 * @date 2021-01-06 11:13
 * @Describe:
 */
@ApiModel
@Data
@ToString
public class RepairProcessDto {

    /**
     * 报修id
     */
    @ApiModelProperty(value = "报修id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long repairId;

    /**
     * 处理人
     */
    @ApiModelProperty(value = "处理人")
    private String handleUser;

    /**
     * 处理时间
     */
    @ApiModelProperty(value = "处理时间")
    @JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date handleDate;

    /**
     * 处理人电话
     */
    @ApiModelProperty(value = "处理人电话")
    private String handlePhone;

    /**
     * 处理类型(1业主提交,2业主取消,3关闭工单,4派工,5转单,6维修接单,7待支付,8已完成,9已评价)
     */
    @ApiModelProperty(value = "处理类型(1业主提交,2业主取消,3关闭工单,4派工,5转单,6维修接单,7待支付,8已完成,9已评价)")
    private Integer processType;

    /**
     * 维修人
     */
    @ApiModelProperty(value = "维修人")
    private String processUser;

    /**
     * 处理备注
     */
    @ApiModelProperty(value = "处理备注")
    private String remark;

    /**
     * 处理人岗位id
     */
    @ApiModelProperty(value = "处理人岗位id")
    private String handleJobId;

    /**
     * 处理人岗位名称
     */
    @ApiModelProperty(value = "处理人岗位名称")
    private String handleJobName;

    /**
     * 维修人电话
     */
    @ApiModelProperty(value = "维修人电话")
    private String processPhone;

    /**
     * 维修人岗位id
     */
    @ApiModelProperty(value = "维修人岗位id")
    private String processJobId;

    /**
     * 维修人岗位名称
     */
    @ApiModelProperty(value = "维修人岗位名称")
    private String processJobName;

    /**
     * 报修完成图片
     */
    @ApiModelProperty(value = "报修完成图片")
    private List<String> picPath=new ArrayList<>();

    /**
     * 报修完成费用
     */
    @ApiModelProperty(value = "报修完成费用")
    private BigDecimal cost;

}
