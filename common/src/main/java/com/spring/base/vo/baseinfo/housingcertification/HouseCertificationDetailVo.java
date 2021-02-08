package com.spring.base.vo.baseinfo.housingcertification;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @className: HouseCertificationDetailVo
 * @Description: 房屋认证详情
 * @author: shangshanshan
 * @date: 2021/1/5 15:01
 * @version: v1.0
 */
@ApiModel
@Data
@ToString
public class HouseCertificationDetailVo {

    @ApiModelProperty(value = "数据id")
    private String id;

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
    private String residentsType;

    @ApiModelProperty(value = "认证方式(1.业主认证 2.物业认证)")
    private String certificationWay;

    @ApiModelProperty(value = "证件号码")
    private String certificateNo;

    @ApiModelProperty(value = "证件类型(1.身份证 2.港澳通行证)")
    private String certificateType;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "驳回原因")
    private String rejectedReason;

    @ApiModelProperty(value = "审核状态")
    private String auditStatus;

}
