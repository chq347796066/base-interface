package com.spring.base.vo.baseinfo.car;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;


/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-31 19:02:26
 * @Desc类说明: 更新 车位信息vo
 */
@ApiModel
@Data
@ToString
public class CarUpdateVo  {
	
	//主键
	@ApiModelProperty(value = "主键")
	@NotNull(message = "主键id不能为空")
	private String id;
	
	//小区id
	@ApiModelProperty(value = "小区id")
	@NotNull(message = "小区id不能为空")
	private String communityId;

	 //客户id
	 @ApiModelProperty(value = "客户id")
	 private String customerId;
	
	//车位编码
	@ApiModelProperty(value = "车位编码")
	@NotNull(message = "车位编码不能为空")
	private String carCode;
	
	//车牌号
	@ApiModelProperty(value = "车牌号")
	private String carNo;
	
	//车位面积
	@ApiModelProperty(value = "车位面积")
	private String area;
	
	//车位类型
	@ApiModelProperty(value = "车位类型")
	private Integer carType;
	
	//车位状态
	@ApiModelProperty(value = "车位状态")
	private Integer carStatus;
	
	//状态(1启用,2禁用)
	@ApiModelProperty(value = "状态(1启用,2禁用)")
	private Integer status;
	
}
