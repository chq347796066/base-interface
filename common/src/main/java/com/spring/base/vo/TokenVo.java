package com.spring.base.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


/**
* @author 作者：赵进华 
* @version 创建时间：2019年3月21日 上午9:36:54
* @Desc类说明:token类
*/
@ApiModel
@Data
@ToString(callSuper = true)
public class TokenVo implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * token
	 */
	@ApiModelProperty(value="token")
	private String token;

	/**
	 * userId
	 */
	@ApiModelProperty(value="userId")
	private String userId;

	/**
	 * userId
	 */
	@ApiModelProperty(value="userCode")
	private String userCode;

	/**
	 * userName
	 */
	@ApiModelProperty(value="userName")
	private String userName;

	
	/**
	 * 生成token的时间
	 */
	private Date tokenDate;

	/**
	 * 职位名称
	 */
	private String JobName;
}
