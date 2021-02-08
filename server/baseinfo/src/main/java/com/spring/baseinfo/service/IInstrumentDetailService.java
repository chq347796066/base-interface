package com.spring.baseinfo.service;

import com.spring.base.entity.baseinfo.InstrumentDetailEntity;
import com.spring.base.vo.baseinfo.instrumentdetail.InstrumentDetailAddVo;
import com.spring.base.vo.baseinfo.instrumentdetail.InstrumentDetailUpdateVo;
import com.spring.base.service.IBaseService;
import com.spring.common.response.ApiResponseResult;

 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-03 16:03:23
 * @Desc类说明: 仪明细业务接口类
 */
public interface IInstrumentDetailService extends IBaseService<InstrumentDetailEntity,String>{
	
	/**
	 * 新增仪明细
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-04-03 16:03:23
	 */
    ApiResponseResult addInstrumentDetail(InstrumentDetailAddVo vo) throws Exception;
	
	/**
	 * 更仪明细
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-04-03 16:03:23
	 */
    ApiResponseResult updateInstrumentDetail(InstrumentDetailUpdateVo vo) throws Exception;
}
