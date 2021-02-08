package com.spring.base.entity.baseinfo;

import java.io.Serializable;
import java.util.Date;
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
* @Desc类说明: 用户信息实体类
*/
@ApiModel
@Data
@ToString(callSuper = true)
@TableName("b_user")
public class UserEntity extends BaseEntity implements Vo<String>, Serializable {
   private static final long serialVersionUID = 1L;

   // 主键
   @ApiModelProperty(value = " 主键")
   private String id;

    //租户id
//    @ApiModelProperty(value = "租户id")
//    private String tenantId;

   //公司id
   @ApiModelProperty(value = "公司id")
   private String companyId;

    //公司ids
    @ApiModelProperty(value = "公司ids")
    @TableField(exist = false)
    private List<String> companyIds;

    //小区id
   @ApiModelProperty(value = "小区id")
   private String communityId;

    //小区ids
    @ApiModelProperty(value = "小区ids")
    @TableField(exist = false)
    private List<String> communityIds;

   //用户名
   @ApiModelProperty(value = "用户Code码")
   private String userCode;

    @ApiModelProperty(value = "用户昵称")
    private String userNickname;

   //用户姓名
   @ApiModelProperty(value = "用户姓名")
   private String userName;

   //密码
   @ApiModelProperty(value = "密码")
   private String password;

   //手机
   @ApiModelProperty(value = "手机")
   private String mobile;

   //性别(1男,2女)
   @ApiModelProperty(value = "性别(1男,2女)")
   private Integer sex;

   //生日
   @ApiModelProperty(value = "生日")
   private String birthday;

   //身份证
   @ApiModelProperty(value = "身份证")
   private String idCard;

   //地址
   @ApiModelProperty(value = "地址")
   private String address;

   //籍贯
   @ApiModelProperty(value = "籍贯")
   private String nativePlace;

   //角色
   @ApiModelProperty(value = "角色")
   private String roleId;

   //登录开关(1开,2关)
   @ApiModelProperty(value = "登录开关(1开,2关)")
   private Integer loginStatus;

    //登录时间
    @ApiModelProperty(value = "登录时间")
    private Date loginTime;

   //状态(1在职,2离职)
   @ApiModelProperty(value = "状态(1在职,2离职)")
   private Integer status;

    //用户类型(1平台管理员,2物业公司管理员)
    @ApiModelProperty(value = "用户类型(1平台管理员,2物业公司管理员)")
    private String  userType;

    //用户类型(1平台管理员,2物业公司管理员)
    @ApiModelProperty(value = "住户类型（1:业主；2：亲属；3:租客；4：游客；5：未注册）")
    private String  houseHoldType;

    //平台权限(0管理平台,1物业APP,2收费系统)
    @ApiModelProperty(value = "平台权限(0管理平台,1物业APP,2收费系统)")
    private String platformAuthority;

    //渠道来源(1:android,2:ios,3:pc)
    @ApiModelProperty(value = "渠道来源(1:android,2:ios,3:pc)")
    private Integer sourceChannel;

    @ApiModelProperty(value = "用户状态(1:启用或2:禁用)")
    private Integer enableStatusFlag;

    /**
     * 是否是saas用户
     */
    @ApiModelProperty(value = "0非saas用户,1saas用户")
    private Integer isSaas;

    /**
     * 是否是租户管理员:0不是,1是租户管理员
     */
    @ApiModelProperty(value = "是否是租户管理员:0不是,1是租户管理员")
    private Integer tenantAdmin;

    //职位
    @ApiModelProperty(value = "职位")
    private String jobId;

}
