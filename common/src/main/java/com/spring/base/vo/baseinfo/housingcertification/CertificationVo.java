package com.spring.base.vo.baseinfo.housingcertification;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@ApiModel
@Data
@ToString
public class CertificationVo {

    @ApiModelProperty(value = "数据id")
    private String id;

    //审核状态(0 待审核 1已通过 2未通过 3撤回)
    @ApiModelProperty(value = "审核状态(0 待审核 1已通过 2未通过 3撤回)")
    private Integer auditStatus;

    @ApiModelProperty(value = "拒绝原因")
    private String auditNotPass;


}
