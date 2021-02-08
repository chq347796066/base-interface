package com.spring.base.vo.baseinfo.company;


import com.spring.base.entity.baseinfo.PicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-31 19:02:26
 * @Desc类说明: 新增 公司vo
 */
 
@ApiModel
@Data
@ToString
public class CompanyAddVo<picEntityList> {
	
	//公司代码
	@ApiModelProperty(value = "公司代码")
	private String companyCode;
	
	//公司名称
	@ApiModelProperty(value = "公司名称")
	@NotNull(message = "公司名称不能为空")
	private String companyName;

	//父节点, 第一级为0
	@ApiModelProperty(value = "父节点, 第一级为0")
	@NotNull(message = "父节点不能为空")
	private String parent;
	
	//公司电话
	@ApiModelProperty(value = "公司电话")
	@NotNull(message = "公司电话不能为空")
	private String mobile;
	
	//公司地址
	@ApiModelProperty(value = "公司地址")
	@NotNull(message = "公司地址不能为空")
	private String address;
	
	//开户行
	@ApiModelProperty(value = "开户行")
	private String openBank;
	
	//银行账户
	@ApiModelProperty(value = "银行账户")
	private String bankAccount;
	
	//法人姓名
	@ApiModelProperty(value = "法人姓名")
	@NotNull(message = "法人姓名不能为空")
	private String legalName;
	
	//法人电话
	@ApiModelProperty(value = "法人电话")
	@NotNull(message = "法人电话不能为空")
	private String legalMobile;
	
	//logo
	@ApiModelProperty(value = "logo")
	@NotNull(message = "公司logo不能为空")
	private String logo;
	
	//营业执照
	@ApiModelProperty(value = "营业执照")
	@NotNull(message = "公司营业执照不能为空")
	private String businessLicense;

	@ApiModelProperty(value = "公司省市区")
	@NotNull(message = "公司省市区不能为空")
	private String companyAddress;
	
	//状态(1启用,2禁用)
	@ApiModelProperty(value = "状态(1启用,2禁用)")
	private Integer status;

	@ApiModelProperty(value = "公司层级")
	private int companyLevel;

	//图片集合
	@ApiModelProperty(value = "图片集合)")
	private List<PicEntity> picEntityList;

}
