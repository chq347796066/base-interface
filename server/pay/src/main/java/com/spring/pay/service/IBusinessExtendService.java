package com.spring.pay.service;

import com.spring.base.entity.pay.BusinessExtendEntity;
import com.spring.base.service.IBaseService;
import com.spring.base.vo.pay.businessextend.BusinessExtendAddVo;
import com.spring.base.vo.pay.businessextend.BusinessExtendUpdateVo;
import com.spring.common.response.ApiResponseResult;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-23 09:49:14
 * @Desc类说明: 业务流水扩展业务接口类
 */
public interface IBusinessExtendService extends IBaseService<BusinessExtendEntity,String>{
	
	/**
	 * 新增业务流水扩展
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-04-23 09:49:14
	 */
    ApiResponseResult addBusinessExtend(BusinessExtendAddVo vo) throws Exception;
	
	/**
	 * 更业务流水扩展
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-04-23 09:49:14
	 */
    ApiResponseResult updateBusinessExtend(BusinessExtendUpdateVo vo) throws Exception;
}
