package com.spring.pay.service;

import com.spring.base.entity.pay.DisttransJournalsEntity;
import com.spring.base.service.IBaseService;
import com.spring.base.vo.pay.disttransjournals.DisttransJournalsAddVo;
import com.spring.base.vo.pay.disttransjournals.DisttransJournalsUpdateVo;
import com.spring.common.response.ApiResponseResult;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-23 09:49:14
 * @Desc类说明: 优惠流水记录业务接口类
 */
public interface IDisttransJournalsService extends IBaseService<DisttransJournalsEntity,String>{
	
	/**
	 * 新增优惠流水记录
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-04-23 09:49:14
	 */
    ApiResponseResult addDisttransJournals(DisttransJournalsAddVo vo) throws Exception;
	
	/**
	 * 更优惠流水记录
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-04-23 09:49:14
	 */
    ApiResponseResult updateDisttransJournals(DisttransJournalsUpdateVo vo) throws Exception;
}
