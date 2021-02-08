package com.spring.base.vo.meter;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;


 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-10-28 14:30:45
 * @Desc类说明: 更新房屋抄记录vo
 */
@ApiModel
@Data
@ToString
public class MMeterRecordUpdateVo  {

	/**
	 * 
	 */
	@ApiModelProperty(value = "")
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
	
//	/**
//	 *
//	 */
//	@ApiModelProperty(value = "")
//	private Date lastReadDate;
	
	/**
	 * 
	 */
	@ApiModelProperty(value = "")
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	private Date currentReadDate;

	
}
