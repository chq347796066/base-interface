package com.spring.base.entity.pay;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.spring.base.vo.Vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;

import lombok.Data;
import lombok.ToString;

/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-03-18 17:32:18
* @Desc类说明: 实体类
*/
@ApiModel
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@TableName("b_module")
public class ModuleEntity implements Vo<String>, Serializable {
   private static final long serialVersionUID = 1L;

   //主键
   @ApiModelProperty(value = "主键")
   private String id;

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

   //状态(1启用,2禁用)
   @ApiModelProperty(value = "状态(1启用,2禁用)")
   private Integer status;

   //创建人
   @ApiModelProperty(value = "创建人")
   private String createUser;

   //创建时间
   @ApiModelProperty(value = "创建时间")
   private Date createDate;

   //修改人
   @ApiModelProperty(value = "修改人")
   private String modifyUser;

   //修改时间
   @ApiModelProperty(value = "修改时间")
   private Date modifyDate;


}
