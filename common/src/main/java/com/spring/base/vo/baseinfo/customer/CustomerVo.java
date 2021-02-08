package com.spring.base.vo.baseinfo.customer;
import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @Desc:    导入客户信息实体类
 * @Author:邓磊
 * @UpdateDate:2020/4/21 19:59
 */
@ApiModel
@Data
public class CustomerVo {
	//小区名称
	@Excel(name = "小区名称")
	private String communityName;

	//客户姓名
	@Excel(name = "客户名称")
	private String customerName;

	//客户类型 1 个人 2单位 3开发商
	@Excel(name = "客户类别")
	private String customerType;
	
	//性别(1男,2女)
	@Excel(name = "性别")
	private String sex;
	
	//证件类型 1 身份证
	@Excel(name = "证件类型")
	private String certificatesType;
	
	//证件号码
	@Excel(name = "证件号码")
	private String certificatesNumber;
	
	//联系电话
	@Excel(name = "联系电话")
	private String phone;
	
	//籍贯
	@Excel(name = "籍贯")
	private String nativePlace;
	
	//学历
	@Excel(name = "学历")
	private String education;
	
	//职业
	@Excel(name = "职业")
	private String occupation;
	
	//客户地址
	@Excel(name = "客户地址")
	private String communityAddress;
}
