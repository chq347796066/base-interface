package com.spring.saas.service;


import com.spring.base.entity.saas.CompanyScaleEntity;
import com.spring.base.service.IBaseService;
import com.spring.base.vo.saas.CompanyScaleAddVo;
import com.spring.base.vo.saas.CompanyScaleUpdateVo;
import com.spring.common.response.ApiResponseResult;

 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-02 15:17:12
 * @Desc类说明: 公司规模业务接口类
 */
public interface ICompanyScaleService extends IBaseService<CompanyScaleEntity,String> {
	
	/**
	 * 新增公司规模
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-07-02 15:17:12
	 */
	 ApiResponseResult addCompanyScale(CompanyScaleAddVo vo) throws Exception;
	
	/**
	 * 更公司规模
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-07-02 15:17:12
	 */
	 ApiResponseResult updateCompanyScale(CompanyScaleUpdateVo vo) throws Exception;
}
