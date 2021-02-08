package com.spring.base.vo.baseinfo.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import lombok.Data;
import lombok.ToString;


/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-03-18 15:37:54
* @Desc类说明: 新增角色vo
*/

@ApiModel
@Data
@ToString
public class RoleAddVo {

   //角色代码
   @ApiModelProperty(value = "角色代码")
   private String roleCode;

   //角色名称
   @ApiModelProperty(value = "角色名称")
   private String roleName;

   //角色描述
   @ApiModelProperty(value = "角色描述")
   private String roleDesc;


}
