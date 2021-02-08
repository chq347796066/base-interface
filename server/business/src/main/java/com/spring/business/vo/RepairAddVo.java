package com.spring.business.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2021-01-05 14:57:47
 * @Desc类说明: 新增报事报修vo
 */
 
@ApiModel
@Data
@ToString
public class RepairAddVo {
	
	/**
	 * 报修单号
	 */
	@ApiModelProperty(value = "报修单号")
	private String repairOrder;
	
	/**
	 * 报修单类型(1业主报修,2代客报修)
	 */
	@ApiModelProperty(value = "报修单类型(1业主报修,2代客报修)")
	@NotNull(message = "报修单类型不能为空")
	private Integer orderType;
	
	/**
	 * 报修区域
	 */
	@ApiModelProperty(value = "报修区域")
	@NotNull(message = "报修区域不能为空")
	private Integer repairRegion;
	
	/**
	 * 报修类型
	 */
	@ApiModelProperty(value = "报修类型")
	private String repairType;
	
	/**
	 * 报修描述
	 */
	@ApiModelProperty(value = "报修描述")
	@NotBlank(message = "报修描述不能为空")
	private String content;
	
	/**
	 * 业主id
	 */
	@ApiModelProperty(value = "业主id")
	private String ownerId;
	
	/**
	 * 业主姓名
	 */
	@ApiModelProperty(value = "业主姓名")
	private String ownerName;
	
	/**
	 * 业主电话
	 */
	@ApiModelProperty(value = "业主电话")
	private String phone;
	
	/**
	 * 业主地址
	 */
	@ApiModelProperty(value = "业主地址")
	private String address;
	
	/**
	 * 期望开始上门时间
	 */
	@ApiModelProperty(value = "期望开始上门时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
	private Date hopeBeginTime;
	
	/**
	 * 期望结束上门时间
	 */
	@ApiModelProperty(value = "期望结束上门时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
	private Date hopeEndTime;
	
	/**
	 * 维修人
	 */
	@ApiModelProperty(value = "维修人")
	private String handleUser;
	
	/**
	 * 维修金额
	 */
	@ApiModelProperty(value = "维修金额")
	private BigDecimal cost;
	
	/**
	 * 维修结果
	 */
	@ApiModelProperty(value = "维修结果")
	private String repairResult;
	
	/**
	 * 完成时间
	 */
	@ApiModelProperty(value = "完成时间")
	private Date finishDate;
	
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

	/**
	 * 公司id
	 */
	@ApiModelProperty(value = "图片路径")
	private List<String> picUrlList;

}
