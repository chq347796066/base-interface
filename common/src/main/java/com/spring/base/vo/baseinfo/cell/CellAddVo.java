package com.spring.base.vo.baseinfo.cell;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;


/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-31 19:02:26
 * @Desc类说明: 新增单元信息vo
 */
 
@ApiModel
@Data
@ToString
public class CellAddVo {
	
	//小区id
	@ApiModelProperty(value = "小区id")
	@NotNull(message = "小区id不能为空")
	private String communityId;
	
	//楼栋id
	@ApiModelProperty(value = "楼栋id")
	@NotNull(message = "楼栋id不能为空")
	private String buildId;
	
	//单元名称
	@ApiModelProperty(value = "单元名称")
	private String cellName;
	
	// 描述
	@ApiModelProperty(value = " 描述")
	private String cellDesc;

	//状态(1启用,2禁用)
	@ApiModelProperty(value = "状态(1启用,2禁用)")
	private Integer status;

}
