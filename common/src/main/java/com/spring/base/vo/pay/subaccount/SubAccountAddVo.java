package com.spring.base.vo.pay.subaccount;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import lombok.Data;
import lombok.ToString;

 
 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-11-07 17:18:11
 * @Desc类说明: 新增vo
 */
 
@ApiModel
@Data
@ToString
public class SubAccountAddVo {
	
	//业主姓名
	@ApiModelProperty(value = "业主姓名")
	private String pname;
	
	//业主编号
	@ApiModelProperty(value = "业主编号")
	private String pid;
	
	//房屋编号
	@ApiModelProperty(value = "房屋编号")
	private String hid;
	
	//账号
	@ApiModelProperty(value = "账号")
	private String subAccountNo;
	
	//客户号
	@ApiModelProperty(value = "客户号")
	private String custNo;
	
	//账号类型 01-平台活期账号02-公司活期账号03-物业费账号04-车位费账号05-水费账号06-电费账号07-煤气费账号
            
	@ApiModelProperty(value = "账号类型 01-平台活期账号 02-公司活期账号 03-物业费账号 04-车位费账号 05-水费账号 06-电费账号 07-煤气费账号 ")
	private String subAccountType;
	
	//账号名称
	@ApiModelProperty(value = "账号名称")
	private String subAccountName;
	
	//账号状态 01-正常 02-冻结
            
	@ApiModelProperty(value = "账号状态 01-正常 02-冻结 ")
	private String subAccountStatus;
	
	//账号状态 0 - 不计息1-计息2-参考小区配置
	@ApiModelProperty(value = "账号状态 0 - 不计息 1-计息 2-参考小区配置")
	private String interestFlag;
	
	//当前账户余额
	@ApiModelProperty(value = "当前账户余额")
	private String currentBalance;
	
	//变动前余额
	@ApiModelProperty(value = "变动前余额")
	private String preAmount;
	
	//冻结金额
	@ApiModelProperty(value = "冻结金额")
	private String freezeAmount;
	
	//利息金额
	@ApiModelProperty(value = "利息金额")
	private String interestAmount;
	
	//返还金额
	@ApiModelProperty(value = "返还金额")
	private String returnAmount;
	
	//赠送金额
	@ApiModelProperty(value = "赠送金额")
	private String donateAmount;
	
	//不可退金额
	@ApiModelProperty(value = "不可退金额")
	private String finalAmount;
	
	//
	@ApiModelProperty(value = "")
	private String openBranch;
	
	//开户机构
	@ApiModelProperty(value = "开户机构")
	private String openOrg;
	
	//客户名
	@ApiModelProperty(value = "客户名")
	private String custName;
	
	
}
