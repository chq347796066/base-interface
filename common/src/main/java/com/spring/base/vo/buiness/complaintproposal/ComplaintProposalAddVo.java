package com.spring.base.vo.buiness.complaintproposal;

import com.spring.base.entity.baseinfo.PicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-11 15:49:31
 * @Desc类说明: 新增业主投诉建议vo
 */
 
@ApiModel
@Data
@ToString
public class ComplaintProposalAddVo {
	//小区id
	@ApiModelProperty(value = "小区id")
	@NotNull(message = "小区id不能为空")
	private String communityId;

	//小区名称
	@ApiModelProperty(value = "小区名称")
	@NotNull(message = "小区名称不能为空")
	private String communityName;

	//楼栋Id
	@ApiModelProperty(value = "楼栋Id")
	@NotNull(message = "楼栋Id不能为空")
	private String buildId;

	//楼栋名称
	@ApiModelProperty(value = "楼栋名称")
	@NotNull(message = "楼栋名称不能为空")
	private String buildName;

	//单元Id
	@ApiModelProperty(value = "单元Id")
	@NotNull(message = "单元Id不能为空")
	private String cellId;

	//单元名称
	@ApiModelProperty(value = "单元名称")
	@NotNull(message = "单元名称不能为空")
	private String cellName;

	//房产Id
	@ApiModelProperty(value = "房产Id")
	@NotNull(message = "房产Id不能为空")
	private String houseId;

	//房屋编码
	@ApiModelProperty(value = "房屋编码")
	@NotNull(message = "房屋编码不能为空")
	private String houseCode;
	
	//投诉描述
	@ApiModelProperty(value = "投诉描述")
	private String complaintDescribe;
	
	//投诉图片ID
	@ApiModelProperty(value = "投诉图片ID")
	private String pictureId;
	
	//用户id
	@ApiModelProperty(value = "用户id")
	@NotNull(message = "用户id不能为空")
	private String userId;
	
	//用户姓名
	@ApiModelProperty(value = "用户姓名")
	@NotNull(message = "用户姓名不能为空")
	private String userName;
	
	//手机号码
	@ApiModelProperty(value = "手机号码")
	@NotNull(message = "手机号码不能为空")
	private String mobile;
	
	//小区详细地址
	@ApiModelProperty(value = "小区详细地址")
	@NotNull(message = "小区详细地址不能为空")
	private String complaintAddress;
	
	//提交人
	@ApiModelProperty(value = "提交人")
	private String submitUser;
	
	//投诉状态（1 待处理 2 已完成 3 已评价　４已取消）
	@ApiModelProperty(value = "投诉状态（1 待处理 2 已完成 3 已评价　４已取消）")
	private Integer proposalStatus;
	
	//审核完成处理结果
	@ApiModelProperty(value = "审核完成处理结果")
	private String resultDescribe;
	
	//审核完成处理结果图片ID
	@ApiModelProperty(value = "审核完成处理结果图片ID")
	private String resultId;
	
	//取消原因（1 原因１ 2 原因２ 3 原因３　４其他）
	@ApiModelProperty(value = "取消原因（1 原因１ 2 原因２ 3 原因３　４其他）")
	private Integer canceState;
	
	//取消描述
	@ApiModelProperty(value = "取消描述")
	private String canceDescribe;
	
	//评价状态（1 好评 2 中评 3 差评）
	@ApiModelProperty(value = "评价状态（1 好评 2 中评 3 差评）")
	private Integer evaluationStatus;
	
	//评价描述
	@ApiModelProperty(value = "评价描述")
	private String evaluationDescribe;
	
	//投诉建议开始日期
	@ApiModelProperty(value = "投诉建议开始日期")
	private String startDate;
	
	//投诉建议完成日期
	@ApiModelProperty(value = "投诉建议完成日期")
	private String endDate;
	
	//创建人
	@ApiModelProperty(value = "创建人")
	private String createUser;
	
	//修改人
	@ApiModelProperty(value = "修改人")
	private String modifyUser;
	
	//取消人员
	@ApiModelProperty(value = "取消人员")
	private String proposalReserve;
	
	//删除标志（0 未删除 1 已删除）
	@ApiModelProperty(value = "删除标志（0 未删除 1 已删除）")
	private Integer delFlag;
	
	//租户id
	@ApiModelProperty(value = "租户id")
	private String tenantId;

	//投诉建议图片
	@ApiModelProperty(value = "投诉图片)")
	private List<PicEntity> picEntityList;
	
	
}
