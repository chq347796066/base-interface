package com.spring.base.vo.baseinfo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
* @author 作者：吕文祥
* @version 创建时间：2019年5月21日 下午2:48:39
* @Desc类说明:登录vo
*/
@ApiModel
@Data
@ToString
public class ResetPasswordVo {
	/**
	 * 用户id
	 */
	@ApiModelProperty(value = "id")
	private String id;
}
