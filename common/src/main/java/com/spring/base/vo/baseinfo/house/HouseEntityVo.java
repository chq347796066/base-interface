package com.spring.base.vo.baseinfo.house;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-03-31 19:02:26
* @Desc类说明: 房产信息实体类
*/
@ApiModel
@Data
@ToString
public class HouseEntityVo {
   private static final long serialVersionUID = 1L;

   //
   @ApiModelProperty(value = "主键ID")
   private String id;

   //小区id
   @ApiModelProperty(value = "小区id")
   private String communityId;

   //客户id
   @ApiModelProperty(value = "客户id")
   private String customerId;

   //楼栋id
   @ApiModelProperty(value = "楼栋id")
   private String buildId;

   // 单元id
   @ApiModelProperty(value = " 单元id")
   private String cellId;

   //楼层
   @ApiModelProperty(value = "楼层")
   private String floor;

   //门牌号
   @ApiModelProperty(value = "门牌号")
   private String houseNo;

   //房屋编码
   @ApiModelProperty(value = "房屋编码")
   private String houseCode;

   //房间类型
   @ApiModelProperty(value = "房间类型")
   private String houseType;

   //房间状态
   @ApiModelProperty(value = "房间状态")
   private Integer houseStatus;

   //建筑面积
   @ApiModelProperty(value = "建筑面积")
   private Double architectureArea;

   //业主姓名
   @ApiModelProperty(value = "业主姓名")
   private String ownerName;

   //业主电话
   @ApiModelProperty(value = "业主电话")
   private String ownerMobile;

   //业主证件
   @ApiModelProperty(value = "业主证件")
   private String ownerCard;

   //状态(1启用,2禁用)
   @ApiModelProperty(value = "状态(1启用,2禁用)")
   private Integer status;

   @ApiModelProperty(value = "租户ID")
   private String tenantId;

   @ApiModelProperty(value = "小区名称")
   private String communityName;

   @ApiModelProperty(value = "所属楼栋")
   private String buildName;

   @ApiModelProperty(value = "单元")
   private String cellName;



}
