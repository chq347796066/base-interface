package com.spring.base.vo.pay.accountchangerecode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;


/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-11-07 17:18:11
 * @Desc类说明: 更新vo
 */
@ApiModel
@Data
@ToString
public class AccountChangeRecordUpdateVo  {
	
	//主键
	@ApiModelProperty(value = "主键")
	private Long id;
	
	//流水号
	@ApiModelProperty(value = "流水号")
	private String transId;
	
	//客户号
	@ApiModelProperty(value = "客户号")
	private String custNo;
	
	//变动时间
	@ApiModelProperty(value = "变动时间")
	private Date changeTime;
	
	//变动账号
	@ApiModelProperty(value = "变动账号")
	private String changeAccountNo;
	
	//变动的账户余额
	@ApiModelProperty(value = "变动的账户余额")
	private String changeBalance;
	
	//变动的利息金额
	@ApiModelProperty(value = "变动的利息金额")
	private String changeInterestAmount;
	
	//变动金额
	@ApiModelProperty(value = "变动金额")
	private String changeAmount;
	
	//处理状态 01-已落账02-待审核
            
	@ApiModelProperty(value = "处理状态 01-已落账 02-待审核 ")
	private String processStatus;
	
	//变动原因 01-充值02-提现03-转账04-抵扣05-利息收益（批量执行）06-赠送07-冻结08-解冻
            
	@ApiModelProperty(value = "变动原因 01-充值 02-提现 03-转账 04-抵扣 05-利息收益（批量执行） 06-赠送 07-冻结 08-解冻 ")
	private String changeReason;
	
	//变动类型 01-增加02-减少03-冻结04-解冻
            
	@ApiModelProperty(value = "变动类型 01-增加 02-减少 03-冻结 04-解冻 ")
	private String changeType;
	
	//
	@ApiModelProperty(value = "")
	private String changeMark;
	
	//变动前余额
	@ApiModelProperty(value = "变动前余额")
	private String preAmount;
	
	//子账户类型 
	@ApiModelProperty(value = "子账户类型 ")
	private String subAccountType;
	
	//子账号名称
	@ApiModelProperty(value = "子账号名称")
	private String subAccountName;
	
	//机构编号
	@ApiModelProperty(value = "机构编号")
	private String orgNo;
	
	//房号id
	@ApiModelProperty(value = "房号id")
	private String hid;
	
	//状态(1启用,2禁用)
	@ApiModelProperty(value = "状态(1启用,2禁用)")
	private Integer status;
	
}
