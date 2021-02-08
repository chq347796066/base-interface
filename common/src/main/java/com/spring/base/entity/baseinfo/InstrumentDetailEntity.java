package com.spring.base.entity.baseinfo;

import java.io.Serializable;
import java.util.Date;

import com.spring.base.entity.BaseEntity;
import com.spring.base.vo.Vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.ToString;

/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-04-03 16:03:23
* @Desc类说明: 仪明细实体类
*/
@ApiModel
@Data
@ToString(callSuper = true)
@TableName("b_instrument_detail")
public class InstrumentDetailEntity extends BaseEntity implements Vo<String>, Serializable {
   private static final long serialVersionUID = 1L;

   //主键id
   @ApiModelProperty(value = "主键id")
   private String id;

   //小区id
   @ApiModelProperty(value = "小区id")
   private String communityId;

    //小区名称
    @ApiModelProperty(value = "小区名称")
    private String communityName;

   //楼栋id
   @ApiModelProperty(value = "楼栋id")
   private String buildId;

    //楼栋名称
    @ApiModelProperty(value = "楼栋名称")
    private String buildName;

   //房屋编码
   @ApiModelProperty(value = "房屋编码")
   private String houseCode;

   //仪表id
   @ApiModelProperty(value = "仪表id")
   private String instrumentId;

   //仪表名称
   @ApiModelProperty(value = "仪表名称")
   private String instrumentName;

    //仪表类型
    @ApiModelProperty(value = "仪表类型")
    private String instrumentType;

   //抄表状态(0 已抄表 1 未抄表)
   @ApiModelProperty(value = "抄表状态(0 已抄表 1 未抄表)")
   private String readingStatus;

   //上期抄表时间
   @ApiModelProperty(value = "上期抄表时间")
   private Date lastReadingTime;

   //上期抄表读数
   @ApiModelProperty(value = "上期抄表读数")
   private Integer lastReadingNumber;

   //本期抄表时间
   @ApiModelProperty(value = "本期抄表时间")
   private Date issueReadingTime;

   //本期抄表读数
   @ApiModelProperty(value = "本期抄表读数")
   private Integer issueReadingNumber;

   //仪表倍率
   @ApiModelProperty(value = "仪表倍率")
   private Integer ratio;

   //用量
   @ApiModelProperty(value = "用量")
   private Integer dosage;


}
