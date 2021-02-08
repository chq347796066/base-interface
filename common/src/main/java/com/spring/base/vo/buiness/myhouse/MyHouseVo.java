package com.spring.base.vo.buiness.myhouse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;


/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-17 09:46:08
 * @Desc类说明: 新增我的房屋信息vo
 */
 
@ApiModel
@Data
public class MyHouseVo {
	
	//小区id
	@ApiModelProperty(value = "小区id")
	@NotNull(message = "小区id不能为空")
	private String communityId;

	//楼栋Id
	@ApiModelProperty(value = "楼栋Id")
	@NotNull(message = "楼栋Id不能为空")
	private String buildId;
	
	//单元Id
	@ApiModelProperty(value = "单元Id")
	@NotNull(message = "单元Id不能为空")
	private String cellId;

	//房产Id
	@ApiModelProperty(value = "房产Id")
	@NotNull(message = "房产Id不能为空")
	private String houseId;
	
	//房屋编码
	@ApiModelProperty(value = "房屋编码")
	@NotNull(message = "房屋编码不能为空")
	private String houseCode;
	
	//校验业主姓名
	@ApiModelProperty(value = "校验业主姓名")
	@NotNull(message = "业主姓名不能为空")
	private String ownerName;
	
	//校验业主手机号
	@ApiModelProperty(value = "校验业主手机号")
	@NotNull(message = "业主手机号不能为空")
	private String ownerPhone;


}
