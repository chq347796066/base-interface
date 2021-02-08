package com.spring.saas.service;


import com.spring.base.entity.saas.IndustryEntity;
import com.spring.base.service.IBaseService;
import com.spring.base.vo.saas.IndustryAddVo;
import com.spring.base.vo.saas.IndustryUpdateVo;
import com.spring.common.response.ApiResponseResult;

 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-02 15:17:11
 * @Desc类说明: 行业业务接口类
 */
public interface IIndustryService extends IBaseService<IndustryEntity,String> {
	
	/**
	 * 新增行业
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-07-02 15:17:11
	 */
	 ApiResponseResult addIndustry(IndustryAddVo vo) throws Exception;
	
	/**
	 * 更新行业
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-07-02 15:17:11
	 */
	 ApiResponseResult updateIndustry(IndustryUpdateVo vo) throws Exception;
}
