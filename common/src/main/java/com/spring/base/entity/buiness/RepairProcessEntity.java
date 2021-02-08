package com.spring.base.entity.buiness;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.base.vo.Vo;
import com.spring.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.ToString;

 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2021-01-05 14:57:47
 * @Desc类说明: 报修流程实体类
 */
@ApiModel
@Data
@ToString(callSuper = true)
@TableName("r_repair_process")
public class RepairProcessEntity extends BaseEntity implements Vo<Long>, Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主键")
	private Long id;

	/**
	 * 报修id
	 */
	@ApiModelProperty(value = "报修id")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long repairId;

	/**
	 * 处理人
	 */
	@ApiModelProperty(value = "处理人")
	private String handleUser;

	/**
	 * 处理时间
	 */
	@ApiModelProperty(value = "处理时间")
	private Date handleDate;

	/**
	 * 处理人电话
	 */
	@ApiModelProperty(value = "处理人电话")
	private String handlePhone;

	 /**
	  * 处理人岗位id
	  */
	 @ApiModelProperty(value = "处理人岗位id")
	 private String handleJobId;

	 /**
	  * 处理人岗位名称
	  */
	 @ApiModelProperty(value = "处理人岗位名称")
	 private String handleJobName;

	/**
	 * 处理类型(1业主提交,2业主取消,3关闭工单,4派工,5转单,6维修接单,7待支付,8已完成,9已评价)
	 */
	@ApiModelProperty(value = "处理类型(1业主提交,2业主取消,3关闭工单,4派工,5转单,6维修接单,7待支付,8已完成,9已评价)")
	private Integer processType;

	/**
	 * 维修人姓名
	 */
	@ApiModelProperty(value = "维修人姓名")
	private String processUser;

	 /**
	  * 维修人电话
	  */
	 @ApiModelProperty(value = "维修人电话")
	 private String processPhone;

	 /**
	  * 维修人岗位id
	  */
	 @ApiModelProperty(value = "维修人岗位id")
	 private String processJobId;

	 /**
	  * 维修人岗位名称
	  */
	 @ApiModelProperty(value = "维修人岗位名称")
	 private String processJobName;

	/**
	 * 处理备注
	 */
	@ApiModelProperty(value = "处理备注")
	private String remark;

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
