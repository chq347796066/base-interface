package com.spring.saas.service;

import com.spring.base.entity.saas.ApplicationEntity;
import com.spring.base.service.IBaseService;
import com.spring.base.vo.saas.ApplicationAddVo;
import com.spring.base.vo.saas.ApplicationUpdateVo;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-01 14:52:37
 * @Desc类说明: 应用业务接口类
 */
public interface IApplicationService extends IBaseService<ApplicationEntity,String>{
	
	/**
	 * 新增应用
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-07-01 14:52:37
	 */
	 ApiResponseResult addApplication(ApplicationAddVo vo) throws Exception;

	/**
	 * 删除应用
	 * @author 熊锋
	 * @param id
	 * @date 2020/7/8 16:28
	 * @return com.spring.common.response.ApiResponseResult
	 * @throws Exception
	 */
	 ApiResponseResult deleteApp(String id) throws Exception;

	 /**
	  * 更新应用
	  * @param vo
	  * @return
	  * @throws Exception
	  * @author 作者：ZhaoJinHua
	  * @version 创建时间：2020-07-01 14:52:37
	  */
	 ApiResponseResult updateApplication(ApplicationUpdateVo vo) throws Exception;

	 /**
	  * 查询可订购应用
	  * @param requestPageVO
	  * @return
	  * @throws Exception
	  * @author 作者：ZhaoJinHua
	  * @version 创建时间：2020-07-01 14:52:37
	  */
	 ApiResponseResult queryAppList(RequestPageVO<ApplicationEntity> requestPageVO) throws Exception;

 }
