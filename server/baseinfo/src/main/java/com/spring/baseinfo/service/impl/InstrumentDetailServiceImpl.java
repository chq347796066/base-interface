package com.spring.baseinfo.service.impl;

import com.spring.base.entity.baseinfo.InstrumentDetailEntity;
import com.spring.base.vo.baseinfo.instrumentdetail.InstrumentDetailAddVo;
import com.spring.base.vo.baseinfo.instrumentdetail.InstrumentDetailUpdateVo;
import com.spring.common.request.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.base.dao.BaseDao;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.baseinfo.dao.IInstrumentDetailDao;
import com.spring.baseinfo.service.IInstrumentDetailService;
import org.springframework.beans.BeanUtils;
import com.spring.common.constants.MessageCode;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.id.UUIDFactory;

import java.util.Date;

 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-03 16:03:23
 * @Desc类说明: 仪明细业务接口实现类
 */

@Service("instrumentDetailService")
public class InstrumentDetailServiceImpl extends BaseServiceImpl<InstrumentDetailEntity, String> implements IInstrumentDetailService {
	
	@Autowired
	private IInstrumentDetailDao instrumentDetailDao;

	@Override
	public BaseDao getBaseMapper() {
		return instrumentDetailDao;
	}
	
	/**
	 * 新增仪明细
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua
	 * @version 创建时间：2020-04-03 16:03:23
	 */
	@Override
	public ApiResponseResult addInstrumentDetail(InstrumentDetailAddVo vo) throws Exception {
		// 返回的对象
		ApiResponseResult result = new ApiResponseResult();
		InstrumentDetailEntity entity = new InstrumentDetailEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setId(UUIDFactory.createId());
		entity.setCreateUser(RequestUtils.getUserId());
		entity.setCreateDate(new Date());
		// 新增
		int no = instrumentDetailDao.insert(entity);
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
	 * 更新仪明细
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-04-03 16:03:23
	 */
	@Override
	public ApiResponseResult updateInstrumentDetail(InstrumentDetailUpdateVo vo) throws Exception {
		InstrumentDetailEntity entity = new InstrumentDetailEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setModifyUser(RequestUtils.getUserId());
		entity.setModifyDate(new Date());
		// 更新
		int no = instrumentDetailDao.updateById(entity);
		if (no > 0) {
			return createSuccessResult(null);
		}
		return createFailResult();
	}
}
