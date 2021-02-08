package com.spring.base.vo.saas;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;


/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-07-01 17:10:11
* @Desc类说明: 发票列表查询vo
*/

@ApiModel
@Data
@ToString
public class InvoiceResponseVo {

   /**
    * 发票id
    */
   @ApiModelProperty(value = "发票id")
   private String id;

   /**
    * 发票抬头id
    */
   @ApiModelProperty(value = "发票抬头id")
   private String invoiceHeadId;

   /**
    * 金额
    */
   @ApiModelProperty(value = "金额")
   private BigDecimal invoiceAmount;

   /**
    * 状态（1 待审核 2 开票失败 3 已开票
    */
   @ApiModelProperty(value = "状态（1 待审核 2 开票失败 3 已开票")
   private Integer invoiceStatus;

   /**
    * 发票号码
    */
   @ApiModelProperty(value = "发票号码")
   private String invoiceNum;
   /**
    * 开票申请时间
    */
   @ApiModelProperty(value = "开票申请时间")
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
   private Date applyTime;

   /**
    * 开票描述
    */
   @ApiModelProperty(value = "开票描述")
   private String description;

   /**
    * 公司（发票抬头）
    */
   @ApiModelProperty(value = "公司（发票抬头）")
   private String companyName;

}
