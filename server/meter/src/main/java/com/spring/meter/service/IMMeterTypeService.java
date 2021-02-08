package com.spring.meter.service;

import com.spring.base.entity.meter.MMeterTypeEntity;
import com.spring.base.service.IBaseService;
import com.spring.base.vo.meter.MMeterTypeAddVo;
import com.spring.base.vo.meter.MMeterTypeUpdateVo;
import com.spring.base.vo.meter.MeterIds;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;

import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-10-28 14:30:45
 * @Desc类说明: 仪类型业务接口类
 */
public interface IMMeterTypeService extends IBaseService<MMeterTypeEntity,Long>{
	
	/**
	 * 新增仪类型
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-10-28 14:30:45
	 */
	 ApiResponseResult addMMeterType(MMeterTypeAddVo vo) throws Exception;
	
	/**
	 * 更仪类型
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-10-28 14:30:45
	 */
	 ApiResponseResult updateMMeterType(MMeterTypeUpdateVo vo) throws Exception;

     ApiResponseResult singelDelete(Long id) throws Exception;

	 ApiResponseResult batchDeleteById(MeterIds ids) throws Exception;

     ApiResponseResult queryMeterTypePage(RequestPageVO<MMeterTypeEntity> requestPageVO) throws Exception;

    void exportMeterTypeInfo(MMeterTypeEntity vo) throws Exception;
}
