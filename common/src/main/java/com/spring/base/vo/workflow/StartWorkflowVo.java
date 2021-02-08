package com.spring.base.vo.workflow;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author 作者：赵进华
 * @version 创建时间：2019年6月5日 下午2:17:55
 * @Desc类说明:启动工作流vo
 */
@ApiModel
@Data
@ToString(callSuper = true)
public class StartWorkflowVo {

	@ApiModelProperty(value = "业务id")
	private String businessId;
	
	@ApiModelProperty(value = "业务类型")
	private String businessType;
	
	@ApiModelProperty(value = "用户id")
	private String userId;
	
	@ApiModelProperty(value = "项目编号")
	private String projectNo;

	@ApiModelProperty(value = "项目名称")
	private String projectName;
	
}
