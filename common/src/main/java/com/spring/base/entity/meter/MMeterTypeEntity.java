package com.spring.base.entity.meter;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.base.entity.BaseEntity;
import com.spring.base.vo.Vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-10-28 14:30:45
 * @Desc类说明: 仪类型实体类
 */
@ApiModel
@Data
@ToString(callSuper = true)
@TableName("m_meter_type")
public class MMeterTypeEntity extends BaseEntity implements Vo<Long>, Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	@ApiModelProperty(value = "主键id")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
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
