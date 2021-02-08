package com.spring.base.vo.pay.module;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import lombok.Data;
import lombok.ToString;


/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-03-18 17:32:18
* @Desc类说明: 新增vo
*/

@ApiModel
@Data
@ToString
public class ModuleAddVo {

   //父ID
   @ApiModelProperty(value = "父ID")
   private String parentId;

   // 组件名称
   @ApiModelProperty(value = " 组件名称")
   private String moduleName;

   //层级
   @ApiModelProperty(value = "层级")
   private Integer moduleLevel;

   //说明文件
   @ApiModelProperty(value = "说明文件")
   private String moduleFile;

   //描述
   @ApiModelProperty(value = "描述")
   private String moduleDesc;

   //排序
   @ApiModelProperty(value = "排序")
   private Integer moduleOrder;

}
