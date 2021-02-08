package com.spring.base.entity.baseinfo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.spring.base.entity.BaseEntity;
import com.spring.base.vo.Vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-04-03 17:35:27
* @Desc类说明: 客户信息实体类
*/
@ApiModel
@Data
@ToString(callSuper = true)
@TableName("b_customer")
public class CustomerEntity extends BaseEntity implements Vo<String>, Serializable {
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


    //小区名称
   @ApiModelProperty(value = "小区名称")
   private String communityName;

   //客户类型 1 个人 2单位 3开发商
   @ApiModelProperty(value = "客户类型 1 个人 2单位 3开发商")
   private String customerType;

   //客户姓名
   @ApiModelProperty(value = "客户姓名")
   private String customerName;

   //性别(1男,2女)
   @ApiModelProperty(value = "性别(1男,2女)")
   private Integer sex;

   //证件类型 1 身份证
   @ApiModelProperty(value = "证件类型 1 身份证")
   private Integer certificatesType;

   //证件号码
   @ApiModelProperty(value = "证件号码")
   private String certificatesNumber;

    //证件号码
    @ApiModelProperty(value = "证件号码")
    @TableField(exist = false)
    private String certificatesNumberShow;

   //联系电话
   @ApiModelProperty(value = "联系电话")
   private String phone;

    //证件号码
    @ApiModelProperty(value = "联系电话")
    @TableField(exist = false)
    private String phoneShow;

   //籍贯
   @ApiModelProperty(value = "籍贯")
   private String nativePlace;

   //学历
   @ApiModelProperty(value = "学历")
   private String education;

   //职业
   @ApiModelProperty(value = "职业")
   private String occupation;

   //客户地址
   @ApiModelProperty(value = "客户地址")
   private String communityAddress;

    //状态"(1启用,2禁用)
    @ApiModelProperty(value = "状态(1启用,2禁用)")
    private String status;

    //登录时间
    @ApiModelProperty(value = "登录时间")
    private Date loginTime;

    //注册时间
    @ApiModelProperty(value = "注册时间")
    private String registerTime;

    //来源渠道
    @ApiModelProperty(value = "来源渠道")
    private Date sourceChannel;

    //文件类型
    @ApiModelProperty(value ="文件类型")
    @TableField(exist = false)
    private String excelType;

    //租户id
   /* @ApiModelProperty(value = "租户id")
    private String tenantId;*/

    //公司id
    @ApiModelProperty(value = "公司id")
    private String companyId;
}
