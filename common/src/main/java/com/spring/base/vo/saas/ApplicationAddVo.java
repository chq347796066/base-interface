package com.spring.base.vo.saas;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

 
 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-01 14:52:37
 * @Desc类说明: 新增应用vo
 */
 
@ApiModel
@Data
@ToString
public class ApplicationAddVo {
	
	//应用编号
	@ApiModelProperty(value = "应用编号")
	private String appCode;
	
	//应用名称
	@ApiModelProperty(value = "应用名称")
	@NotBlank(message = "应用名称不能为空")
	private String appName;
	
	//网址
	@ApiModelProperty(value = "网址")
	private String url;
	
	//应用价格(默认一个月一个小区多少钱)
	@ApiModelProperty(value = "应用价格(默认一个月一个小区多少钱)")
	@NotNull(message = "应用价格不能为空")
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

	 /**
	  * 0 扩展应用,1 试用应用,1 主应用
	  */
	 @ApiModelProperty(value = "0 扩展应用,1 试用应用,1 主应用")
	 private Integer appType;

	 //菜单列表
	 @ApiModelProperty(value = "菜单列表")
	 private String[] menuIdList;
}
