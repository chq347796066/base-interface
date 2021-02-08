package com.spring.base.vo.baseinfo.housingcertification;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@ApiModel
@Data
@ToString
public class AuditHouseVo {

    @ApiModelProperty(value = "审核操作者用户ID")
    private String auditUserId;

    @ApiModelProperty(value = "发起年月")
    private Date applyDate;

    @ApiModelProperty(value = "审核类型")
    private String identityType;

    @ApiModelProperty(value = "审核状态")
    private String auditStatus;



}
