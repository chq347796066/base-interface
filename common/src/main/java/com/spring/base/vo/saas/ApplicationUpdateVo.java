package com.spring.base.vo.saas;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-01 14:52:37
 * @Desc类说明: 更新应用vo
 */
@ApiModel
@Data
@ToString
public class ApplicationUpdateVo  {
	
	//应用id
	@ApiModelProperty(value = "应用id")
	private String id;
	
	//应用编号
	@ApiModelProperty(value = "应用编号")
	private String appCode;
	
	//应用名称
	@ApiModelProperty(value = "应用名称")
	@NotNull(message = "应用名称不能为空")
	private String appName;
	
	//网址
	@ApiModelProperty(value = "网址")
	private String url;
	
	//应用价格(默认一个月一个小区多少钱)
	@ApiModelProperty(value = "应用价格(默认一个月一个小区多少钱)")
	private BigDecimal appPrice;
	
	//图片
	@ApiModelProperty(value = "图片")
	private String appPic;
	
	//描述
	@ApiModelProperty(value = "描述")
	private String description;
	
	//状态(1：已下架  2：已上架)
	@ApiModelProperty(value = "状态(1：已下架  2：已上架)")
	private Integer appStatus;

	
}
