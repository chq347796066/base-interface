package com.spring.base.entity.saas;

import java.io.Serializable;

import com.spring.base.entity.SaasBaseEntity;
import com.spring.base.vo.Vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.ToString;

 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-10 09:43:02
 * @Desc类说明: 发票订单关系实体类
 */
@ApiModel
@Data
@ToString(callSuper = true)
@TableName("s_invoice_order")
public class InvoiceOrderEntity extends SaasBaseEntity implements Vo<String>, Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键id
	 */
	@ApiModelProperty(value = "主键id")
	private String id;
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
