package com.spring.base.vo.baseinfo.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;


/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-03-18 15:37:54
* @Desc类说明: 更新角色vo
*/
@ApiModel
@Data
@ToString
public class RoleUpdateVo  {

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

   //状态(1启用,2禁用)
   @ApiModelProperty(value = "状态(1启用,2禁用)")
   private Integer status;

}
