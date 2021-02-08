package com.spring.base.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author 作者：赵进华
 * @version 创建时间：2019年5月21日 下午3:00:52
 * @Desc类说明:登录vo
 */
@ApiModel
public class AppVo implements Serializable {

	private static final long serialVersionUID = 1L;

	// 应用类型(1:web,2:app)
	@ApiModelProperty(value = "应用类型(1:web,2:物业app,3:业主app)")
	private String appType;

	// 应用id
	@ApiModelProperty(value = "应用id")
	private String appId;

	// 应用密码
	@ApiModelProperty(value = "应用密码")
	private String appPassword;
	
	// 验证码
	@ApiModelProperty(value = "验证码")
	private String verifyCode;

	// 时间戳
	@ApiModelProperty(value = "时间戳")
	private Long timeStamp;

	// 来源渠道
	@ApiModelProperty(value = "来源渠道")
	private String sourceChannel;

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppPassword() {
		return appPassword;
	}

	public void setAppPassword(String appPassword) {
		this.appPassword = appPassword;
	}

	public Long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public String getSourceChannel() {
		return sourceChannel;
	}

	public void setSourceChannel(String sourceChannel) {
		this.sourceChannel = sourceChannel;
	}

	@Override
	public String toString() {
		return "appVo [appType=" + appType + ", appId=" + appId + ", appPassword=" + appPassword + ", verifyCode="
				+ verifyCode + ", timeStamp=" + timeStamp + "]";
	}


	
	
}
