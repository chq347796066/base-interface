package com.spring.base.vo.buiness.reportingrepai;

import com.spring.base.entity.baseinfo.PicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;


/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-11 16:03:31
 * @Desc类说明: 新增业主报事报修vo
 */
 
@ApiModel
@Data
@ToString
public class ReportingRepairAddVo {
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
	
	//报修描述
	@ApiModelProperty(value = "报修描述")
	@NotNull(message = "报修描述不能为空")
	private String reportingDescribe;
	
	//报修图片ID
	@ApiModelProperty(value = "报修图片ID")
	private String repairId;
	
	//报修类型（1 个人报修 2 公共报修）
	@ApiModelProperty(value = "报修类型（1 个人报修 2 公共报修）")
	private Integer repairType;
	
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
	
	//报修状态（1 待派单 2 待接单 3 处理中　４待确认 5 已完成 6 已评价 7 已取消）
	@ApiModelProperty(value = "报修状态（1 待派单 2 待接单 3 处理中　４待确认 5 已完成 6 已评价 7 已取消）")
	private Integer reportingStatus;
	
	//取消原因（1 原因１ 2 原因２ 3 原因３　４其他）
	@ApiModelProperty(value = "取消原因（1 原因１ 2 原因２ 3 原因３　４其他）")
	private Integer canceState;
	
	//取消描述
	@ApiModelProperty(value = "取消描述")
	private String canceDescribe;
	
	//维修费用
	@ApiModelProperty(value = "维修费用")
	private BigDecimal outlay;
	
	//维修明细
	@ApiModelProperty(value = "维修明细")
	private String repairDetail;
	
	//维修费用图片ID
	@ApiModelProperty(value = "维修费用图片ID")
	private String outlayId;
	
	//评价状态（1 好评 2 中评 3 差评）
	@ApiModelProperty(value = "评价状态（1 好评 2 中评 3 差评）")
	private Integer evaluationStatus;
	
	//评价描述
	@ApiModelProperty(value = "评价描述")
	private String evaluationDescribe;
	
	//报事报修开始日期
	@ApiModelProperty(value = "报事报修开始日期")
	private String startDate;
	
	//报事报修完成日期
	@ApiModelProperty(value = "报事报修完成日期")
	private String endDate;
	
	//创建人
	@ApiModelProperty(value = "创建人")
	private String createUser;
	
	//修改人
	@ApiModelProperty(value = "修改人")
	private String modifyUser;
	
	//预留字段
	@ApiModelProperty(value = "预留字段")
	private String repairReserve;
	
	//删除标志（0 未删除 1 已删除）
	@ApiModelProperty(value = "删除标志（0 未删除 1 已删除）")
	private Integer delFlag;
	
	//租户id
	@ApiModelProperty(value = "租户id")
	private String tenantId;

	//业主报修图片
	@ApiModelProperty(value = "业主报修图片)")
	private List<PicEntity> picEntityList;
}
