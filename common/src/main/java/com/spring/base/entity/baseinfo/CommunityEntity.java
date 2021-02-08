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
* @Desc类说明: 小区信息实体类
*/
@ApiModel
@Data
@ToString(callSuper = true)
@TableName("b_community")
public class CommunityEntity extends BaseEntity implements Vo<String>, Serializable {
   private static final long serialVersionUID = 1L;

   // 主键
   @ApiModelProperty(value = " 主键")
   private String id;

   //小区id
   @ApiModelProperty(value = "小区id")
   private String communityId;

   //小区ids
   @ApiModelProperty(value = "小区ids")
   @TableField(exist = false)
   private List<String> communityIds;

   //租户id
//   @ApiModelProperty(value = "租户id")
//   private String tenantId;

   //公司名称
   @ApiModelProperty(value = "租户名称")
   @TableField(exist = false)
   private String tenantName;

   //公司id
   @ApiModelProperty(value = "公司id")
   private String companyId;

    //公司名称
    @ApiModelProperty(value = "公司名称")
    @TableField(exist = false)
   private String companyName;

   //公司名称
   @ApiModelProperty(value = "公司详细层级")
   @TableField(exist = false)
   private String companyNameList;

   //小区编号
   @ApiModelProperty(value = "小区编号")
   private String communityCode;

   //小区名称
   @ApiModelProperty(value = "小区名称")
   private String communityName;

   //小区地址
   @ApiModelProperty(value = "小区地址")
   private String communityAddress;

   @ApiModelProperty(value = "小区详细地址")
   private String communityAddressDetails;

   //省
   @ApiModelProperty(value = "省")
   private String province;

   //市
   @ApiModelProperty(value = "市")
   private String city;

   //区
   @ApiModelProperty(value = "区")
   private String region;


   //小区电话
    @ApiModelProperty(value = "小区电话")
    private String communityMobile;

   //占地面积
   @ApiModelProperty(value = "占地面积")
   private Double floorArea;

   //建筑面积
   @ApiModelProperty(value = "建筑面积")
   private Double architectureArea;

   //总户数
   @ApiModelProperty(value = "总户数")
   private Integer allHouseholds;

   //容积率
   @ApiModelProperty(value = "容积率")
   private Float plotRatio;

   //状态(1启用,2禁用)
   @ApiModelProperty(value = "状态(1启用,2禁用)")
   private Integer status;

   //文件类型
   @ApiModelProperty(value ="文件类型")
   @TableField(exist = false)
   private String excelType;

   //租户公司数组
   @ApiModelProperty(value = "租户公司数组")
   @TableField(exist = false)
   private String[] tenantCompanyArray;

}
