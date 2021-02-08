package com.spring.base.vo.workflow;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
* @author 作者：赵进华 
* @version 创建时间：2019年5月22日 下午4:08:08
* @Desc类说明:字典数据vo
*/
@ApiModel
@Data
@ToString
public class DictionaryDataVo {

	// label
	@ApiModelProperty(value = "字典名称")
	private String label;
	
	// value
	@ApiModelProperty(value = "字典值")
	private String value;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "DictionaryDataVo [label=" + label + ", value=" + value + "]";
	}
	
	
}
