package com.spring.base.vo.baseinfo.community;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotNull;

/**
 * @Desc:    首页-管理概况统计小区
 * @Author:邓磊
 * @UpdateDate:2020/5/12 10:30
 */
@ApiModel
@Data
public class CommunityStatisticsVo {
	//公司id
	@ApiModelProperty(value = "公司id")
	private String companyId;

	@ApiModelProperty(value = "管理小区总数")
	private Integer  communityTotal;

	//管理户数
	@ApiModelProperty(value = "管理户数")
	private Integer allHouseholds;

	//管理面积上
	@ApiModelProperty(value = "管理面积")
	private Integer architectureArea;
}
