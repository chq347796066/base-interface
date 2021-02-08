package com.spring.base.vo.workflow;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
* @author 作者：赵进华 
* @version 创建时间：2019年6月5日 下午6:27:08
* @Desc类说明:工作流类型Vo
*/
@ApiModel
@Data
@ToString
public class WorkflowTypeVo {
	@ApiModelProperty(value = "表名")
	private String tableName;
	
	@ApiModelProperty(value = "工作流名称")
	private String workflowName;
}
