package com.spring.base.vo.baseinfo.houseinstrument;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;


/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-04-03 09:35:57
* @Desc类说明: 更新房屋仪管理vo
*/
@ApiModel
@Data
@ToString
public class HouseInstrumentUpdateVo {

   //主键id
   @ApiModelProperty(value = "主键id")
   private Long id;

   //房屋id
   @ApiModelProperty(value = "房屋id")
   private String houseId;

   //楼栋id
   @ApiModelProperty(value = "楼栋id")
   private String buildId;

   //小区id
   @ApiModelProperty(value = "小区id")
   private String communityId;

   //仪表名称
   @ApiModelProperty(value = "仪表名称")
   private String instrumentName;

   //仪表id
   @ApiModelProperty(value = "仪表id")
   private String instrumentId;

   //仪表倍率
   @ApiModelProperty(value = "仪表倍率")
   private Integer ratio;

   //最大读数
   @ApiModelProperty(value = "最大读数")
   private BigDecimal maxReading;

   //最小读数
   @ApiModelProperty(value = "最小读数")
   private BigDecimal minReading;

   //初始日期
   @ApiModelProperty(value = "初始日期")
   private String initialDate;

   //删除标志（0 未删除 1 已删除）
   @ApiModelProperty(value = "删除标志（0 未删除 1 已删除）")
   private Integer delFlag;

   //租户id
   @ApiModelProperty(value = "租户id")
   private String tenantId;

}
