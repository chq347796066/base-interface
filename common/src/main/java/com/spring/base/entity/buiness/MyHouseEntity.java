package com.spring.base.entity.buiness;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.base.vo.Vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;
import lombok.ToString;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2021-01-07 14:07:46
 * @Desc类说明: 我的房屋信息实体类
 */
@ApiModel
@Data
@ToString(callSuper = true)
@TableName("b_my_house")
public class MyHouseEntity implements Vo<String>, Serializable {
   private static final long serialVersionUID = 1L;

   // 主键
   @ApiModelProperty(value = " 主键")
   private String id;

   //流程编号
   @ApiModelProperty(value = "流程编号")
   private String processCode;

   //流程名称
   @ApiModelProperty(value = "流程名称")
   private String processName;

   //用户Id
   @ApiModelProperty(value = "用户Id")
   private String userId;

   //用户姓名
   @ApiModelProperty(value = "用户姓名")
   private String userName;

   //公司id
   @ApiModelProperty(value = "公司id")
   private String companyId;

   //公司名称
   @ApiModelProperty(value = "公司名称")
   private String companyName;

   //小区id
   @ApiModelProperty(value = "小区id")
   private String communityId;

   //小区ids
   @ApiModelProperty(value = "小区ids")
   @TableField(exist = false)
   private List<String> communityIds;

   //小区名称
   @ApiModelProperty(value = "小区名称")
   private String communityName;

   //小区地址
   @ApiModelProperty(value = "小区地址")
   private String communityAddress;

   //详细地址
   @ApiModelProperty(value = "详细地址")
   private String communityAddressDetails;

   //楼栋Id
   @ApiModelProperty(value = "楼栋Id")
   private String buildId;

   //楼栋名称
   @ApiModelProperty(value = "楼栋名称")
   private String buildName;

   //单元Id
   @ApiModelProperty(value = "单元Id")
   private String cellId;

   //单元名称
   @ApiModelProperty(value = "单元名称")
   private String cellName;

   //房产Id
   @ApiModelProperty(value = "房产Id")
   private String houseId;

   //门牌号
   @ApiModelProperty(value = "门牌号")
   private String houseNo;

   //房屋编码
   @ApiModelProperty(value = "房屋编码")
   private String houseCode;

   //校验业主姓名
   @ApiModelProperty(value = "校验业主姓名")
   private String ownerName;

   //校验业主手机号
   @ApiModelProperty(value = "校验业主手机号")
   private String ownerPhone;

   //审核状态(0 待审核 1已通过 2未通过 3撤回)
   @ApiModelProperty(value = "审核状态(0 待审核 1已通过 2未通过 3撤回)")
   private Integer auditStatus;

   //审核操作者用户ID
   @ApiModelProperty(value = "审核操作者用户ID")
   private String auditUserId;

   //审核操作者名称
   @ApiModelProperty(value = "审核操作者名称")
   private String auditUserName;

   //审核操作者时间
   @ApiModelProperty(value = "审核操作者时间")
   private Date auditDate;

   //审核通过描述
   @ApiModelProperty(value = "审核通过描述")
   private String auditAdopt;

   //审核未通过描述
   @ApiModelProperty(value = "审核未通过描述")
   private String auditNotPass;

   //身份类型(1 业主 2家庭成员 3租客)
   @ApiModelProperty(value = "身份类型(1 业主 2家属 3租客)")
   private Integer identityType;

   //认证方式(1.业主认证 2.物业认证)
   private Integer certificationWay;

   //认证类型(1 房屋认证 2公司审核)
   @ApiModelProperty(value = "认证类型(1 房屋认证 2公司审核)")
   private Integer authType;

   //联系电话
   @ApiModelProperty(value = "联系电话")
   private String mobile;

   //性别(1男,2女)
   @ApiModelProperty(value = "性别(1男,2女)")
   private Integer sex;


   //证件类型(1.身份证 2.港澳通行证)
   @ApiModelProperty(value = "证件类型(1.身份证 2.港澳通行证)")
   private Integer certificateType;

   //证件号码
   @ApiModelProperty(value = "证件号码")
   private String certificateNo;

   //申请时间
   @ApiModelProperty(value = "申请时间")
   @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
   private Date applyDate;

   //租户id
   @ApiModelProperty(value = "租户id")
   private String tenantId;

   //创建人
   @ApiModelProperty(value = "创建人")
   private String createUser;

   //创建时间
   @ApiModelProperty(value = "创建时间")
   @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
   private Date createDate;

   //修改人
   @ApiModelProperty(value = "修改人")
   private String modifyUser;

   //修改时间
   @ApiModelProperty(value = "修改时间")
   @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
   private Date modifyDate;

   //删除标志（0 未删除 1 已删除）
   @ApiModelProperty(value = "删除标志（0 未删除 1 已删除）")
   private Integer delFlag;

   private String idCard;




}
