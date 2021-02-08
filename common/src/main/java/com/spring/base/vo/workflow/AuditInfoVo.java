package com.spring.base.vo.workflow;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
* @author 作者：赵进华 
* @version 创建时间：2019年6月28日 下午5:32:42
* @Desc类说明:审核记录vo
*/
@ApiModel
@Data
@ToString
public class AuditInfoVo {
	@ApiModelProperty(value = "时间")
	@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate;
	
	@ApiModelProperty(value = "节点名称")
	private String processName;
	
	@ApiModelProperty(value = "操作")
	private String operation;
	
	@ApiModelProperty(value = "操作人")
	private String operationMan;
	
	@ApiModelProperty(value = "备注")
	private String remark;
}
