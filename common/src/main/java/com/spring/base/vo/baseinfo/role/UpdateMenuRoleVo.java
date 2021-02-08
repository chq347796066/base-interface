package com.spring.base.vo.baseinfo.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author 作者：赵进华
 * @version 创建时间：2019年5月24日 上午10:31:30
 * @Desc类说明:更新角色vo
 */
@ApiModel
@Data
@ToString
public class UpdateMenuRoleVo {

	// 角色名称
	@ApiModelProperty(value = "角色ID", required = true)
	private String roleId;

	//菜单列表
	@ApiModelProperty(value = "菜单列表")
	private String[] menuIdList;
}
