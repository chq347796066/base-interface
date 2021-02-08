package com.spring.saas.service;


import com.spring.base.entity.saas.InvoiceEntity;
import com.spring.base.service.IBaseService;
import com.spring.base.vo.saas.*;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;

import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-09 10:48:56
 * @Desc类说明: 发票信息业务接口类
 */
public interface IInvoiceService extends IBaseService<InvoiceEntity,String> {

	/**
	 * 新增发票信息
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua
	 * @version 创建时间：2020-07-09 10:48:56
	 */
	 ApiResponseResult addInvoice(InvoiceAddVo vo) throws Exception;

	 /**
	  * 查询发票列表
	  * @author 熊锋
	  * @param requestPageVO
	  * @date 2020/7/10 11:31
	  * @return com.spring.common.response.ApiResponseResult
	  * @throws Exception
	  */
	 ApiResponseResult queryInvoice(RequestPageVO<InvoiceQueryVo> requestPageVO) throws Exception;

	 /**
	  * 查询开票详情
	  * @author 熊锋
	  * @param id
	  * @date 2020/7/10 14:06
	  * @return com.spring.common.response.ApiResponseResult
	  * @throws Exception
	  */
	 ApiResponseResult queryMakeInvoiceDetail(String id) throws Exception;

	/**
	 * 分页查询开票查询（运营后台）
	 *
	 * @param requestPageVO
	 * @return
	 * @throws Exception
	 * @author WuJiaQuan
	 * @date 2020/7/10 19:24
	 */
	ApiResponseResult invoiceInquiryQueryPage(RequestPageVO<InvoiceInquiryQueryVo> requestPageVO) throws Exception;

	/**
	 * 查询开票摘要
	 *
	 * @param invoiceId
	 * @return
	 * @throws Exception
	 * @author WuJiaQuan
	 * @date 2020/7/13 14:56
	 */
	ApiResponseResult queryInvoiceSummary(String invoiceId) throws Exception;

	/**
	 * 查询开票详情
	 *
	 * @param invoiceId
	 * @return
	 * @throws Exception
	 * @author WuJiaQuan
	 * @date 2020/7/13 15:22
	 */
	ApiResponseResult queryInvoiceDetailList(String invoiceId) throws Exception;

    /**
     * 审核发票
     *
     * @param invoiceReviewVo
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @date 2020/7/13 15:36
     */
    ApiResponseResult reviewInvoice(InvoiceReviewVo invoiceReviewVo) throws Exception;

	/**
	 * 查询开票摘要
	 * @author 熊锋
	 * @param id
	 * @date 2020/7/10 14:06
	 * @return com.spring.common.response.ApiResponseResult
	 * @throws Exception
	 */
    ApiResponseResult queryMakeInvoiceSummary(String id) throws Exception;
}
