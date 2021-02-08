package com.spring.base.vo.baseinfo.house;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;


/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-31 19:02:26
 * @Desc类说明: 更新房产信息vo
 */
@ApiModel
@Data
@ToString
public class HouseUpdateVo  {
	
	@ApiModelProperty(value = "主键ID")
	@NotNull(message = "主键ID不能为空")
	private String id;
	
	//小区id
	@ApiModelProperty(value = "小区id")
	@NotNull(message = "小区id不能为空")
	private String communityId;

	//客户id
	@ApiModelProperty(value = "客户id")
	private String customerId;
	
	//楼栋id
	@ApiModelProperty(value = "楼栋id")
	@NotNull(message = "楼栋id不能为空")
	private String buildId;
	
	// 单元id
	@ApiModelProperty(value = " 单元id")
	@NotNull(message = "单元id不能为空")
	private String cellId;
	
	//楼层
	@ApiModelProperty(value = "楼层")
	@NotNull(message = "楼层不能为空")
	private String floor;
	
	//门牌号
	@ApiModelProperty(value = "门牌号")
	@NotNull(message = "门牌号不能为空")
	private String houseNo;
	
	//房屋编码
	@ApiModelProperty(value = "房屋编码")
	@NotNull(message = "房屋编码不能为空")
	private String houseCode;
	
	//房间类型
	@ApiModelProperty(value = "房间类型")
	private Integer houseType;
	
	//房间状态
	@ApiModelProperty(value = "房间状态")
	private Integer houseStatus;
	
	//建筑面积
	@ApiModelProperty(value = "建筑面积")
	@NotNull(message = "建筑面积不能为空")
	private String architectureArea;

	 //使用面积
	 @ApiModelProperty(value = "使用面积")
	 private String useArea;
	
	//业主姓名
	@ApiModelProperty(value = "业主姓名")
	private String ownerName;
	
	//业主电话
	@ApiModelProperty(value = "业主电话")
	private String ownerMobile;
	
	//业主证件
	@ApiModelProperty(value = "业主证件")
	private String ownerCard;
	
	//状态(1启用,2禁用)
	@ApiModelProperty(value = "状态(1启用,2禁用)")
	private Integer status;
	
}
