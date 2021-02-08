package com.spring.base.vo.baseinfo.instrumentdetail;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;


/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-04-03 16:03:23
* @Desc类说明: 新增仪明细vo
*/

@ApiModel
@Data
@ToString
public class InstrumentDetailAddVo {

   //小区id
   @ApiModelProperty(value = "小区id")
   private String communityId;

   //楼栋id
   @ApiModelProperty(value = "楼栋id")
   private String buildId;

   //房屋编码
   @ApiModelProperty(value = "房屋编码")
   private String houseCode;

   //仪表id
   @ApiModelProperty(value = "仪表id")
   private String instrumentId;

   //仪表名称
   @ApiModelProperty(value = "仪表名称")
   private String instrumentName;

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

   //创建人
   @ApiModelProperty(value = "创建人")
   private String createUser;

   //修改人
   @ApiModelProperty(value = "修改人")
   private String modifyUser;

   //删除标志（0 未删除 1 已删除）
   @ApiModelProperty(value = "删除标志（0 未删除 1 已删除）")
   private Integer delFlag;

   //租户id
   @ApiModelProperty(value = "租户id")
   private String tenantId;


}
