package com.spring.base.vo.saas;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import lombok.Data;
import lombok.ToString;

 
 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-02 15:17:11
 * @Desc类说明: 新增行业vo
 */
 
@ApiModel
@Data
@ToString
public class IndustryAddVo {
	
	//行业名称
	@ApiModelProperty(value = "行业名称")
	private String industryName;
	
	
}
