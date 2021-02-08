package com.spring.base.entity.baseinfo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.spring.base.entity.BaseEntity;
import com.spring.base.vo.Vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.ToString;

/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-04-03 09:35:57
* @Desc类说明: 房屋仪管理实体类
*/
@ApiModel
@Data
@ToString(callSuper = true)
@TableName("b_house_instrument")
public class HouseInstrumentEntity extends BaseEntity implements Vo<Long>, Serializable {
   private static final long serialVersionUID = 1L;

   //主键id
   @ApiModelProperty(value = "主键id")
   private Long id;

   //房屋id
   @ApiModelProperty(value = "房屋id")
   private String houseId;

    //房屋id
    @ApiModelProperty(value = "房屋编码")
    private String houseCode;

   //楼栋id
   @ApiModelProperty(value = "楼栋id")
   private String buildId;

    //楼栋名称
    @ApiModelProperty(value = "楼栋名称")
    private String buildName;

   //小区id
   @ApiModelProperty(value = "小区id")
   private String communityId;

    //小区id
    @ApiModelProperty(value = "小区名称")
    private String communityName;

   //仪表名称
   @ApiModelProperty(value = "仪表名称")
   private String instrumentName;

   //仪表id
   @ApiModelProperty(value = "仪表id")
   private String instrumentId;

    //仪表id
    @ApiModelProperty(value = "仪表类型")
    private String instrumentType;

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

}
