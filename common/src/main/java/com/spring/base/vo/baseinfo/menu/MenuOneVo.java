package com.spring.base.vo.baseinfo.menu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 作者：赵进华
 * @version 创建时间：2019年6月25日 下午6:20:01
 * @Desc类说明:第一级菜单vo
 */
@ApiModel
@Data
@ToString
public class MenuOneVo {
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

	//系统代码
	@ApiModelProperty(value = "系统代码(manage-管理平台,payment-收费系统,app-物业app)")
	private String systemCode;
	
	// 菜单
	@ApiModelProperty(value = "菜单")
	private List<MenuTwoVo> children = new ArrayList<>();

	@ApiModelProperty(value = "用户拥有权限按钮列表")
	private List<String> buttonList = new ArrayList<>();
}
