package com.spring.base.vo.saas;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;


/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-07-01 17:10:11
* @Desc类说明: 发票列表查询vo
*/

@ApiModel
@Data
@ToString
public class InvoiceQueryVo {

   /**
    * 发票号码
    */
   @ApiModelProperty(value = "发票号码")
   private String invoiceNum;

   /**
    * 开票状态
    */
   @ApiModelProperty(value = "开票状态 1 待审核 2 开票失败 3 已开票")
   private Integer invoiceStatus;

   /**
    * 发票申请开始时间
    */
   @ApiModelProperty(value = "发票申请开始时间")
   private String startTime;

   /**
    * 发票申请结束时间
    */
   @ApiModelProperty(value = "发票申请结束时间")
   private String endTime;

   /**
    * 用户手机号码
    */
   @ApiModelProperty(value = "用户手机号码")
   @NotNull(message = "手机号码不能未空")
   private String mobile;

}
