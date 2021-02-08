package com.spring.base.vo.baseinfo.dictionary;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author 作者：赵进华
 * @version 创建时间：2019年5月21日 下午1:56:35
 * @Desc类说明:字典实体类
 */
@ApiModel
@Data
@ToString(callSuper = true)
public class DictionaryNodeVo implements Serializable {
	private static final long serialVersionUID = 1L;

	//主键
	@ApiModelProperty(value = "主键")
	private String id;

	//父ID
	@ApiModelProperty(value = "父ID")
	private String dictParentId;

	//类型
	@ApiModelProperty(value = "类型")
	private String dictParentCode;

	//代码
	@ApiModelProperty(value = "代码")
	private String dictCode;

	//名称
	@ApiModelProperty(value = "名称")
	private String dictName;

	//字典类型
	@ApiModelProperty(value = "字典类型")
	private String dictType;

	//层级
	@ApiModelProperty(value = "层级")
	private Integer dictLevel;

	//排序
	@ApiModelProperty(value = "排序")
	private Integer dictOrder;

	//备注
	@ApiModelProperty(value = "备注")
	private String dictDesc;

	//状态(1启用,2禁用)
	@ApiModelProperty(value = "状态(1启用,2禁用,3-系统默认)")
	private Integer status;

	//创建人
	@ApiModelProperty(value = "创建人")
	private String createUser;

	//创建时间
	@ApiModelProperty(value = "创建时间")
	@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate;

	private List<DictionaryNodeVo> children;
}
