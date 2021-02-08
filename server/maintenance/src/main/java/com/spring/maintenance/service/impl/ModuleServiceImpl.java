package com.spring.maintenance.service.impl;

import com.spring.base.entity.pay.ModuleEntity;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.base.vo.pay.module.ModuleAddVo;
import com.spring.base.vo.pay.module.ModuleUpdateVo;
import com.spring.common.request.RequestUtils;
import com.spring.maintenance.dao.IModuleDao;
import com.spring.maintenance.service.IModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.base.dao.BaseDao;
import org.springframework.beans.BeanUtils;
import com.spring.common.constants.MessageCode;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.id.UUIDFactory;

import java.util.Date;

 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-18 17:32:18
 * @Desc类说明: 业务接口实现类
 */

@Service("moduleService")
public class ModuleServiceImpl extends BaseServiceImpl<ModuleEntity, String> implements IModuleService {
	
	@Autowired
	private IModuleDao moduleDao;

	@Override
	public BaseDao getBaseMapper() {
		return moduleDao;
	}
	
	/**
	 * 新增
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua
	 * @version 创建时间：2020-03-18 17:32:18
	 */
	@Override
	public ApiResponseResult addModule(ModuleAddVo vo) throws Exception {
		// 返回的对象
		ApiResponseResult result = new ApiResponseResult();
		ModuleEntity entity = new ModuleEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setId(UUIDFactory.createId());
		entity.setCreateUser(RequestUtils.getUserId());
		entity.setCreateDate(new Date());
		// 新增
		int no = moduleDao.insert(entity);
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
	 * @version 创建时间：2020-03-18 17:32:18
	 */
	@Override
	public ApiResponseResult updateModule(ModuleUpdateVo vo) throws Exception {
		ModuleEntity entity = new ModuleEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setModifyUser(RequestUtils.getUserId());
		entity.setModifyDate(new Date());
		// 更新
		int no = moduleDao.updateById(entity);
		if (no > 0) {
			return createSuccessResult(null);
		}
		return createFailResult();
	}

	 /**
	  * @description:查询组件列表
	  * @return:
	  * @author: 赵进华
	  * @time: 2020/3/23 16:07
	  */
	 @Override
	 public ApiResponseResult queryModuleList() throws Exception {
		 ModuleEntity entity=new ModuleEntity();
		 entity.setParentId("0");
		 return createSuccessResult(moduleDao.queryModuleList(entity));
	 }
 }
