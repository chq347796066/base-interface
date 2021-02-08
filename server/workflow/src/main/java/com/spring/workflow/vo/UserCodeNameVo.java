package com.spring.workflow.vo;



import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
* @author 作者：赵进华 
* @version 创建时间：2019年10月26日 上午10:52:51
* @Desc类说明:
*/

@ApiModel
@Data
@ToString(callSuper = true)
public class UserCodeNameVo {
	
	@ApiModelProperty(value = "审核用户id")
	private String userId;

	@ApiModelProperty(value = "审核用户名称")
	private String userName;
	
	@ApiModelProperty(value = "邮件")
	private String email;

}
