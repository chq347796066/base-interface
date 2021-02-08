package com.spring.base.vo.saas;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 租户分页查询Vo
 *
 * @author WuJiaQuan
 */
@ApiModel
@Data
@ToString
public class TenantPageVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 租户id
     */
    @ApiModelProperty(value = "租户id")
    private String id;

    /**
     * 租户编号
     */
    @ApiModelProperty(value = "租户编号")
    private String tenantCode;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String mobile;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    private String companyName;

    /**
     * 省
     */
    @ApiModelProperty(value = "省")
    private String province;

    /**
     * 省编码
     */
    @ApiModelProperty(value = "省编码")
    private Integer provinceCode;

    /**
     * 市
     */
    @ApiModelProperty(value = "市")
    private String city;

    /**
     * 市编码
     */
    @ApiModelProperty(value = "市")
    private Integer cityCode;

    /**
     * 区
     */
    @ApiModelProperty(value = "区")
    private String area;

    /**
     * 区编码
     */
    @ApiModelProperty(value = "区编码")
    private Integer areaCode;

    /**
     * 详细地址
     */
    @ApiModelProperty(value = "详细地址")
    private String address;

    /**
     * 公司规模id关联公司规模表id
     */
    @ApiModelProperty(value = "公司规模id关联公司规模表id")
    private String scaleId;

    /**
     * 应用id
     */
    @ApiModelProperty(value = "应用id")
    private String applicationId;

    /**
     * 行业id关联行业表id
     */
    @ApiModelProperty(value = "行业id关联行业表id")
    private String industryId;

    /**
     * 营业执照号
     */
    @ApiModelProperty(value = "营业执照号")
    private String businessLicenseNum;

    /**
     * 营业执照图片
     */
    @ApiModelProperty(value = "营业执照图片")
    private String businessLicensePic;

    /**
     * 联系人
     */
    @ApiModelProperty(value = "联系人")
    private String contactPeople;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    private String contactPhone;

    /**
     * 联系邮箱
     */
    @ApiModelProperty(value = "联系邮箱")
    private String contactEmail;

    /**
     * 状态（1：待审核，2：试用，3.启用，停用）
     */
    @ApiModelProperty(value = "状态（1：待审核，2：试用，3.启用，停用）")
    private Integer tenantStatus;

    /**
     * 注册时间
     */
    @ApiModelProperty(value = "注册时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime registerTime;

    /**
     * 审核时间
     */
    @ApiModelProperty(value = "审核时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime auditTime;

    /**
     * 租户应用信息
     */
    @ApiModelProperty(value = "租户应用信息")
    private List<TenantAppVo> tenantAppVoList;
}
