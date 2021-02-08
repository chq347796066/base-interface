package com.spring.base.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* @author 作者：赵进华 
* @version 创建时间：2019年6月20日 上午9:41:58
* @Desc类说明:自定义字段分页Vo
*/
@ApiModel
@Data
@ToString(callSuper = true)
public class PageResultVo {
	
	//当前页
	@ApiModelProperty(value = "当前页")
	private Integer currentPage;
	
	
	//每页多少条
	@ApiModelProperty(value = "每页多少条")
	private Integer pageSize;
	

	 //总记录数
	@ApiModelProperty(value = "总记录数")
    private long total;
    
	@ApiModelProperty(value = "数据列表")
	List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
}
