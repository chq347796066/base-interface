package com.spring.account.service;

import com.spring.base.entity.account.AccountChangeRecordEntity;
import com.spring.base.service.IBaseService;
import com.spring.base.vo.pay.accountchangerecode.AccountChangeRecordAddVo;
import com.spring.base.vo.pay.accountchangerecode.AccountChangeRecordUpdateVo;
import com.spring.common.response.ApiResponseResult;

 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-11-07 17:18:11
 * @Desc类说明: 业务接口类
 */
public interface IAccountChangeRecordService extends IBaseService<AccountChangeRecordEntity,Long>{
	
	/**
	 * 新增
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-11-07 17:18:11
	 */
	public ApiResponseResult addAccountChangeRecord(AccountChangeRecordAddVo vo) throws Exception;
	
	/**
	 * 更
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-11-07 17:18:11
	 */
	public ApiResponseResult updateAccountChangeRecord(AccountChangeRecordUpdateVo vo) throws Exception;
}
