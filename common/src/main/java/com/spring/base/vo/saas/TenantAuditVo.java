package com.spring.base.vo.saas;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @description:租户审核vo
 * @author: 赵进华
 * @time: 2020/7/15 14:46
 */
@ApiModel
@Data
@ToString
public class TenantAuditVo {
    /**
     * 租户id
     */
    @ApiModelProperty(value = "租户id")
    private String id;

    /**
     * 状态(2: 审核试用，3:审核拒绝)
     */
    @ApiModelProperty(value = "状态(2: 审核试用，3:审核拒绝)")
    private int status;
}
