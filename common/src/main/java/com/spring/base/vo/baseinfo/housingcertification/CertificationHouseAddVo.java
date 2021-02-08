package com.spring.base.vo.baseinfo.housingcertification;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @className: CertificationHouseAddVo
 * @Description: 新增业主房屋认证
 * @author: shangshanshan
 * @date: 2021/1/6 10:03
 * @version: v1.0
 */
@ApiModel
@Data
@ToString
public class CertificationHouseAddVo {

    @ApiModelProperty(value = "数据id(新增是不传,修改时传入)")
    private String id;

    @ApiModelProperty(value = "流程编号")
    @JsonIgnore
    private String processCode;

    @ApiModelProperty(value = "流程名称")
    @JsonIgnore
    private String processName;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "小区id")
    private String communityId;

    @ApiModelProperty(value = "小区地址")
    private String communityAddress;

    @ApiModelProperty(value = "小区详细地址")
    private String communityAddressDetails;

    @ApiModelProperty(value = "小区名称")
    private String communityName;

    @ApiModelProperty(value = "楼栋Id")
    private String buildId;

    @ApiModelProperty(value = "楼栋名称")
    private String buildName;

    @ApiModelProperty(value = "单元Id元")
    private String cellId;

    @ApiModelProperty(value = "单元名称")
    private String cellName;

    @ApiModelProperty(value = "房屋id")
    private String houseId;

    @ApiModelProperty(value = "门牌号")
    private String houseNo;

    @ApiModelProperty(value = "房屋编码")
    private String houseCode;

    @ApiModelProperty(value = "审核人id")
    private String auditUserId;

    @ApiModelProperty(value = "审核人名称")
    private String auditUserName;

    @ApiModelProperty(value = "身份类型(1 业主 2家庭成员 3租客)")
    private String identityType;

    @ApiModelProperty(value = "身认证方式(1.业主认证 2.物业认证)", hidden = true)
    @JsonIgnore
    private String certificationWay;

    @ApiModelProperty(value = "证件号码")
    private String certificateNo;

    @ApiModelProperty(value = "身份証")
    private String idCard;

    @ApiModelProperty(value = "证件类型(1.身份证 2.港澳通行证)")
    private String certificateType;

    @ApiModelProperty(value = "手机号码")
    private String mobile;
}
