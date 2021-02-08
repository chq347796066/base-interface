package com.spring.account.service.impl;

import com.spring.account.service.IAccountChangeRecordService;
import com.spring.account.dao.IAccountChangeRecordDao;
import com.spring.base.entity.account.AccountChangeRecordEntity;
import com.spring.base.vo.pay.accountchangerecode.AccountChangeRecordAddVo;
import com.spring.base.vo.pay.accountchangerecode.AccountChangeRecordUpdateVo;
import com.spring.common.request.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.base.dao.BaseDao;
import com.spring.base.service.impl.BaseServiceImpl;
import org.springframework.beans.BeanUtils;
import com.spring.common.constants.MessageCode;
import com.spring.common.response.ApiResponseResult;
import lombok.extern.slf4j.Slf4j;
import java.util.Date;

 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-11-07 17:18:11
 * @Desc类说明: 业务接口实现类
 */

 @Slf4j
@Service("accountChangeRecordService")
public class AccountChangeRecordServiceImpl extends BaseServiceImpl<AccountChangeRecordEntity, Long> implements IAccountChangeRecordService {
	
	@Autowired
	private IAccountChangeRecordDao accountChangeRecordDao;

	@Override
	public BaseDao getBaseMapper() {
		return accountChangeRecordDao;
	}
	
	/**
	 * 新增
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua
	 * @version 创建时间：2020-11-07 17:18:11
	 */
	@Override
	public ApiResponseResult addAccountChangeRecord(AccountChangeRecordAddVo vo) throws Exception {
		// 返回的对象
		ApiResponseResult result = new ApiResponseResult();
		AccountChangeRecordEntity entity = new AccountChangeRecordEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setStatus(1);
		entity.setCreateUser(RequestUtils.getUserId());
		entity.setCreateDate(new Date());
		// 新增
		int no = accountChangeRecordDao.insert(entity);
		if (no > 0) {
			result.setCode(MessageCode.SUCCESS);
			result.setMsg("成功");
		} else {
			result.setCode(MessageCode.FAIL);
			result.setMsg("新增失败");
		}
		return result;
	}

	/**
	 * 更新
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-11-07 17:18:11
	 */
	@Override
	public ApiResponseResult updateAccountChangeRecord(AccountChangeRecordUpdateVo vo) throws Exception {
		AccountChangeRecordEntity entity = new AccountChangeRecordEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setModifyUser(RequestUtils.getUserId());
		entity.setModifyDate(new Date());
		// 更新
		int no = accountChangeRecordDao.updateById(entity);
		if (no > 0) {
			return createSuccessResult(null);
		}
		return createFailResult();
	}
}
