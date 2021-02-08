package com.spring.base.vo.baseinfo.company;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @description:新增saas租户vo
 * @author: 赵进华
 * @time: 2020/7/3 18:32
 */
@ApiModel
@Data
@ToString
public class AddSaasVo {

    @ApiModelProperty(value = "id")
    private String id;

    //公司代码
    @ApiModelProperty(value = "公司代码")
    private String companyCode;

    //公司名称
    @ApiModelProperty(value = "公司名称")
    @NotNull(message = "公司名称不能为空")
    private String companyName;

    //公司电话
    @ApiModelProperty(value = "公司电话")
    @NotNull(message = "公司电话不能为空")
    private String mobile;

    //公司地址
    @ApiModelProperty(value = "公司地址")
    @NotNull(message = "公司地址不能为空")
    private String address;

    //联系人
    @ApiModelProperty(value = "联系人")
    private String contactPeople;

}
