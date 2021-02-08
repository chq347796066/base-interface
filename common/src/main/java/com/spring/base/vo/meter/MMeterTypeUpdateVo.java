package com.spring.base.vo.meter;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.base.vo.Vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;


 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-10-28 14:30:45
 * @Desc类说明: 更新仪类型vo
 */
@ApiModel
@Data
@ToString
public class MMeterTypeUpdateVo  {
	
	/**
	 * 
	 */
	@ApiModelProperty(value = "")
	private Long id;
	
	/**
	 * 
	 */
	@ApiModelProperty(value = "")
	private String typeName;
	
	/**
	 * 计量单位
	 */
	@ApiModelProperty(value = "计量单位")
	private String unit;
	
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String mark;
	
}
