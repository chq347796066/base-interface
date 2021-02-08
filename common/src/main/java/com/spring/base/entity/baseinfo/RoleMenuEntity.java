package com.spring.base.entity.baseinfo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.spring.base.entity.BaseEntity;
import com.spring.base.vo.Vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-03-30 17:05:44
* @Desc类说明: 角色和菜单中间实体类
*/
@ApiModel
@Data
@ToString(callSuper = true)
@TableName("b_role_menu")
public class RoleMenuEntity extends BaseEntity implements Vo<String>, Serializable {
   private static final long serialVersionUID = 1L;

   //主键ID
   @ApiModelProperty(value = "主键ID")
   private String id;

   //菜单ID
   @ApiModelProperty(value = "菜单ID")
   private String menuId;

   //菜单父ID
   @ApiModelProperty(value = "菜单父ID")
   private String menuParentId;

   //角色ID
   @ApiModelProperty(value = "角色ID")
   private String roleId;


   //系统代码
   @ApiModelProperty(value = "系统代码(manage-管理平台,payment-收费系统,app-物业app)")
   private String systemCode;


   //状态
   @ApiModelProperty(value = "状态")
   private Integer status;

}
