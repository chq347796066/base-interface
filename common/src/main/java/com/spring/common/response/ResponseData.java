/*
 *
 *      Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the pig4cloud.com developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: lengleng (wangiegie@gmail.com)
 *
 */

package com.spring.common.response;

import com.spring.common.constants.MessageCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 响应信息主体
 *
 * @param <T>
 * @author lengleng
 */
@Builder
@ToString
@Accessors(chain = true)
@ApiModel(description = "响应信息主体")
@AllArgsConstructor
public class ResponseData<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@ApiModelProperty(value = "返回标记：成功标记=0000，失败标记=1111")
	private String code = MessageCode.SUCCESS;

	@Getter
	@Setter
	@ApiModelProperty(value = "返回信息")
	private String msg = "success";


	@Getter
	@Setter
	@ApiModelProperty(value = "数据")
	private T data;

	public ResponseData() {
		super();
	}

	public ResponseData(T data) {
		super();
		this.data = data;
	}

	public ResponseData(T data, String msg) {
		super();
		this.data = data;
		this.msg = msg;
	}

	public ResponseData(String msg,String code) {
		super();
		this.msg = msg;
		this.code = code;
	}

	public ResponseData(Throwable e) {
		super();
		this.msg = e.getMessage();
		this.code = MessageCode.FAIL;
	}
}
