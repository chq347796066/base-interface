package com.spring.workflow.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
* @author 作者：赵进华 
* @version 创建时间：2019年10月18日 下午3:13:05
* @Desc类说明:节点变更vo
*/
@ApiModel
@Data
@ToString
public class NodeChangeVo {
	//项目立项管理ID
	@ApiModelProperty(value = "项目立项管理ID")
	private String proApprovalId;

	//实施计划ID
	@ApiModelProperty(value = "实施计划ID")
	private String proApprovalImplPlanId;
	
	//调整申报日期
	@ApiModelProperty(value = "调整申报日期")
	@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date changeDeclareDate;
}
