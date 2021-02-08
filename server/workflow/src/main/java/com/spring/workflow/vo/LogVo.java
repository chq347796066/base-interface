package com.spring.workflow.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.base.vo.Vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2019-10-25 16:05:33
 * @Desc类说明: 实体类
 */
@ApiModel
@Data
@ToString(callSuper = true)
public class LogVo implements Vo<String>, Serializable {
	private static final long serialVersionUID = 1L;

	// 主键
	@ApiModelProperty(value = "主键")
	private String id;

	// 类型
	@ApiModelProperty(value = "类型")
	private String type;

	// 关键字
	@ApiModelProperty(value = "关键字")
	private String key;

	// 信息
	@ApiModelProperty(value = "信息")
	private String info;

	// ip
	@ApiModelProperty(value = "ip")
	private String ip;

	// 创建人
	@ApiModelProperty(value = "创建人")
	private String createBy;

	// 创建时间
	@ApiModelProperty(value = "创建时间")
	@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate;

	// 开始时间
	@ApiModelProperty(value = "开始时间")
	@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date beginDate;

	// 结束时间
	@ApiModelProperty(value = "结束时间")
	@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endDate;

}
