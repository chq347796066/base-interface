package com.spring.saas.service.impl;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.saas.IndustryEntity;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.base.vo.saas.IndustryAddVo;
import com.spring.base.vo.saas.IndustryUpdateVo;
import com.spring.common.constants.MessageCode;
import com.spring.common.request.RequestUtils;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.id.UUIDFactory;
import com.spring.saas.dao.IIndustryDao;
import com.spring.saas.service.IIndustryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-02 15:17:11
 * @Desc类说明: 行业业务接口实现类
 */

 @Slf4j
@Service("industryService")
public class IndustryServiceImpl extends BaseServiceImpl<IndustryEntity, String> implements IIndustryService {
	
	@Autowired
	private IIndustryDao industryDao;

	@Override
	public BaseDao getBaseMapper() {
		return industryDao;
	}
	
	/**
	 * 新增行业
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua
	 * @version 创建时间：2020-07-02 15:17:11
	 */
	@Override
	public ApiResponseResult addIndustry(IndustryAddVo vo) throws Exception {
		// 返回的对象
		ApiResponseResult result = new ApiResponseResult();
		IndustryEntity entity = new IndustryEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setId(UUIDFactory.createId());
		entity.setCreateUser(RequestUtils.getUserId());
		entity.setCreateDate(new Date());
		// 新增
		int no = industryDao.insert(entity);
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
	 * 更新行业
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-07-02 15:17:11
	 */
	@Override
	public ApiResponseResult updateIndustry(IndustryUpdateVo vo) throws Exception {
		IndustryEntity entity = new IndustryEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setModifyUser(RequestUtils.getUserId());
		entity.setModifyDate(new Date());
		// 更新
		int no = industryDao.updateById(entity);
		if (no > 0) {
			return createSuccessResult(null);
		}
		return createFailResult();
	}
}
