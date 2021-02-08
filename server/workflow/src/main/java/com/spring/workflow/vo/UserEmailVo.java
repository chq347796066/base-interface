package com.spring.workflow.vo;

import java.util.Date;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
* @author 作者：赵进华 
* @version 创建时间：2019年10月28日 下午5:31:38
* @Desc类说明:用户emailVo
*/
@ApiModel
@Data
@ToString
public class UserEmailVo {

	// 节点
	@ApiModelProperty(value = "节点")
	private String node;
	
	// 邮箱
	@ApiModelProperty(value = "邮箱")
	private String email;
}
