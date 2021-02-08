package com.spring.base.entity.account;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.spring.base.vo.Vo;
import com.spring.base.entity.BaseEntity;
import com.spring.common.util.excel.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.ToString;

 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-11-07 17:18:11
 * @Desc类说明: 实体类
 */
@ApiModel
@Data
@ToString(callSuper = true)
@TableName("a_sub_account")
public class SubAccountEntity extends BaseEntity implements Vo<Long>, Serializable {
	private static final long serialVersionUID = 1L;

	 //
	 @ApiModelProperty(value = "小区名称")
	 @Excel(name="小区名称")
	 private String communityName;

	 //楼栋名称
	 @ApiModelProperty(value = "楼栋名称")
	 @Excel(name="楼栋")
	 private String buildName;

	 //单元名称
	 @ApiModelProperty(value = "单元名称")
	 @Excel(name="单元")
	 private String cellName;

	 //房屋编码
	 @ApiModelProperty(value = "房屋编码")
	 @Excel(name="房屋编码")
	 private String houseCode;

	 //业主姓名
	 @ApiModelProperty(value = "业主姓名")
	 @Excel(name = "客户姓名")
	 private String pname;


	 //当前账户余额
	 @ApiModelProperty(value = "当前账户余额")
	 @Excel(name = "账户余额")
	 private String currentBalance;


	//主键
	@ApiModelProperty(value = "主键")
	private Long id;
	

	
	//业主编号
	@ApiModelProperty(value = "业主编号")
	@Excel(name = "业主编号")
	private String pid;
	
	//房屋编号
	@ApiModelProperty(value = "房屋编号")
	@Excel(name = "房屋编号")
	private String hid;
	
	//账号
	@ApiModelProperty(value = "账号")
	@Excel(name = "账号")
	private String subAccountNo;
	
	//客户号
	@ApiModelProperty(value = "客户号")
	@Excel(name = "客户号")
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
	@ApiModelProperty(value = "小区id")
	private String openBranch;


	//开户机构
	@ApiModelProperty(value = "开户机构")
	private String openOrg;
	
	//客户名
	@ApiModelProperty(value = "客户名")
	private String custName;
	
	//状态(1启用,2禁用)
	@ApiModelProperty(value = "状态(1启用,2禁用)")
	private Integer status;

	 //楼栋id
	 @ApiModelProperty(value = "楼栋id")
	 private String buildId;



	 //单元id
	 @ApiModelProperty(value = "单元id")
	 private String cellId;





     //文件类型
     @ApiModelProperty(value ="文件类型")
	 @TableField(exist = false)
     private String excelType;
}
