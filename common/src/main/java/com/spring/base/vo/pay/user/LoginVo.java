package com.spring.base.vo.pay.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
* @author 作者：赵进华 
* @version 创建时间：2019年5月21日 下午2:48:39
* @Desc类说明:登录vo
*/
@ApiModel
@Data
@ToString
public class LoginVo {
	/**
	 * 密文字符串
	 */
	@ApiModelProperty(value = "密文字符串")
	private String secretString;
}
