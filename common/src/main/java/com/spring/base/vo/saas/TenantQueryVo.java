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
public class TenantQueryVo {

   //租户编号
   @ApiModelProperty(value = "租户编号")
   private String tenantCode;

   //公司名称
   @ApiModelProperty(value = "租户名称")
   private String companyName;

   //联系人
   @ApiModelProperty(value = "联系人")
   private String contactPeople;

   //联系电话
   @ApiModelProperty(value = "联系电话")
   private String contactPhone;

   //状态（1：待审核，2：试用，3.启用，停用）
   @ApiModelProperty(value = "状态（1：待审核，2：试用，3.启用，停用）")
   private Integer tenantStatus;

   //注册时间
   @ApiModelProperty(value = "注册开始时间")
   private String startTime;

   //注册时间
   @ApiModelProperty(value = "注册结束时间")
   private String endTime;

}
