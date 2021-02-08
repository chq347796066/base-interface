package com.spring.pay.service;

import com.spring.base.entity.pay.BusinessJournalsEntity;
import com.spring.base.vo.pay.businessjournals.BusinessJournalsAddVo;
import com.spring.base.vo.pay.businessjournals.BusinessJournalsUpdateVo;
import com.spring.base.vo.pay.payonekeypay.BillOffsetVo;
import com.spring.base.vo.pay.payonekeypay.PayOneVo;
import com.spring.base.vo.pay.queryrecord.QueryRecordVo;
import com.spring.common.page.RequestPageVO;
import com.spring.base.service.IBaseService;
import com.spring.common.response.ApiResponseResult;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-23 09:49:14
 * @Desc类说明: 交流流水记录业务接口类
 */
public interface IBusinessJournalsService extends IBaseService<BusinessJournalsEntity,String>{
	
	/**
	 * 新增交流流水记录
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-04-23 09:49:14
	 */
    ApiResponseResult addBusinessJournals(BusinessJournalsAddVo vo) throws Exception;
	
	/**
	 * 更交流流水记录
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-04-23 09:49:14
	 */
    ApiResponseResult updateBusinessJournals(BusinessJournalsUpdateVo vo) throws Exception;

	/**
	 * 一键缴费
	 * @param vo
	 * @return
	 * @throws Exception
	 */
    ApiResponseResult payOneKeyPay(PayOneVo vo) throws Exception;

	/**
	 * 查询缴费记录
	 * @param vo
	 * @return
	 * @throws Exception
	 */
    ApiResponseResult payQueryRecordList(RequestPageVO<QueryRecordVo> requestPageVO) throws Exception;


	
	/**
	 * @Desc:收费历史记录导出
	 * @param recordVo
	 * @Author:邓磊
	 * @UpdateDate:2020/5/20 14:25
	 * @return: 返回
	 */
	void  exportRecordInfo(QueryRecordVo recordVo);

	ApiResponseResult payBillOffset(BillOffsetVo vo) throws Exception;

}
