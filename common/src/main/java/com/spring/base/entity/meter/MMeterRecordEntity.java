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
import java.math.BigDecimal;
import java.util.Date;

 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-10-28 14:30:45
 * @Desc类说明: 房屋抄记录实体类
 */
@ApiModel
@Data
@ToString(callSuper = true)
@TableName("m_meter_record")
public class MMeterRecordEntity extends BaseEntity implements Vo<Long>, Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	@ApiModelProperty(value = "")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long id;

	 /**
	  *
	  */
	 @ApiModelProperty(value = "")
	 private String meterNum;


	/**
	 * 
	 */
	@ApiModelProperty(value = "")
	private String communityId;
	/**
	 * 
	 */
	@ApiModelProperty(value = "")
	private String buildId;
	/**
	 * 
	 */
	@ApiModelProperty(value = "")
	private String cellId;
	/**
	 * 
	 */
	@ApiModelProperty(value = "")
	private String houseId;

	 /**
	  *
	  */
	 @ApiModelProperty(value = "仪表名称")
	 private String meterTypeName;
	 /**
	  *
	  */
	 @ApiModelProperty(value = "房屋编码")
	 private String houseCode;
	 /**
	  *
	  */
	 @ApiModelProperty(value = "房号")
	 private String houseNo;
	 /**
	  *
	  */
	 @ApiModelProperty(value = "单元名称")
	 private String cellName;
	 /**
	  *
	  */
	 @ApiModelProperty(value = "楼栋名称")
	 private String buildName;
	 /**
	  *
	  */
	 @ApiModelProperty(value = "小区名称")
	 private String communityName;

	/**
	 * 
	 */
	@ApiModelProperty(value = "")
	private String meterTypeId;
	/**
	 * 
	 */
	@ApiModelProperty(value = "")
	private BigDecimal lastData;
	/**
	 * 
	 */
	@ApiModelProperty(value = "")
	private BigDecimal currentData;
	/**
	 * 用量
	 */
	@ApiModelProperty(value = "用量")
	private BigDecimal dosage;
	/**
	 * 
	 */
	@ApiModelProperty(value = "")
	private Date lastReadDate;
	/**
	 * 
	 */
	@ApiModelProperty(value = "")
	private Date currentReadDate;
	/**
	 * 是否抄表（0:未抄表；1:已抄表）
	 */
	@ApiModelProperty(value = "是否抄表（0:未抄表；1:已抄表）")
	private Integer readCheck;


 }
