package com.spring.base.vo.buiness.myhouse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-17 09:46:08
 * @Desc类说明: 更新我的房屋信息vo
 */
@ApiModel
@Data
@ToString
public class MyHouseUpdateVo {
	
	// 主键
	@ApiModelProperty(value = " 主键")
	@NotNull(message = "主键Id不能为空")
	private String id;
	
	//流程编号
	@ApiModelProperty(value = "流程编号")
	private String processCode;
	
	//流程名称
	@ApiModelProperty(value = "流程名称")
	private String processName;
	
	//用户Id
	@ApiModelProperty(value = "用户Id")
	private String userId;
	
	//用户姓名
	@ApiModelProperty(value = "用户姓名")
	private String userName;
	
	//公司id
	@ApiModelProperty(value = "公司id")
	private String companyId;
	
	//公司名称
	@ApiModelProperty(value = "公司名称")
	private String companyName;
	
	//小区id
	@ApiModelProperty(value = "小区id")
	private String communityId;
	
	//小区名称
	@ApiModelProperty(value = "小区名称")
	private String communityName;
	
	//楼栋Id
	@ApiModelProperty(value = "楼栋Id")
	private String buildId;
	
	//楼栋名称
	@ApiModelProperty(value = "楼栋名称")
	private String buildName;
	
	//单元Id
	@ApiModelProperty(value = "单元Id")
	private String cellId;
	
	//单元名称
	@ApiModelProperty(value = "单元名称")
	private String cellName;
	
	//房产Id
	@ApiModelProperty(value = "房产Id")
	private String houseId;
	
	//房屋编码
	@ApiModelProperty(value = "房屋编码")
	private String houseCode;
	
	//校验业主姓名
	@ApiModelProperty(value = "校验业主姓名")
	private String ownerName;
	
	//校验业主手机号
	@ApiModelProperty(value = "校验业主手机号")
	private String ownerPhone;
	
	//审核状态(0 待审核 1已通过 2未通过)
	@ApiModelProperty(value = "审核状态(0 待审核 1已通过 2未通过)")
	private Integer auditStatus;
	
	//审核操作者用户ID
	@ApiModelProperty(value = "审核操作者用户ID")
	private String auditUserId;
	
	//审核操作者名称
	@ApiModelProperty(value = "审核操作者名称")
	private String auditUserName;
	
	//审核操作者时间
	@ApiModelProperty(value = "审核操作者时间")
	private Date auditDate;
	
	//审核通过描述
	@ApiModelProperty(value = "审核通过描述")
	private String auditAdopt;
	
	//审核未通过描述
	@ApiModelProperty(value = "审核未通过描述")
	private String auditNotPass;
	
	//身份类型(0 待审核 1已通过 2未通过)
	@ApiModelProperty(value = "身份类型(0 待审核 1已通过 2未通过)")
	private Integer identityType;
	
	//认证类型(1 房屋认证 2公司审核)
	@ApiModelProperty(value = "认证类型(1 房屋认证 2公司审核)")
	private Integer authType;
	
	//联系电话
	@ApiModelProperty(value = "联系电话")
	private String mobile;
	
	//性别(1男,2女)
	@ApiModelProperty(value = "性别(1男,2女)")
	private Integer sex;
	
	//身份证号
	@ApiModelProperty(value = "身份证号")
	private String idCard;
	
	//申请时间
	@ApiModelProperty(value = "申请时间")
	private Date applyDate;
	
	//租户id
	@ApiModelProperty(value = "租户id")
	private String tenantId;
	
	//删除标志（0 未删除 1 已删除）
	@ApiModelProperty(value = "删除标志（0 未删除 1 已删除）")
	private Integer delFlag;
	
}
