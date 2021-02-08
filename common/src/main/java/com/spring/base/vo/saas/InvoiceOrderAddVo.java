package com.spring.base.vo.saas;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import lombok.Data;
import lombok.ToString;

 
 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-10 09:43:02
 * @Desc类说明: 新增发票订单关系vo
 */
 
@ApiModel
@Data
@ToString
public class InvoiceOrderAddVo {
	
	/**
	 * 发票id
	 */
	@ApiModelProperty(value = "发票id")
	private String invoiceId;
	
	/**
	 * 订单id
	 */
	@ApiModelProperty(value = "订单id")
	private String orderId;
	
	
}
