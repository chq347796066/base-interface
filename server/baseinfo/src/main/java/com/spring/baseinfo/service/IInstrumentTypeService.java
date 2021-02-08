package com.spring.baseinfo.service;

import com.spring.base.entity.baseinfo.InstrumentTypeEntity;
import com.spring.base.service.IBaseService;
import com.spring.base.vo.baseinfo.instrumenttype.InstrumentTypeAddVo;
import com.spring.base.vo.baseinfo.instrumenttype.InstrumentTypeUpdateVo;
import com.spring.common.response.ApiResponseResult;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-02 17:17:10
 * @Desc类说明: 仪类型业务接口类
 */
public interface IInstrumentTypeService extends IBaseService<InstrumentTypeEntity,Long> {
	
	/**
	 * 新增仪类型
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-04-02 17:17:10
	 */
	 ApiResponseResult addInstrumentType(InstrumentTypeAddVo vo) throws Exception;
	
	/**
	 * 更仪类型
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-04-02 17:17:10
	 */
	 ApiResponseResult updateInstrumentType(InstrumentTypeUpdateVo vo) throws Exception;


	 /**
	  * 关联删除
	  */
	  ApiResponseResult deleteHouseinstrument(Long id) throws Exception;

	 /**
	  * @Desc:仪表类型管理导出
	  * @param voEntity
	  * @Author:邓磊
	  * @UpdateDate:2020/6/2 13:57
	  */
	  void  exportInstrumentTypeEntityInfo(InstrumentTypeEntity voEntity) throws Exception;

}
