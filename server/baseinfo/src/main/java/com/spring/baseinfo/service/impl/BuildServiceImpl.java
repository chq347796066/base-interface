package com.spring.baseinfo.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.base.dao.BaseDao;
import com.spring.base.entity.baseinfo.BuildEntity;
import com.spring.base.entity.baseinfo.CellEntity;
import com.spring.base.entity.baseinfo.CommunityEntity;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.base.vo.baseinfo.build.BuildAddVo;
import com.spring.base.vo.baseinfo.build.BuildUpdateVo;
import com.spring.baseinfo.dao.IBuildDao;
import com.spring.baseinfo.dao.ICellDao;
import com.spring.baseinfo.dao.ICommunityDao;
import com.spring.baseinfo.service.IBuildService;
import com.spring.baseinfo.service.ICommunityService;
import com.spring.baseinfo.service.ICurrentUserInfoService;
import com.spring.common.constants.Constants;
import com.spring.common.constants.MessageCode;
import com.spring.common.page.RequestPageVO;
import com.spring.common.request.RequestUtils;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.id.UUIDFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-31 19:02:26
 * @Desc类说明:  楼栋业务接口实现类
 */
@Slf4j
@Service("buildService")
public class BuildServiceImpl extends BaseServiceImpl<BuildEntity, String> implements IBuildService {
	@Autowired
	private ICommunityDao communityDao;

	@Autowired
	private IBuildDao buildDao;

	@Autowired
	private ICellDao cellDao;

	@Autowired
	ICommunityService iCommunityService;

	@Autowired
	ICurrentUserInfoService currentUserInfoService;

	 @Override
	public BaseDao getBaseMapper() {
		return buildDao;
	}
	/**
	 * 新增 楼栋
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua
	 * @version 创建时间：2020-03-31 19:02:26
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ApiResponseResult addBuild(BuildAddVo vo) {
		// 返回的对象
		ApiResponseResult result = new ApiResponseResult();
		BuildEntity entity = new BuildEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setId(UUIDFactory.createId());
		entity.setStatus(Constants.Status.ENABLE);
		//校验楼栋名称唯一性
		BuildEntity buildVo = new  BuildEntity();
		buildVo.setBuildName(entity.getBuildName());
		buildVo.setCommunityId(entity.getCommunityId());
		List<BuildEntity> buildEntityList = buildDao.queryBuildName(buildVo);
		if(buildEntityList.size()>0){
			result.setCode(MessageCode.FAIL);
			result.setMsg("你填写的小区下已存在当前楼栋，请核实后请重新输入！");
			return result;
		}
		//赋值租户id,公司id
		CommunityEntity communityEntity=communityDao.selectById(vo.getCommunityId());
		if(communityEntity!=null)
		{
			entity.setCompanyId(communityEntity.getCompanyId());
		}
		// 新增
		int no = buildDao.insert(entity);
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
	 * 更新 楼栋
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-03-31 19:02:26
	 */
	@Override
	public ApiResponseResult updateBuild(BuildUpdateVo vo) {
		ApiResponseResult result = new ApiResponseResult();
		BuildEntity entity = new BuildEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setModifyUser(RequestUtils.getUserId());
		entity.setModifyDate(new Date());
		//校验楼栋名称唯一性
		BuildEntity buildVo = new  BuildEntity();
		buildVo.setBuildName(entity.getBuildName());
		buildVo.setCommunityId(entity.getCommunityId());
		buildVo.setId(entity.getId());
		List<BuildEntity> buildEntityList = buildDao.queryBuildName(buildVo);
		if(buildEntityList.size()>0){
			result.setCode(MessageCode.FAIL);
			result.setMsg("当前小区下已存在当前楼栋，请核实后请重新输入！");
			return result;
		}
		//赋值租户id,公司id
		CommunityEntity communityEntity=communityDao.selectById(vo.getCommunityId());
		if(communityEntity!=null)
		{
			entity.setTenantId(communityEntity.getTenantId());
			entity.setCompanyId(communityEntity.getCompanyId());
		}
		// 更新
		int no = buildDao.updateById(entity);
		if (no > 0) {
			return createSuccessResult(null);
		}
		return createFailResult();
	}

	/**
	 * 关联删除
	 * @param id
	 * @return
	 */
	 @Override
	 public ApiResponseResult deleteCell(String id) throws Exception {
		 ApiResponseResult result = new ApiResponseResult();
		 if(StringUtils.isNotEmpty(id)) {
			 CellEntity cellEntity = new CellEntity();
			 cellEntity.setBuildId(id);
			 List<CellEntity> cellEntities = cellDao.queryList(cellEntity);
			 if (CollectionUtils.isNotEmpty(cellEntities)) {
				 result.setCode(MessageCode.FAIL);
				 result.setMsg("楼栋已使用，不能进行删除");
				 result.setData(null);
			 }else{
				 BuildEntity buildEntity = new BuildEntity();
				 buildEntity.setId(id);
				 buildEntity.setTenantId(RequestUtils.getTenantId());
				 buildDao.deleteBuild(buildEntity);
				 result.setCode(MessageCode.SUCCESS);
				 result.setMsg(MessageCode.SUCCESS_TEXT);
				 result.setData(1);
			 }
		 }
		 return result;
	 }

	/**
	 * 根据条件分页查询楼栋
	 * @param requestPageVO
	 * @return
	 * @throws Exception
	 */
	@Override
	public ApiResponseResult queryBuildPage(RequestPageVO<BuildEntity> requestPageVO) {
		//获取当前登录者所拥有的小区信息
		List<String> communityIds = currentUserInfoService.queryCurrentUserCommunityInfo();

		PageHelper.startPage(requestPageVO.getCurrentPage(), requestPageVO.getPageSize(), true);
		requestPageVO.getEntity().setCommunityIds(communityIds);
		List<BuildEntity> list =buildDao.queryBuildList(requestPageVO.getEntity());
		PageInfo<BuildEntity> pageInfo = new PageInfo<BuildEntity>(list);
		return createSuccessResult(pageInfo);
	}

	@Override
	public BuildEntity queryBuildInfo(BuildEntity vo) {
		return buildDao.queryBuildInfo(vo);
	}
}
