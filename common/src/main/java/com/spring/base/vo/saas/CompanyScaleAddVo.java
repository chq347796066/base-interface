package com.spring.base.vo.saas;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;


/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-02 15:17:12
 * @Desc类说明: 新增公司规模vo
 */
 
@ApiModel
@Data
@ToString
public class CompanyScaleAddVo {
	
	//公司规模
	@ApiModelProperty(value = "公司规模")
	@NotNull(message = "公司规模不能为空")
	private String scaleName;
	
	
}
