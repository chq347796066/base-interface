package com.spring.base.vo.baseinfo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;


/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-31 19:02:26
 * @Desc类说明: 更新用户信息vo
 */
@ApiModel
@Data
@ToString
public class UserAccountStatusVo {
	
	// 用户ID
	@ApiModelProperty(value = " 用户ID")
	@NotNull(message = "主键id不能为空")
	private String id;

	//启用或禁用(1启用,2禁用)
	@ApiModelProperty(value = "启用或禁用(1启用,2禁用)")
	@NotNull(message = "启用或禁用(1启用,2禁用)")
	private Integer enableStatusFlag;
	
}
