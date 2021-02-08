package com.spring.pay.service.impl;

import com.spring.base.entity.pay.DisttransJournalsEntity;
import com.spring.base.vo.pay.disttransjournals.DisttransJournalsAddVo;
import com.spring.base.vo.pay.disttransjournals.DisttransJournalsUpdateVo;
import com.spring.common.request.RequestUtils;
import com.spring.pay.dao.IDisttransJournalsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.base.dao.BaseDao;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.pay.service.IDisttransJournalsService;
import org.springframework.beans.BeanUtils;
import com.spring.common.constants.MessageCode;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.id.UUIDFactory;
import lombok.extern.slf4j.Slf4j;
import java.util.Date;

 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-23 09:49:14
 * @Desc类说明: 优惠流水记录业务接口实现类
 */

 @Slf4j
@Service("disttransJournalsService")
public class DisttransJournalsServiceImpl extends BaseServiceImpl<DisttransJournalsEntity, String> implements IDisttransJournalsService {
	
	@Autowired
	private IDisttransJournalsDao disttransJournalsDao;

	@Override
	public BaseDao getBaseMapper() {
		return disttransJournalsDao;
	}
	
	/**
	 * 新增优惠流水记录
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua
	 * @version 创建时间：2020-04-23 09:49:14
	 */
	@Override
	public ApiResponseResult addDisttransJournals(DisttransJournalsAddVo vo) throws Exception {
		// 返回的对象
		ApiResponseResult result = new ApiResponseResult();
		DisttransJournalsEntity entity = new DisttransJournalsEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setId(UUIDFactory.createId());
		entity.setStatus(1);
		entity.setCreateUser(RequestUtils.getUserId());
		entity.setCreateDate(new Date());
		// 新增
		int no = disttransJournalsDao.insert(entity);
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
	 * 更新优惠流水记录
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-04-23 09:49:14
	 */
	@Override
	public ApiResponseResult updateDisttransJournals(DisttransJournalsUpdateVo vo) throws Exception {
		DisttransJournalsEntity entity = new DisttransJournalsEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setModifyUser(RequestUtils.getUserId());
		entity.setModifyDate(new Date());
		// 更新
		int no = disttransJournalsDao.updateById(entity);
		if (no > 0) {
			return createSuccessResult(null);
		}
		return createFailResult();
	}
}
