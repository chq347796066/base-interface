package com.spring.common.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
* @author 作者：赵进华 
* @version 创建时间：2019年3月21日 上午9:36:54
* @Desc类说明:请求响应类
*/
@ApiModel
@Data
@ToString(callSuper = true)
public class ApiResponseResult {

	// 消息
	@ApiModelProperty(value = "消息")
	private String msg = "";

	// 数据
	@ApiModelProperty(value = "数据")
	private Object data="";

	// 代码
	@ApiModelProperty(value = "代码")
	private String code;

	public ApiResponseResult() {
		super();
	}
	
	public ApiResponseResult(String code, String msg) {
		super();
		this.msg = msg;
		this.code = code;
	}
	
	public ApiResponseResult(String msg, Object data, String code) {
		super();
		this.msg = msg;
		this.data = data;
		this.code = code;
	}
	
}
