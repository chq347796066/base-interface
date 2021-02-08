package com.spring.base.vo.pay.disttransjournals;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;


/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-04-23 09:49:14
* @Desc类说明: 更新优惠流水记录vo
*/
@ApiModel
@Data
@ToString
public class DisttransJournalsUpdateVo  {

   //优惠流水号
   @ApiModelProperty(value = "优惠流水号")
   private String id;

   //业务流水号
   @ApiModelProperty(value = "业务流水号")
   private String businessId;

   //优惠金额
   @ApiModelProperty(value = "优惠金额")
   private String distAmount;

   //01－折扣 02－电子卡券 03－金额 04－纸制卡券05－免费 06- 免零头
   @ApiModelProperty(value = "01－折扣 02－电子卡券 03－金额 04－纸制卡券05－免费 06- 免零头")
   private String distType;

   //优惠编号
   @ApiModelProperty(value = "优惠编号")
   private String distCode;

   //01－期限 02－金额 03－业主编号 04－用户等级
   @ApiModelProperty(value = "01－期限 02－金额 03－业主编号 04－用户等级")
   private String distBenchmarkType;

   //此字段不为空时，只有满足此条件时，优惠才生效
   @ApiModelProperty(value = "此字段不为空时，只有满足此条件时，优惠才生效")
   private String distBenchmark;

   //交易流水号
   @ApiModelProperty(value = "交易流水号")
   private String transId;

   //优惠提供机构号
   @ApiModelProperty(value = "优惠提供机构号")
   private String distProvideCid;

   //优惠提供机构名
   @ApiModelProperty(value = "优惠提供机构名")
   private String distProvideCname;

   //01平台 02物业公司 03开发商
   @ApiModelProperty(value = "01平台 02物业公司 03开发商")
   private String orgType;

   //最后更新时间
   @ApiModelProperty(value = "最后更新时间")
   private String updateTime;

   //折扣
   @ApiModelProperty(value = "折扣")
   private String discount;

   //优惠费项编号
   @ApiModelProperty(value = "优惠费项编号")
   private String preChargeNo;

   //
   @ApiModelProperty(value = "")
   private Integer observation;

}
