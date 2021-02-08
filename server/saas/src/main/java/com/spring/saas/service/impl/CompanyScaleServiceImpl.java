package com.spring.saas.service.impl;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.saas.CompanyScaleEntity;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.base.vo.saas.CompanyScaleAddVo;
import com.spring.base.vo.saas.CompanyScaleUpdateVo;
import com.spring.common.constants.MessageCode;
import com.spring.common.request.RequestUtils;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.id.UUIDFactory;
import com.spring.saas.dao.ICompanyScaleDao;
import com.spring.saas.service.ICompanyScaleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-02 15:17:12
 * @Desc类说明: 公司规模业务接口实现类
 */

 @Slf4j
@Service("companyScaleService")
public class CompanyScaleServiceImpl extends BaseServiceImpl<CompanyScaleEntity, String> implements ICompanyScaleService {
	
	@Autowired
	private ICompanyScaleDao companyScaleDao;

	@Override
	public BaseDao getBaseMapper() {
		return companyScaleDao;
	}
	
	/**
	 * 新增公司规模
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua
	 * @version 创建时间：2020-07-02 15:17:12
	 */
	@Override
	public ApiResponseResult addCompanyScale(CompanyScaleAddVo vo) throws Exception {
		// 返回的对象
		ApiResponseResult result = new ApiResponseResult();
		CompanyScaleEntity entity = new CompanyScaleEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setId(UUIDFactory.createId());
		entity.setCreateUser(RequestUtils.getUserId());
		entity.setCreateDate(new Date());
		// 新增
		int no = companyScaleDao.insert(entity);
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
	 * 更新公司规模
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-07-02 15:17:12
	 */
	@Override
	public ApiResponseResult updateCompanyScale(CompanyScaleUpdateVo vo) throws Exception {
		CompanyScaleEntity entity = new CompanyScaleEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setModifyUser(RequestUtils.getUserId());
		entity.setModifyDate(new Date());
		// 更新
		int no = companyScaleDao.updateById(entity);
		if (no > 0) {
			return createSuccessResult(null);
		}
		return createFailResult();
	}
}
