package com.spring.workflow.vo;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author 作者：赵进华
 * @version 创建时间：2019年6月5日 下午5:17:49
 * @Desc类说明:
 */
@ApiModel
@Data
@ToString(callSuper = true)
public class ActBusinessVo {
	/**
	 * id
	 */
	@ApiModelProperty(value = "id")
	private String id;

	/**
	 * userId
	 */
	@ApiModelProperty(value = "用户id")
	private String userId;

	/**
	 * submitId
	 */
	@ApiModelProperty(value = "提交人id")
	private String submitId;

	@ApiModelProperty(value = "项目编号")
	private String projectNo;

	@ApiModelProperty(value = "项目名称")
	private String projectName;

	/**
	 * 业务id
	 */
	@ApiModelProperty(value = "业务id")
	private String businessId;

	/**
	 * 业务类型
	 */
	@ApiModelProperty(value = "业务类型")
	private String businessType;

	/**
	 * 业务名称
	 */
	@ApiModelProperty(value = "业务名称")
	private String businessName;

	/**
	 * 工作流实例id
	 */
	@ApiModelProperty(value = "工作流实例id")
	private String processInstanceId;

	/**
	 * 状态
	 */
	private Integer status;

	// create_by
	private String createBy;

	// create_date
	private Date createDate;

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "ActBusinessVo [id=" + id + ", userId=" + userId + ", businessId=" + businessId + ", businessType="
				+ businessType + ", businessName=" + businessName + ", processInstanceId=" + processInstanceId
				+ ", status=" + status + ", createBy=" + createBy + ", createDate=" + createDate + "]";
	}

}
