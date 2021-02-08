package com.spring.base.vo.baseinfo.dictionary;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author 作者：赵进华
 * @version 创建时间：2019年5月22日 下午4:08:08
 * @Desc类说明:字典数据vo
 */
@ApiModel
@Data
@ToString
public class DictionaryDataVo implements Serializable {
	private static final long serialVersionUID = 1L;

	// label
	@ApiModelProperty(value = "字典名称")
	private String label;

	// value
	@ApiModelProperty(value = "字典值")
	private String value;

	@ApiModelProperty(value = "子项")
	private List<DictionaryDataVo> children;
}
