package com.spring.base.vo.saas;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;


/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-07-01 17:10:11
* @Desc类说明: 新增租户信息vo
*/

@ApiModel
@Data
@ToString
public class TenantResponseVo {

   //公司名称
   @ApiModelProperty(value = "公司名称")
   private String companyName;

   //省
   @ApiModelProperty(value = "省")
   private String province;

   //省
   @ApiModelProperty(value = "省编码")
   private String provinceCode;

   //市
   @ApiModelProperty(value = "市")
   private String city;

   //市
   @ApiModelProperty(value = "市编码")
   private String cityCode;

   //区
   @ApiModelProperty(value = "区")
   private String area;

   //区
   @ApiModelProperty(value = "区编码")
   private String areaCode;

   //详细地址
   @ApiModelProperty(value = "详细地址")
   private String address;

   //公司规模
   @ApiModelProperty(value = "公司规模")
   private String scaleName;

   //行业名称
   @ApiModelProperty(value = "行业名称")
   private String industryName;

   //营业执照号
   @ApiModelProperty(value = "营业执照号")
   private String businessLicenseNum;

   //营业执照图片
   @ApiModelProperty(value = "营业执照图片")
   private String businessLicensePic;

   //联系人
   @ApiModelProperty(value = "联系人")
   private String contactPeople;

   //联系电话
   @ApiModelProperty(value = "联系电话")
   private String contactPhone;

   //联系邮箱
   @ApiModelProperty(value = "联系邮箱")
   private String contactEmail;

   //状态（1：待审核，2：试用，3.启用，停用）
   @ApiModelProperty(value = "状态（1：待审核，2：试用，3.启用，停用）")
   private Integer tenantStatus;

}
