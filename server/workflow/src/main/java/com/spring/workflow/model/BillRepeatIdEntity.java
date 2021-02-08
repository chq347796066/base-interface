package com.spring.workflow.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-01-16 00:58:44
 * @Desc类说明: 实体类
 */
@ApiModel
@Data
@ToString(callSuper = true)
public class BillRepeatIdEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	@ApiModelProperty(value = "")
	private Integer id;
	
	//
	@ApiModelProperty(value = "")
	private String type;
	
	
}
