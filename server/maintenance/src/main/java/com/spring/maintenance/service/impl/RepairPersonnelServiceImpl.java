package com.spring.maintenance.service.impl;

import com.spring.base.entity.buiness.RepairPersonnelEntity;
import com.spring.base.vo.buiness.repairpersonnel.RepairPersonnelAddVo;
import com.spring.base.vo.buiness.repairpersonnel.RepairPersonnelUpdateVo;
import com.spring.common.request.RequestUtils;
import com.spring.maintenance.dao.IRepairPersonnelDao;
import com.spring.maintenance.service.IRepairPersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.base.dao.BaseDao;
import com.spring.base.service.impl.BaseServiceImpl;
import org.springframework.beans.BeanUtils;
import com.spring.common.constants.MessageCode;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.id.UUIDFactory;
import java.util.Date;
import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-11 16:09:13
 * @Desc类说明: 报修派工人员业务接口实现类
 */

@Service("repairPersonnelService")
public class RepairPersonnelServiceImpl extends BaseServiceImpl<RepairPersonnelEntity, String> implements IRepairPersonnelService {
	
	@Autowired
	private IRepairPersonnelDao repairPersonnelDao;

	@Override
	public BaseDao getBaseMapper() {
		return repairPersonnelDao;
	}
	
	/**
	 * 新增报修派工人员
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua
	 * @version 创建时间：2020-04-11 16:09:13
	 */
	@Override
	public ApiResponseResult addRepairPersonnel(RepairPersonnelAddVo vo) throws Exception {
		// 返回的对象
		ApiResponseResult result = new ApiResponseResult();
		RepairPersonnelEntity entity = new RepairPersonnelEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setId(UUIDFactory.createId());
		entity.setDelFlag(0);
		entity.setTenantId(RequestUtils.getTenantId());
		entity.setCreateUser(RequestUtils.getUserId());
		entity.setCreateDate(new Date());
		// 新增
		int no = repairPersonnelDao.insert(entity);
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
	 * 更新报修派工人员
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-04-11 16:09:13
	 */
	@Override
	public ApiResponseResult updateRepairPersonnel(RepairPersonnelUpdateVo vo) throws Exception {
		RepairPersonnelEntity entity = new RepairPersonnelEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setModifyUser(RequestUtils.getUserId());
		entity.setModifyDate(new Date());
		// 更新
		int no = repairPersonnelDao.updateById(entity);
		if (no > 0) {
			return createSuccessResult(null);
		}
		return createFailResult();
	}


	/**
	 * 查询集合
	 * @param repairPersonnelEntity
	 * @return
	 */
	@Override
	public List<RepairPersonnelEntity> queryRepairPersonnelEntityList(RepairPersonnelEntity repairPersonnelEntity) {
		return repairPersonnelDao.queryRepairPersonnelEntityList(repairPersonnelEntity);
	}

}
