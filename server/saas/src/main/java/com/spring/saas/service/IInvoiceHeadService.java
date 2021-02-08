package com.spring.saas.service;


import com.spring.base.entity.saas.InvoiceHeadEntity;
import com.spring.base.entity.saas.OrderEntity;
import com.spring.base.service.IBaseService;
import com.spring.base.vo.saas.InvoiceHeadAddVo;
import com.spring.base.vo.saas.InvoiceHeadUpdateVo;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;

 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-09 10:48:56
 * @Desc类说明: 发票抬头业务接口类
 */
public interface IInvoiceHeadService extends IBaseService<InvoiceHeadEntity,String> {
	
	/**
	 * 新增发票抬头
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-07-09 10:48:56
	 */
	 ApiResponseResult addInvoiceHead(InvoiceHeadAddVo vo) throws Exception;
	
	/**
	 * 更发票抬头
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-07-09 10:48:56
	 */
	 ApiResponseResult updateInvoiceHead(InvoiceHeadUpdateVo vo) throws Exception;

	 /**
	  * 删除发票抬头
	  * @author 熊锋
	  * @param id
	  * @date 2020/7/9 11:28
	  * @return com.spring.common.response.ApiResponseResult
	  * @throws Exception
	  */
	 ApiResponseResult deleteInvoiceHead(String id) throws Exception;

	 /**
	  * 设置默认
	  * @param id
	  * @author 熊锋
	  * @date 2020/7/9 11:28
	  * @return com.spring.common.response.ApiResponseResult
	  * @throws Exception
	  */
	 ApiResponseResult setDefault(String id) throws Exception;

	 /**
	  * 查询默认发票抬头
	  * @param mobile
	  * @author 熊锋
	  * @date 2020/7/9 14:11
	  * @return
	  * @throws Exception
	  */
	 ApiResponseResult queryDefaultInvoice(String mobile) throws Exception;

	 /**
	  * 查询发票可索取金额
	  * @param mobile
	  * @author 熊锋
	  * @date 2020/7/9 14:11
	  * @return
	  * @throws Exception
	  */
	 ApiResponseResult queryInvoiceMoney(String mobile) throws Exception;

	 /**
	  * 查询可开票订单
	  * @param requestPageVO
	  * @author 熊锋
	  * @date 2020/7/9 14:11
	  * @return
	  * @throws Exception
	  */
	 ApiResponseResult queryInvoiceOrder(RequestPageVO<OrderEntity> requestPageVO) throws Exception;

}
