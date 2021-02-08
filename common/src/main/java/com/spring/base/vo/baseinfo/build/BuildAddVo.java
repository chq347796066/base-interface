package com.spring.base.vo.baseinfo.build;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;


/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-31 19:02:26
 * @date : 创建时间：2020-04-01 09:38:51
 * @Desc类说明: 新增 楼栋vo
 */
 
@ApiModel
@Data
@ToString
public class BuildAddVo {
	
	//小区id
	@ApiModelProperty(value = "小区id")
	@NotNull(message = "小区id不能为空")
	private String communityId;
	
	//楼栋名称
	@ApiModelProperty(value = "楼栋名称")
	private String buildName;
	
	//描述
	@ApiModelProperty(value = "描述")
	private String buildDesc;

	//状态(1启用,2禁用)
	@ApiModelProperty(value = "状态(1启用,2禁用)")
	private Integer status;

}
