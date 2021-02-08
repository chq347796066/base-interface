package com.spring.base.entity.baseinfo;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.spring.base.entity.BaseEntity;
import com.spring.base.vo.Vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.ToString;

/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-03-31 19:02:26
* @Desc类说明: 房产信息实体类
*/
@ApiModel
@Data
@ToString(callSuper = true)
@TableName("b_house")
public class HouseEntity extends BaseEntity implements Vo<String>, Serializable {
   private static final long serialVersionUID = 1L;

   //
   @ApiModelProperty(value = "")
   private String id;

   //小区id
   @ApiModelProperty(value = "小区id")
   private String communityId;

   //小区ids
   @ApiModelProperty(value = "小区ids")
   @TableField(exist = false)
   private List<String> communityIds;

    //小区名称
   @ApiModelProperty(value = "小区名称")
   @TableField(exist = false)
   private String communityName;

   //客户id
   @ApiModelProperty(value = "客户id")
   private String customerId;

   //客户名称
   @ApiModelProperty(value = "客户名称")
   @TableField(exist = false)
   private String customerName;

   //楼栋ID
   @ApiModelProperty(value = "楼栋ID")
   private String buildId;

   //楼栋名称
   @ApiModelProperty(value = "楼栋名称")
   @TableField(exist = false)
   private String buildName;

   // 单元id
   @ApiModelProperty(value = " 单元id")
   private String cellId;

    // 单元名称
    @ApiModelProperty(value = " 单元名称")
    @TableField(exist = false)
    private String cellName;

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
   private Integer houseType;

   //房间状态
   @ApiModelProperty(value = "房间状态")
   private Integer houseStatus;

   //建筑面积
   @ApiModelProperty(value = "建筑面积")
   private String architectureArea;


    //使用面积
    @ApiModelProperty(value = "使用面积")
    private String useArea;

    //住户姓名
    @ApiModelProperty(value = "业主姓名")
    private String ownerName;

    //住户电话
    @ApiModelProperty(value = "业主电话")
    private String ownerMobile;


    //住户证件
    @ApiModelProperty(value = "业主证件")
    private String ownerCard;

   //状态(1启用,2禁用)
   @ApiModelProperty(value = "状态(1启用,2禁用)")
   private Integer status;

    //文件类型
    @ApiModelProperty(value ="文件类型")
    @TableField(exist = false)
    private String excelType;

    //租户id
//    @ApiModelProperty(value = "租户id")
//    private String tenantId;

    //公司id
    @ApiModelProperty(value = "公司id")
    private String companyId;

    @TableField(exist = false)
    private String userId;

    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private String companyName;

    //小区地址
    @TableField(exist = false)
    private String communityAddress;

    @TableField(exist = false)
    private String communityAddressDetails;

    @TableField(exist = false)
    private Integer sex;

    @TableField(exist = false)
    private Integer certificateType;

    @TableField(exist = false)
    private String certificateNo;

}
