package com.spring.pay.service;

import com.spring.base.entity.pay.ModuleEntity;
import com.spring.base.vo.pay.module.ModuleAddVo;
import com.spring.base.vo.pay.module.ModuleUpdateVo;
import com.spring.base.service.IBaseService;
import com.spring.common.response.ApiResponseResult;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-18 17:32:18
 * @Desc类说明: 业务接口类
 */
public interface IModuleService extends IBaseService<ModuleEntity,String>{
	
	/**
	 * 新增
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-03-18 17:32:18
	 */
    ApiResponseResult addModule(ModuleAddVo vo) throws Exception;
	
	/**
	 * 更
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-03-18 17:32:18
	 */
    ApiResponseResult updateModule(ModuleUpdateVo vo) throws Exception;

	/**
	 * @description:查询组件列表
	 * @return:
	 * @author: 赵进华
	 * @time: 2020/3/23 16:07
	 */
    ApiResponseResult queryModuleList() throws Exception;
}
