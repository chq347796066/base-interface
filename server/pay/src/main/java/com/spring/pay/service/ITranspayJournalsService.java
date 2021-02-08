package com.spring.pay.service;

import com.spring.base.entity.pay.TranspayJournalsEntity;
import com.spring.base.vo.pay.transpayjournals.TranspayJournalsAddVo;
import com.spring.base.vo.pay.transpayjournals.TranspayJournalsUpdateVo;
import com.spring.base.service.IBaseService;
import com.spring.common.response.ApiResponseResult;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-23 09:49:14
 * @Desc类说明: 支付记录业务接口类
 */
public interface ITranspayJournalsService extends IBaseService<TranspayJournalsEntity,String>{
	
	/**
	 * 新增支付记录
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-04-23 09:49:14
	 */
    ApiResponseResult addTranspayJournals(TranspayJournalsAddVo vo) throws Exception;
	
	/**
	 * 更支付记录
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-04-23 09:49:14
	 */
    ApiResponseResult updateTranspayJournals(TranspayJournalsUpdateVo vo) throws Exception;
}
