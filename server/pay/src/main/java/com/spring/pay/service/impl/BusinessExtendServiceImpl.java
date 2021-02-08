package com.spring.pay.service.impl;

import com.spring.base.entity.pay.BusinessExtendEntity;
import com.spring.base.vo.pay.businessextend.BusinessExtendAddVo;
import com.spring.base.vo.pay.businessextend.BusinessExtendUpdateVo;
import com.spring.common.request.RequestUtils;
import com.spring.pay.dao.IBusinessExtendDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.base.dao.BaseDao;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.pay.service.IBusinessExtendService;
import org.springframework.beans.BeanUtils;
import com.spring.common.constants.MessageCode;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.id.UUIDFactory;
import lombok.extern.slf4j.Slf4j;
import java.util.Date;

 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-23 09:49:14
 * @Desc类说明: 业务流水扩展业务接口实现类
 */

 @Slf4j
@Service("businessExtendService")
public class BusinessExtendServiceImpl extends BaseServiceImpl<BusinessExtendEntity, String> implements IBusinessExtendService {
	
	@Autowired
	private IBusinessExtendDao businessExtendDao;

	@Override
	public BaseDao getBaseMapper() {
		return businessExtendDao;
	}
	
	/**
	 * 新增业务流水扩展
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua
	 * @version 创建时间：2020-04-23 09:49:14
	 */
	@Override
	public ApiResponseResult addBusinessExtend(BusinessExtendAddVo vo) throws Exception {
		// 返回的对象
		ApiResponseResult result = new ApiResponseResult();
		BusinessExtendEntity entity = new BusinessExtendEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setId(UUIDFactory.createId());
		entity.setStatus(1);
		entity.setCreateUser(RequestUtils.getUserId());
		entity.setCreateDate(new Date());
		// 新增
		int no = businessExtendDao.insert(entity);
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
	 * 更新业务流水扩展
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-04-23 09:49:14
	 */
	@Override
	public ApiResponseResult updateBusinessExtend(BusinessExtendUpdateVo vo) throws Exception {
		BusinessExtendEntity entity = new BusinessExtendEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setModifyUser(RequestUtils.getUserId());
		entity.setModifyDate(new Date());
		// 更新
		int no = businessExtendDao.updateById(entity);
		if (no > 0) {
			return createSuccessResult(null);
		}
		return createFailResult();
	}
}
