package com.spring.pay.service.impl;

import com.spring.base.entity.pay.TranspayJournalsEntity;
import com.spring.base.vo.pay.transpayjournals.TranspayJournalsAddVo;
import com.spring.base.vo.pay.transpayjournals.TranspayJournalsUpdateVo;
import com.spring.common.request.RequestUtils;
import com.spring.pay.dao.ITranspayJournalsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.base.dao.BaseDao;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.pay.service.ITranspayJournalsService;
import org.springframework.beans.BeanUtils;
import com.spring.common.constants.MessageCode;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.id.UUIDFactory;
import lombok.extern.slf4j.Slf4j;
import java.util.Date;

 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-23 09:49:14
 * @Desc类说明: 支付记录业务接口实现类
 */

 @Slf4j
@Service("transpayJournalsService")
public class TranspayJournalsServiceImpl extends BaseServiceImpl<TranspayJournalsEntity, String> implements ITranspayJournalsService {
	
	@Autowired
	private ITranspayJournalsDao transpayJournalsDao;

	@Override
	public BaseDao getBaseMapper() {
		return transpayJournalsDao;
	}
	
	/**
	 * 新增支付记录
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua
	 * @version 创建时间：2020-04-23 09:49:14
	 */
	@Override
	public ApiResponseResult addTranspayJournals(TranspayJournalsAddVo vo) throws Exception {
		// 返回的对象
		ApiResponseResult result = new ApiResponseResult();
		TranspayJournalsEntity entity = new TranspayJournalsEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setId(UUIDFactory.createId());
		entity.setStatus(1);
		entity.setCreateUser(RequestUtils.getUserId());
		entity.setCreateDate(new Date());
		// 新增
		int no = transpayJournalsDao.insert(entity);
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
	 * 更新支付记录
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-04-23 09:49:14
	 */
	@Override
	public ApiResponseResult updateTranspayJournals(TranspayJournalsUpdateVo vo) throws Exception {
		TranspayJournalsEntity entity = new TranspayJournalsEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setModifyUser(RequestUtils.getUserId());
		entity.setModifyDate(new Date());
		// 更新
		int no = transpayJournalsDao.updateById(entity);
		if (no > 0) {
			return createSuccessResult(null);
		}
		return createFailResult();
	}
}
