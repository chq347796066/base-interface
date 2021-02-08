package com.spring.base.entity.buiness;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.spring.base.entity.BaseEntity;
import com.spring.base.vo.Vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2021-01-05 14:57:47
 * @Desc类说明: 报修评价论实体类
 */
@ApiModel
@ToString(callSuper = true)
@TableName("r_repair_comment")
@Data
public class RepairCommentEntity extends BaseEntity implements Vo<Long>, Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
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
	 * 评论id
	 */
	@ApiModelProperty(value = "评论id")
	private String commentId;
	/**
	 * 评论
	 */
	@ApiModelProperty(value = "评论")
	private String commentName;
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
