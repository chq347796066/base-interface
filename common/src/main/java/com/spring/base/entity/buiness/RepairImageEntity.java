package com.spring.base.entity.buiness;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.base.vo.Vo;
import com.spring.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.ToString;

 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2021-01-05 14:57:47
 * @Desc类说明: 报修图片实体类
 */
@ApiModel
@Data
@ToString(callSuper = true)
@TableName("r_repair_image")
public class RepairImageEntity extends BaseEntity implements Vo<Long>, Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主键")
	private Long id;
	/**
	 * 报修id
	 */
	@ApiModelProperty(value = "报修id")
	private Long repairId;
	/**
	 * 文档id
	 */
	@ApiModelProperty(value = "图片路径")
	private String picPath;
	/**
	 * 文档类型(1报修图片,2维修完图片,3支付图片)
	 */
	@ApiModelProperty(value = "图片类型(1报修图片,2维修完图片,3支付图片)")
	private Integer docType;
	/**
	 * 小区id
	 */
	@ApiModelProperty(value = "小区id")
	private String communityId;
	/**
	 * 公司id
	 */
	@ApiModelProperty(value = "公司id")
	private String companyId;

}
