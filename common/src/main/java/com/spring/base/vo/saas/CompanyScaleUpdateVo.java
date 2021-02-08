package com.spring.base.vo.saas;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;


 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-02 15:17:12
 * @Desc类说明: 更新公司规模vo
 */
@ApiModel
@Data
@ToString
public class CompanyScaleUpdateVo  {
	
	//id
	@ApiModelProperty(value = "id")
	private String id;
	
	//公司规模
	@ApiModelProperty(value = "公司规模")
	private String scaleName;
	
}
