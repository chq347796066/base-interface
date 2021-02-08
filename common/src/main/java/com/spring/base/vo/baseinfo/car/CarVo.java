package com.spring.base.vo.baseinfo.car;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import lombok.Data;


/**
 * @Desc:    车位导入映射实体类
 * @Author:邓磊
 * @UpdateDate:2020/4/21 10:59
 */
@ApiModel
@Data
public class CarVo {
	
	@Excel(name = "小区名称")
	private String communityName;

	@Excel(name = "车位编码")
	private String carCode;

	//车位类型
	@Excel(name = "车位类型")
	private String carType;

	//车位状态
	@Excel(name = "车位状态")
	private String carStatus;

	//车位面积
	@Excel(name = "车位面积")
	private String area;

	//客户姓名
	@Excel(name = "客户姓名")
	private String customerName;

	//性别(1男,2女)
	@Excel(name = "客户性别")
	private String sex;

	//客户类型 1 个人 2单位 3开发商
	@Excel(name = "客户类别")
	private String customerType;

	//联系电话
	@Excel(name = "客户电话")
	private String phone;

	//证件类型 1 身份证
	@Excel(name = "证件类型")
	private String certificatesType;

	//证件号码
	@Excel(name = "客户证件")
	private String certificatesNumber;






	
}
