package com.spring.pay.service;

import com.spring.base.entity.pay.PayBillsEntity;
import com.spring.base.service.IBaseService;
import com.spring.base.vo.pay.paybills.BillParamVo;
import com.spring.base.vo.pay.paybills.PayBillsAddVo;
import com.spring.base.vo.pay.paybills.PayBillsUpdateVo;
import com.spring.common.response.ApiResponseResult;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-23 09:49:14
 * @Desc类说明: 缴费账单业务接口类
 */
public interface IPayBillsService extends IBaseService<PayBillsEntity,String>{
	
	/**
	 * 新增缴费账单
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-04-23 09:49:14
	 */
    ApiResponseResult addPayBills(PayBillsAddVo vo) throws Exception;
	
	/**
	 * 更缴费账单
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-04-23 09:49:14
	 */
    ApiResponseResult updatePayBills(PayBillsUpdateVo vo) throws Exception;

	/**
	 * 账单预览
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua
	 * @version 创建时间：2020-04-23 09:49:14
	 */
    ApiResponseResult queryPreview(BillParamVo billParamVo) throws Exception;

	/**
	 * 账单生成
	 * @param billParamVo
	 * @return
	 * @throws Exception
	 */
    ApiResponseResult createBill(BillParamVo billParamVo) throws Exception;
}
