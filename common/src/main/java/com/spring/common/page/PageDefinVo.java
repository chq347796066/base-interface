package com.spring.common.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
* @author 作者：赵进华 
* @version 创建时间：2019年6月20日 上午11:19:57
* @Desc类说明:自定义条件分页vo
*/
@ApiModel
@Data
@ToString(callSuper = true)
public class PageDefinVo {

	@ApiModelProperty(value = "当前页")
	private Integer currentPage;
	
	@ApiModelProperty(value = "每页多少条")
	private Integer pageSize;
    
	@ApiModelProperty(value = "条件map")
    private Map<String, Object> map=new HashMap<String, Object>();
}
