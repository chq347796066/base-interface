package com.spring.baseinfo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.base.dao.BaseDao;
import com.spring.base.entity.baseinfo.CellEntity;
import com.spring.base.entity.baseinfo.CommunityEntity;
import com.spring.base.entity.baseinfo.HouseEntity;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.base.vo.baseinfo.cell.CellAddVo;
import com.spring.base.vo.baseinfo.cell.CellUpdateVo;
import com.spring.baseinfo.dao.ICellDao;
import com.spring.baseinfo.dao.ICommunityDao;
import com.spring.baseinfo.dao.IHouseDao;
import com.spring.baseinfo.service.ICellService;
import com.spring.baseinfo.service.ICurrentUserInfoService;
import com.spring.common.constants.Constants;
import com.spring.common.constants.MessageCode;
import com.spring.common.page.RequestPageVO;
import com.spring.common.request.RequestUtils;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.id.UUIDFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-31 19:02:26
 * @Desc类说明: 单元信息业务接口实现类
 */
@Slf4j
@Service("cellService")
public class CellServiceImpl extends BaseServiceImpl<CellEntity, String> implements ICellService {
	@Autowired
	private ICommunityDao communityDao;

	@Autowired
	private ICellDao cellDao;

	 @Autowired
	 private IHouseDao houseDao;

	 @Autowired
	 private ICurrentUserInfoService currentUserInfoService;


	 @Override
	public BaseDao getBaseMapper() {
		return cellDao;
	}
	
	/**
	 * 新增单元信息
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua
	 * @version 创建时间：2020-03-31 19:02:26
	 */
	@Override
	public ApiResponseResult addCell(CellAddVo vo) {
		// 返回的对象
		ApiResponseResult result = new ApiResponseResult();
		CellEntity entity = new CellEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setId(UUIDFactory.createId());
		entity.setStatus(Constants.Status.ENABLE);
		entity.setCreateUser(RequestUtils.getUserId());
		entity.setCreateDate(new Date());
		//校验单元名称唯一性
		CellEntity cellEntity = new CellEntity();
		cellEntity.setCellName(entity.getCellName());
		cellEntity.setBuildId(entity.getBuildId());
		cellEntity.setCommunityId(entity.getCommunityId());
		List<CellEntity> cellEntityList = cellDao.queryCellName(cellEntity);
		if(cellEntityList.size()>0){
			result.setCode(MessageCode.FAIL);
			result.setMsg("你填写的楼栋下已存在当前单元，请核实后请重新输入！");
			return result;
		}
		//赋值租户id,公司id
		CommunityEntity communityEntity=communityDao.selectById(vo.getCommunityId());
		if(communityEntity!=null)
		{
//			entity.setTenantId(communityEntity.getTenantId());
			entity.setCompanyId(communityEntity.getCompanyId());
		}
		// 新增
		int no = cellDao.insert(entity);
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
	 * 更新单元信息
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-03-31 19:02:26
	 */
	@Override
	public ApiResponseResult updateCell(CellUpdateVo vo) {
		CellEntity entity = new CellEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setModifyUser(RequestUtils.getUserId());
		entity.setModifyDate(new Date());
		//赋值租户id,公司id
		CommunityEntity communityEntity=communityDao.selectById(vo.getCommunityId());
		if(communityEntity!=null)
		{
			entity.setTenantId(communityEntity.getTenantId());
			entity.setCompanyId(communityEntity.getCompanyId());
		}
		// 更新
		int no = cellDao.updateById(entity);
		if (no > 0) {
			return createSuccessResult(null);
		}
		return createFailResult();
	}

	 /**
	  * 关联删房屋
	  * @param id
	  * @return
	  * @throws Exception
	  */
	 @Override
	 public ApiResponseResult deleteHouse(String id) throws Exception {
		 ApiResponseResult result = new ApiResponseResult();
		 if(StringUtils.isNotEmpty(id)){
			 HouseEntity houseEntity = new HouseEntity();
			 houseEntity.setCellId(id);
			 List<HouseEntity> houseEntities = houseDao.queryList(houseEntity);
			 if(CollectionUtils.isNotEmpty(houseEntities)){
				 result.setCode(MessageCode.FAIL);
				 result.setMsg("单元已使用，不能进行删除");
				 result.setData(null);
			 }else{
				 CellEntity cellEntity = new CellEntity();
				 cellEntity.setId(id);
				 cellEntity.setTenantId(RequestUtils.getTenantId());
				 cellDao.deleteCell(cellEntity);
				 result.setCode(MessageCode.SUCCESS);
				 result.setMsg(MessageCode.SUCCESS_TEXT);
				 result.setData(1);
			 }
		 }
		 return result;
	 }

	/**
	 * 根据条件分页查询小区
	 * @param requestPageVO
	 * @return
	 * @throws Exception
	 */
	@Override
	public ApiResponseResult queryCellPage(RequestPageVO<CellEntity> requestPageVO) {
		//获取当前登录者所拥有的小区信息
		List<String> communityIds=currentUserInfoService.queryCurrentUserCommunityInfo();

		PageHelper.startPage(requestPageVO.getCurrentPage(), requestPageVO.getPageSize(), true);
		requestPageVO.getEntity().setCommunityIds(communityIds);
		List<CellEntity> cellList = cellDao.queryCellList(requestPageVO.getEntity());
		PageInfo<CellEntity> pageInfo = new PageInfo<CellEntity>(cellList);
		return createSuccessResult(pageInfo);
	}
}
