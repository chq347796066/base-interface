package com.spring.pay.service;

import com.spring.base.entity.pay.TransJournalsEntity;
import com.spring.base.vo.pay.transjournals.TodayPramVo;
import com.spring.base.vo.pay.transjournals.TransJournalsAddVo;
import com.spring.base.vo.pay.transjournals.TransJournalsUpdateVo;
import com.spring.base.service.IBaseService;
import com.spring.common.response.ApiResponseResult;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-23 09:49:14
 * @Desc类说明: 缴费记录业务接口类
 */
public interface ITransJournalsService extends IBaseService<TransJournalsEntity,String>{
	
	/**
	 * 新增缴费记录
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-04-23 09:49:14
	 */
    ApiResponseResult addTransJournals(TransJournalsAddVo vo) throws Exception;
	
	/**
	 * 更缴费记录
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-04-23 09:49:14
	 */
    ApiResponseResult updateTransJournals(TransJournalsUpdateVo vo) throws Exception;


	/**
	 * 查询今日收费
	 * @param todayPramVo
	 * @return
	 * @throws Exception
	 */
    ApiResponseResult queryTodayFees(TodayPramVo todayPramVo) throws Exception;
}
