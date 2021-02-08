package com.spring.base.vo.baseinfo.housingcertification;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @className: UserHouseListVo
 * @Description: 业主房屋列表
 * @author: shangshanshan
 * @date: 2021/1/4 19:57
 * @version: v1.0
 */
@ApiModel
@Data
@ToString
public class UserHouseListVo {

    @ApiModelProperty(value = "数据id")
    private String id;

    @ApiModelProperty(value = "小区id")
    private String communityId;

    @ApiModelProperty(value = "房屋id")
    private String houseId;

    @ApiModelProperty(value = "小区地址")
    private String communityAddress;

    @ApiModelProperty(value = "小区详细地址")
    private String communityAddressDetails;

    @ApiModelProperty(value = "小区名称")
    private String communityName;

    @ApiModelProperty(value = "楼栋名称")
    private String buildName;

    @ApiModelProperty(value = "单元名称")
    private String cellName;

    @ApiModelProperty(value = "门牌号")
    private String houseNo;

    @ApiModelProperty(value = "住户类型(1.业主 2.家属 3.租客)")
    private String identityType;

    @ApiModelProperty(value = "认证状态(0 待审核 1已通过 2未通过 3撤回)")
    private String auditStatus;
}
