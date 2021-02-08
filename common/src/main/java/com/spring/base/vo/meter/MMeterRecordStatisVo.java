package com.spring.base.vo.meter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;


/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-10-28 14:30:45
* @Desc类说明: 新增房屋抄记录vo
*/

@ApiModel
@Data
@ToString
public class MMeterRecordStatisVo {

    /**
     *
     */
    @ApiModelProperty(value = "")
    private String communityName;

   /**
    *
    */
   @ApiModelProperty(value = "")
   private String communityId;

   /**
    *
    */
   @ApiModelProperty(value = "")
   private String buildId;

   /**
    *
    */
   @ApiModelProperty(value = "")
   private String buildName;

   /**
    *
    */
   @ApiModelProperty(value = "仪表总户数")
   private String houseNum;

   /**
    *
    */
   @ApiModelProperty(value = "")
   private String meterNum;

   /**
    *
    */
   @ApiModelProperty(value = "已抄表")
   private String readNum;

   /**
    *
    */
   @ApiModelProperty(value = "未抄表")
   private String notReadNum;



}
