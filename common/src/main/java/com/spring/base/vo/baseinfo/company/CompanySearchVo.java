package com.spring.base.vo.baseinfo.company;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @description:公司查询vo
 * @author: 赵进华
 * @time: 2020/6/12 15:43
 */
@ApiModel
@Data
@ToString
public class CompanySearchVo {

    @ApiModelProperty(value = "租户id")
    private String tenantId;

    @ApiModelProperty(value = "公司id")
    private String companyId;

    @ApiModelProperty(value = "小区id")
    private String communityId;
}
