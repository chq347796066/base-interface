package com.spring.workflow.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
* @author 作者：赵进华 
* @version 创建时间：2019年6月5日 下午6:27:08
* @Desc类说明:工作流信息Vo
*/
@ApiModel
@Data
@ToString
public class WorkflowInfoVo {
	@ApiModelProperty(value = "业务id")
	private String businessId;
	
	@ApiModelProperty(value = "工作流实例id")
	private String processInstanceId;
	
	@ApiModelProperty(value = "任务id")
	private String taskId;
	
	@ApiModelProperty(value = "工作流名称")
	private String workflowName;
	
	@ApiModelProperty(value = "审核节点名称")
	private String auditName;
	
	@ApiModelProperty(value = "用户")
	private String userId;
	
	@ApiModelProperty(value = "创建人")
	private String createBy;
	
	@ApiModelProperty(value = "创建时间")
	private Date createDate;
}
