package com.spring.base.vo.meter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;


/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-10-28 14:30:45
* @Desc类说明: 新增房屋抄记录vo
*/

@ApiModel
@Data
@ToString
public class MMeterRecordStatisParam {

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
   private String cellId;

   /**
    *
    */
   @ApiModelProperty(value = "")
   private String houseCode;

   /**
    *
    */
   @ApiModelProperty(value = "")
   private String houseId;

   /**
    *
    */
   @ApiModelProperty(value = "")
   private String meterTypeId;

   /**
    * 是否抄表（0:未抄表；1:已抄表）
    */
   @ApiModelProperty(value = "是否抄表（0:未抄表；1:已抄表）")
   private Integer readCheck;

   @ApiModelProperty(value = "抄表月份（yyyy-MM）")
   private String readMonth;


}
