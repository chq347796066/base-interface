package com.spring.base.entity.baseinfo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
* @date : 创建时间：2020-03-31 19:02:26
* @Desc类说明:  公司实体类
*/
@ApiModel
@Data
@ToString(callSuper = true)
@TableName("b_company")
public class CompanyEntity extends BaseEntity implements Vo<String>, Serializable {
   private static final long serialVersionUID = 1L;

   //主键
   @ApiModelProperty(value = "主键")
   private String id;

   //父节点, 第一级为0
   @ApiModelProperty(value = "父节点, 第一级为0")
   private String parent;

   //公司代码
   @ApiModelProperty(value = "公司代码")
   private String companyCode;

   //公司名称
   @ApiModelProperty(value = "公司名称")
   private String companyName;

   //公司详细层级
   @ApiModelProperty(value = "公司详细层级")
   @TableField(exist = false)
   private String companyNameList;

   //公司电话
   @ApiModelProperty(value = "公司电话")
   private String mobile;

   //公司详细地址
   @ApiModelProperty(value = "公司详细地址")
   private String address;

   //省市区地址
   @ApiModelProperty(value = "公司省市区地址")
   private String companyAddress;

   //开户行
   @ApiModelProperty(value = "开户行")
   private String openBank;

   //银行账户
   @ApiModelProperty(value = "银行账户")
   private String bankAccount;

   //法人姓名
   @ApiModelProperty(value = "法人姓名")
   private String legalName;

   //法人电话
   @ApiModelProperty(value = "法人电话")
   private String legalMobile;

   //logo
   @ApiModelProperty(value = "logo")
   private String logo;

   //营业执照
   @ApiModelProperty(value = "营业执照")
   private String businessLicense;

   //状态(1启用,2禁用)
   @ApiModelProperty(value = "状态(1启用,2禁用)")
   private Integer status;

    //图片集合
    @ApiModelProperty(value = "图片集合")
    @TableField(exist = false)
    private List<PicEntity> picEntityList;

    //文件类型
    @ApiModelProperty(value = "文件类型")
    @TableField(exist = false)
    private String excelType;

   @ApiModelProperty(value = "下级公司列表")
   @TableField(exist = false)
   private List<CompanyEntity> children;

   @ApiModelProperty(value = "下级公司列表")
   @TableField(exist = false)
   private List<String> childrenList;

   @ApiModelProperty(value = "上级公司列表")
   @TableField(exist = false)
   private CompanyEntity parentEntity;

   //租户id
//   @ApiModelProperty(value = "租户id")
//   private String tenantId;

   /**
    * saas用户试用时间
    */
   @ApiModelProperty(value = "saas用户试用时间")
   private Date tryDate;

   /**
    * saas购买的小区数
    */
   @ApiModelProperty(value = "saas购买的小区数")
   private Integer communityNum;

   /**
    * saas状态:0试用状态，1正式购买应用状态
    */
   @ApiModelProperty(value = "saas状态:0试用状态，1正式购买应用状态")
   private Integer saasStatus;

   @ApiModelProperty(value = "公司层级")
   private int companyLevel;

   @ApiModelProperty(value = "父项公司")
   @TableField(exist = false)
   private String parentName;
}
