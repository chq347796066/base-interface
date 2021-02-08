package com.spring.base.entity.baseinfo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.base.entity.BaseEntity;
import com.spring.base.vo.Vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-03-18 15:37:54
* @Desc类说明: 角色实体类
*/
@ApiModel
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@TableName("b_role")
public class RoleEntity extends BaseEntity implements Vo<String>, Serializable {
   private static final long serialVersionUID = 1L;

   //主键
   @ApiModelProperty(value = "主键")
   private String id;

   //角色代码
   @ApiModelProperty(value = "角色代码")
   private String roleCode;

   //角色名称
   @ApiModelProperty(value = "角色名称")
   private String roleName;

   //角色描述
   @ApiModelProperty(value = "角色描述")
   private String roleDesc;

   //数据权限是否全选，1-是，2-否
   @ApiModelProperty(value = "数据权限是否全选，1-是，2-否")
   private Integer dataPowerAll;

   //状态(1启用,2禁用)
   @ApiModelProperty(value = "状态(1启用,2禁用)")
   private Integer status;

   //创建人
   @ApiModelProperty(value = "创建人")
   private String createUser;

   //修改时间
   @ApiModelProperty(value = "修改时间")
   @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
   private Date modifyDate;

   //修改人
   @ApiModelProperty(value = "修改人")
   private String modifyUser;

   //租户id
   @ApiModelProperty(value = "租户id")
   private String tenantId;

   /**
    * 角色类型(1系统角色,2SAAS应用)
    */
   @ApiModelProperty(value = "角色类型(1系统角色,2SAAS应用)")
   private Integer roleType;

   /**
    * 0 扩展应用,1 试用应用 , 2主应用
    */
   @ApiModelProperty(value = "0 扩展应用,1 试用应用 , 2主应用")
   private Integer appType;
}
