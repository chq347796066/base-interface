package com.spring.base.vo.workflow;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
* @author 作者：赵进华 
* @version 创建时间：2019年6月10日 下午4:06:24
* @Desc类说明:审批信息vo
*/
@ApiModel
@Data
@ToString
public class ApproveInfoVo {
	/**
	 * id	
	 */
	@ApiModelProperty(value = "id")
	private String id;

	/**
	 * 业务id	
	 */
	@ApiModelProperty(value = "业务id")
	private String businessId;
	
	
	/**
	 * 流程实例id
	 */
	@ApiModelProperty(value = "流程实例id")
	private String processInstanceId;
	
	/**
	 * 审批节点名称
	 */
	@ApiModelProperty(value = "审批节点名称")
	private String processName;
	
	/**
	 * 任务id
	 */
	@ApiModelProperty(value = "任务id")
	private String taskId;
	
	/**
	 * 操作
	 */
	@ApiModelProperty(value = "操作:审核(pass),驳回(reject)")
	private String operation;
	
	/**
	 * 操作人
	 */
	@ApiModelProperty(value = "操作人")
	private String operationMan;
	
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;
	
	@ApiModelProperty(value = "状态")
	private Integer status;
	
	@ApiModelProperty(value = "创建人")
	private String createBy;
	
	@ApiModelProperty(value = "创建时间")
	private Date createDate;

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getOperationMan() {
		return operationMan;
	}

	public void setOperationMan(String operationMan) {
		this.operationMan = operationMan;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	@Override
	public String toString() {
		return "ApproveInfoVo [id=" + id + ", businessId=" + businessId + ", processInstanceId=" + processInstanceId
				+ ", processName=" + processName + ", taskId=" + taskId + ", operation=" + operation + ", operationMan="
				+ operationMan + ", remark=" + remark + ", status=" + status + ", createBy=" + createBy
				+ ", createDate=" + createDate + "]";
	}


	

	
	
}
