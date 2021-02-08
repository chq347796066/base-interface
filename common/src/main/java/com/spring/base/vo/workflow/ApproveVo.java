package com.spring.base.vo.workflow;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author 作者：赵进华
 * @version 创建时间：2019年6月5日 下午3:38:26
 * @Desc类说明:审核Vo
 */
@ApiModel
@Data
@ToString(callSuper = true)
public class ApproveVo {

	@ApiModelProperty(value = "审核用户id")
	private String userId;
	
	@ApiModelProperty(value = "审核用户名")
	private String userName;

	/**
	 * 业务id
	 */
	@ApiModelProperty(value = "业务id")
	private String businessId;
	
	/**
	 * 项目编号
	 */
	@ApiModelProperty(value = "项目编号")
	private String projectNo;
	

	/**
	 * 业务类型
	 */
	@ApiModelProperty(value = "业务类型")
	private String businessType;

	/**
	 * 流程实例id
	 */
	@ApiModelProperty(value = "流程实例id")
	private String processInstanceId;

	@ApiModelProperty(value = "备注")
	private String remark;
	
}
