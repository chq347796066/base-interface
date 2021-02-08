package com.spring.account.service.impl;

import com.spring.account.dao.ISubAccountDao;
import com.spring.account.service.ISubAccountService;
import com.spring.base.entity.account.SubAccountEntity;
import com.spring.base.vo.pay.subaccount.SubAccountAddVo;
import com.spring.base.vo.pay.subaccount.SubAccountUpdateVo;
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
@Service("subAccountService")
public class SubAccountServiceImpl extends BaseServiceImpl<SubAccountEntity, Long> implements ISubAccountService {
	
	@Autowired
	private ISubAccountDao subAccountDao;

	@Override
	public BaseDao getBaseMapper() {
		return subAccountDao;
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
	public ApiResponseResult addSubAccount(SubAccountAddVo vo) throws Exception {
		// 返回的对象
		ApiResponseResult result = new ApiResponseResult();
		SubAccountEntity entity = new SubAccountEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setStatus(1);
		entity.setCreateUser(RequestUtils.getUserId());
		entity.setCreateDate(new Date());
		// 新增
		int no = subAccountDao.insert(entity);
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
	public ApiResponseResult updateSubAccount(SubAccountUpdateVo vo) throws Exception {
		SubAccountEntity entity = new SubAccountEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setModifyUser(RequestUtils.getUserId());
		entity.setModifyDate(new Date());
		// 更新
		int no = subAccountDao.updateById(entity);
		if (no > 0) {
			return createSuccessResult(null);
		}
		return createFailResult();
	}
}
