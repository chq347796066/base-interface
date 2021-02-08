package com.spring.base.vo.baseinfo.community;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;


/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-31 19:02:26
 * @Desc类说明: 新增小区信息vo
 */
 
@ApiModel
@Data
@ToString
public class CommunityAddVo {
	
	//小区编号
	@ApiModelProperty(value = "小区编号")
	private String communityCode;
	
	//小区名称
	@ApiModelProperty(value = "小区名称")
	@NotNull(message = "小区名称不能为空")
	private String communityName;

	//小区地址
	@ApiModelProperty(value = "小区地址")
	private String communityAddress;
	
	@ApiModelProperty(value = "小区详细地址")
	private String communityAddressDetails;

	 //小区电话
	 @ApiModelProperty(value = "小区电话")
	 @NotNull(message = "小区电话不能为空")
	 private String communityMobile;
	
	//占地面积
	@ApiModelProperty(value = "占地面积")
	private Double floorArea;
	
	//建筑面积
	@ApiModelProperty(value = "建筑面积")
	private Double architectureArea;
	
	//总户数
	@ApiModelProperty(value = "总户数")
	private Integer allHouseholds;
	
	//容积率
	@ApiModelProperty(value = "容积率")
	private Float plotRatio;

	//状态(1启用,2禁用)
	@ApiModelProperty(value = "状态(1启用,2禁用)")
	private Integer status;

	//租户公司数组
	@ApiModelProperty(value = "租户公司数组")
	private String[] tenantCompanyArray;

}
