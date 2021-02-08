package com.spring.base.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
* @author 作者：赵进华 
* @version 创建时间：2019年3月25日 上午10:25:06
* @Desc类说明:查询业务日志Vo
*/
@ApiModel
@Data
@ToString(callSuper = true)
public class SearchBussinessLogVo {

	@ApiModelProperty(value = "当前页")
	private Integer currentPage;
	

	@ApiModelProperty(value = "每页记录数")
	private Integer pageSize;

	@ApiModelProperty(value = "关键字")
	private String key;

	@ApiModelProperty(value = "起始时间:格式2019-06-20 00:00:00")
	private String beginDate;
	
	@ApiModelProperty(value = "结束时间:格式2019-06-20 24:59:59")
	private String endDate;
}
