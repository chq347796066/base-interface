package com.spring.gateway.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
* @author 作者：赵进华 
* @version 创建时间：2019年3月21日 上午9:36:54
* @Desc类说明:token类
*/
@Data
public class GatewayTokenVo implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * token
	 */
	private String token;

	/**
	 * userId
	 */
	private String userId;

	/**
	 * userCode
	 */
	private String userCode;

	/**
	 * userName
	 */
	private String userName;

	/**
	 * 生成token的时间
	 */
	private Date tokenDate;

}
