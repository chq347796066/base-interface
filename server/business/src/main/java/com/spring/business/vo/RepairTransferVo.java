package com.spring.business.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;


/**
 * @description:转单VO
 * @author: 赵进华
 * @time: 2021/1/6 16:24
 */
@ApiModel
@Data
@ToString
public class RepairTransferVo {

    /**
     * 报修id
     */
    @ApiModelProperty(value = "报修id")
    private Long repairId;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private String userId;

    /**
     * 用户姓名
     */
    @ApiModelProperty(value = "用户姓名")
    private String userName;

    /**
     * 用户电话
     */
    @ApiModelProperty(value = "用户电话")
    private String phone;

    /**
     * 维修人
     */
    @ApiModelProperty(value = "维修人")
    @NotBlank(message = "维修人不能为空")
    private String processUser;

    /**
     * 处理人电话
     */
    @ApiModelProperty(value = "处理人电话")
    private String handlePhone;

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

}
