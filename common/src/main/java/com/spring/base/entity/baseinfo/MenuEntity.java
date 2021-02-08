package com.spring.base.entity.baseinfo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.spring.base.entity.BaseEntity;
import com.spring.base.vo.Vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-03-18 14:56:17
* @Desc类说明: 菜单实体类
*/
@ApiModel
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@TableName("b_menu")
public class MenuEntity extends BaseEntity implements Vo<String>, Serializable {
   private static final long serialVersionUID = 1L;

   //主键
   @ApiModelProperty(value = "主键")
   private String id;

   //父项
   @ApiModelProperty(value = "父项")
   private String parent;

   //菜单Code，当为按钮时必传
   @ApiModelProperty(value = "菜单Code，当为按钮时必传")
   private String menuCode;

   //菜单名称
   @ApiModelProperty(value = "菜单名称")
   private String menuTitle;

   //当前层级
   @ApiModelProperty(value = "当前层级")
   private Integer menuLevel;

   //路由名称
   @ApiModelProperty(value = "路由名称")
   private String routeName;

   //路由
   @ApiModelProperty(value = "路由")
   private String routePath;

   //图标
   @ApiModelProperty(value = "图标")
   private String menuIcon;

   //描述
   @ApiModelProperty(value = "描述")
   private String menuDesc;

   //排序
   @ApiModelProperty(value = "排序")
   private Integer menuOrder;

   //功能按钮对应接口名
   @ApiModelProperty(value = "功能按钮对应接口名")
   private String interfaceName;

    //菜单属性
    @ApiModelProperty(value = "菜单属性")
    private String menuProperty;

    //模板路径
    @ApiModelProperty(value = "模板路径")
    private String component;

   //菜单类型，1-页面，2-按钮
   @ApiModelProperty(value = "菜单类型，1-页面，2-按钮")
   private Integer menuType;

   //状态(1启用,2禁用)
   @ApiModelProperty(value = "状态(1启用,2禁用)")
   private String status;

   //系统代码
   @ApiModelProperty(value = "系统代码(manage-管理平台,payment-收费系统,app-物业app)")
   private String systemCode;

}
