package com.spring.base.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
* @author 作者：赵进华 
* @version 创建时间：2019年3月22日 上午11:15:36
* @Desc类说明:异常日志查询Vo
*/
@ApiModel
public class ExceptionLogSearchVo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 起始页
	 */
	@ApiModelProperty(value = "起始页")
	private Integer pageNum;
	
	/**
	 * 每页记录数
	 */
	@ApiModelProperty(value = "每页记录数")
	private Integer pageSize;
	

	/**
	 * 起始时间
	 */
	@ApiModelProperty(value = "起始时间")
	private String beginDate;
	
	/**
	 * 结束时间
	 */
	@ApiModelProperty(value = "结束时间")
	private String endDate;

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "ExceptionLogSearchVo [pageNum=" + pageNum + ", pageSize=" + pageSize + ", beginDate=" + beginDate
				+ ", endDate=" + endDate + "]";
	}

	
	
	
}
