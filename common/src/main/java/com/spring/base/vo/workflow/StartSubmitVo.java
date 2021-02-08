package com.spring.base.vo.workflow;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
* @author 作者：赵进华 
* @version 创建时间：2019年6月5日 下午3:38:26
* @Desc类说明:提交Vo
*/
@ApiModel
@Data
@ToString(callSuper = true)
public class StartSubmitVo {
	
	
	/**
	 * 审核用户id	
	 */
	@ApiModelProperty(value = "审核用户id")
	private String userId;

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
	
	@ApiModelProperty(value = "项目编号")
	private String projectNo;

	@ApiModelProperty(value = "项目名称")
	private String projectName;
	
}
