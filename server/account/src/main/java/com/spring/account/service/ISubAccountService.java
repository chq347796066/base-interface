package com.spring.account.service;

import com.spring.base.entity.account.SubAccountEntity;
import com.spring.base.service.IBaseService;
import com.spring.base.vo.pay.subaccount.SubAccountAddVo;
import com.spring.base.vo.pay.subaccount.SubAccountUpdateVo;
import com.spring.common.response.ApiResponseResult;

 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-11-07 17:18:11
 * @Desc类说明: 业务接口类
 */
public interface ISubAccountService extends IBaseService<SubAccountEntity,Long> {
	
	/**
	 * 新增
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-11-07 17:18:11
	 */
	public ApiResponseResult addSubAccount(SubAccountAddVo vo) throws Exception;
	
	/**
	 * 更
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-11-07 17:18:11
	 */
	public ApiResponseResult updateSubAccount(SubAccountUpdateVo vo) throws Exception;
}
