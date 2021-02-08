package com.spring.saas.service;

import com.spring.base.entity.saas.InvoiceOrderEntity;
import com.spring.base.service.IBaseService;
import com.spring.base.vo.saas.InvoiceOrderAddVo;
import com.spring.base.vo.saas.InvoiceOrderUpdateVo;
import com.spring.common.response.ApiResponseResult;

 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-10 09:43:02
 * @Desc类说明: 发票订单关系业务接口类
 */
public interface IInvoiceOrderService extends IBaseService<InvoiceOrderEntity,String> {
	
	/**
	 * 新增发票订单关系
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-07-10 09:43:02
	 */
	 ApiResponseResult addInvoiceOrder(InvoiceOrderAddVo vo) throws Exception;
	
	/**
	 * 更发票订单关系
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-07-10 09:43:02
	 */
	 ApiResponseResult updateInvoiceOrder(InvoiceOrderUpdateVo vo) throws Exception;
}
