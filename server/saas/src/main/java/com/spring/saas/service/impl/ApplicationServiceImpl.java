package com.spring.saas.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.base.dao.BaseDao;
import com.spring.base.entity.baseinfo.RoleEntity;
import com.spring.base.entity.saas.ApplicationEntity;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.base.vo.baseinfo.role.SaasRoleVo;
import com.spring.base.vo.saas.ApplicationAddVo;
import com.spring.base.vo.saas.ApplicationUpdateVo;
import com.spring.common.constants.MessageCode;
import com.spring.common.feign.client.BaseInfoFeignClient;
import com.spring.common.page.RequestPageVO;
import com.spring.common.request.RequestUtils;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.id.UUIDFactory;
import com.spring.saas.dao.IApplicationDao;
import com.spring.saas.service.IApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-01 14:52:37
 * @Desc类说明: 应用业务接口实现类
 */

 @Slf4j
@Service("applicationService")
public class ApplicationServiceImpl extends BaseServiceImpl<ApplicationEntity, String> implements IApplicationService {
	 @Autowired
	 private BaseInfoFeignClient roleFeignCilnet;

	@Autowired
	private IApplicationDao applicationDao;

	@Override
	public BaseDao getBaseMapper() {
		return applicationDao;
	}
	
	/**
	 * 新增应用
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua
	 * @version 创建时间：2020-07-01 14:52:37
	 */
	@Override
	public ApiResponseResult addApplication(ApplicationAddVo vo) throws Exception {
		// 返回的对象
		ApiResponseResult result = new ApiResponseResult();

		if(vo.getAppType()!=null)
		{
			//判断试用版应用是否只有一个
			if(vo.getAppType()==1) {
				QueryWrapper<ApplicationEntity> queryWrapper = new QueryWrapper<>();
				queryWrapper.eq("app_type", 1);
				// 根据用户id和密码查询是否正确
				List<ApplicationEntity> appList = applicationDao.selectList(queryWrapper);
				if (CollectionUtils.isNotEmpty(appList)) {
					result.setCode(MessageCode.FAIL);
					result.setMsg("试用版应用只能一个！");
					return result;
				}
			}
			//判断主应用是否只有一个
			if(vo.getAppType()==2) {
				QueryWrapper<ApplicationEntity> queryWrapper = new QueryWrapper<>();
				queryWrapper.eq("app_type", 2);
				// 根据用户id和密码查询是否正确
				List<ApplicationEntity> appList = applicationDao.selectList(queryWrapper);
				if (CollectionUtils.isNotEmpty(appList)) {
					result.setCode(MessageCode.FAIL);
					result.setMsg("主应用只能一个！");
					return result;
				}
			}
		}
		ApplicationEntity entity = new ApplicationEntity();
		BeanUtils.copyProperties(vo, entity);
		String baseRoleId=UUIDFactory.createId();
		entity.setId(baseRoleId);
		entity.setCreateUser(RequestUtils.getUserId());
		entity.setCreateDate(new Date());
		entity.setAppCode("PIC"+entity.getId());
		// 新增
		int no = applicationDao.insert(entity);
		if (no > 0) {
			//调用base微服务增加角色和权限
			SaasRoleVo roleVo=new SaasRoleVo();
			roleVo.setRoleId(baseRoleId);
			roleVo.setRoleName(vo.getAppName());
			roleVo.setRoleDesc(vo.getDescription());
			roleVo.setAppType(vo.getAppType());
			roleVo.setMenuIdList(vo.getMenuIdList());
			roleFeignCilnet.addSaasRole(roleVo);

			result.setCode(MessageCode.SUCCESS);
			result.setMsg("成功");
		} else {
			result.setCode(MessageCode.FAIL);
			result.setMsg("新增失败");
		}
		return result;
	}

	 @Override
	 public ApiResponseResult deleteApp(String id) throws Exception {
		 // 删除
		 ApplicationEntity applicationEntity=new ApplicationEntity();
		 applicationEntity.setDelFlag(1);
		 applicationEntity.setId(id);
		 int no = applicationDao.updateById(applicationEntity);
		 if (no > 0) {
		 	 //删除base微服务的角色和角色下的权限
			 ApplicationEntity entity=applicationDao.selectById(id);
			 if(entity!=null) {
				 roleFeignCilnet.delete(entity.getId());
			 }
			 return createSuccessResult(null);
		 }
		 return createFailResult();
	 }

	 /**
	  * 更新应用
	  * @param vo
	  * @return
	  * @throws Exception
	  * @author 作者：ZhaoJinHua
	  * @version 创建时间：2020-07-01 14:52:37
	  */
	 @Override
	 public ApiResponseResult updateApplication(ApplicationUpdateVo vo) throws Exception {
		 ApplicationEntity entity = new ApplicationEntity();
		 BeanUtils.copyProperties(vo, entity);
		 entity.setModifyUser(RequestUtils.getUserId());
		 entity.setModifyDate(new Date());
		 // 更新
		 int no = applicationDao.updateById(entity);
		 if (no > 0) {
			 return createSuccessResult(null);
		 }
		 return createFailResult();
	 }

	 /**
	  * 查询可订购应用
	  * @param requestPageVO
	  * @return
	  * @throws Exception
	  * @author 作者：ZhaoJinHua
	  * @version 创建时间：2020-07-01 14:52:37
	  */
	 @Override
	 public ApiResponseResult queryAppList(RequestPageVO<ApplicationEntity> requestPageVO) throws Exception {
		 // 设置分页参数
		 PageHelper.startPage(requestPageVO.getCurrentPage(), requestPageVO.getPageSize(), true);
		 QueryWrapper<ApplicationEntity> queryWrapper=new QueryWrapper<>();
		 queryWrapper.eq("del_flag",0)
		              .in("app_type",0,2);
		 List<ApplicationEntity> list=applicationDao.selectList(queryWrapper);
		 if (CollectionUtils.isEmpty(list)){
			return createSuccessResult(null);
		 }
		 PageInfo<ApplicationEntity> pageInfo = new PageInfo<>(list);
		 return createSuccessResult(pageInfo);
	 }

 }
