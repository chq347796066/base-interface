package com.spring.base.vo;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 作者：赵进华
 * @version 创建时间：2019年3月21日 下午2:50:13
 * @Desc类说明:日志信息类
 */
@ApiModel
public class ExceptionLogVo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 日志主信息
	 */
	private String mainInfo;

	/**
	 * 日志详细信息
	 */
	private String logInfo;

	/**
	 * 日志时间
	 */
	private Date logDate;

	public String getMainInfo() {
		return mainInfo;
	}

	public void setMainInfo(String mainInfo) {
		this.mainInfo = mainInfo;
	}

	public String getLogInfo() {
		return logInfo;
	}

	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}

	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	@Override
	public String toString() {
		return "LogVo [mainInfo=" + mainInfo + ", logInfo=" + logInfo + ", logDate=" + logDate + "]";
	}

	
}
