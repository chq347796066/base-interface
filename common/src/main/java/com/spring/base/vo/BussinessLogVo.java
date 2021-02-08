package com.spring.base.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
* @author 作者：赵进华 
* @version 创建时间：2019年3月25日 上午10:17:52
* @Desc类说明:业务日志
*/
@ApiModel
public class BussinessLogVo {

	/**
	 * 类型
	 */
	@ApiModelProperty(value = "类型")
	private String type;
	
	/**
	 * 关键字
	 */
	@ApiModelProperty(value = "关键字")
	private String key;
	
	/**
	 * 日志信息
	 */
	@ApiModelProperty(value = "日志信息")
	private String logInfo;
	
	/**
	 * ip
	 */
	@ApiModelProperty(value = "ip")
	private String ip;
	
	
	/**
	 * 创建人
	 */
	@ApiModelProperty(value = "创建人")
	private String createBy;
	
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private Date createDate;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getLogInfo() {
		return logInfo;
	}

	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
	public String toString() {
		return "BussinessLogVo [type=" + type + ", key=" + key + ", logInfo=" + logInfo + ", ip=" + ip + ", createBy="
				+ createBy + ", createDate=" + createDate + "]";
	}

	
	
	
}
