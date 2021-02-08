package com.spring.business.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;


/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2021-01-05 14:57:47
 * @Desc类说明: 新增报修评价论vo
 */
 
@ApiModel
@Data
@ToString
public class RepairCommentAddVo {
	
	/**
	 * 报修id
	 */
	@ApiModelProperty(value = "报修id")
	private Long repairId;
	
	/**
	 * 评论星级
	 */
	@ApiModelProperty(value = "评论星级")
	private Integer commentStar;

	/**
	 * 评论信息
	 */
	@ApiModelProperty(value = "评论信息")
	private List<RepairCommentVo> repairInfoList;

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
