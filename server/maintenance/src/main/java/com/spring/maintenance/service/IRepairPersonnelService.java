package com.spring.maintenance.service;

import com.spring.base.entity.buiness.RepairPersonnelEntity;
import com.spring.base.vo.buiness.repairpersonnel.RepairPersonnelAddVo;
import com.spring.base.vo.buiness.repairpersonnel.RepairPersonnelUpdateVo;
import com.spring.base.service.IBaseService;
import com.spring.common.response.ApiResponseResult;

import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-11 16:09:13
 * @Desc类说明: 报修派工人员业务接口类
 */
public interface IRepairPersonnelService extends IBaseService<RepairPersonnelEntity,String>{
	
	/**
	 * 新增报修派工人员
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-04-11 16:09:13
	 */
    ApiResponseResult addRepairPersonnel(RepairPersonnelAddVo vo) throws Exception;
	
	/**
	 * 更报修派工人员
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-04-11 16:09:13
	 */
    ApiResponseResult updateRepairPersonnel(RepairPersonnelUpdateVo vo) throws Exception;

	/**
	 * 查询
	 * @param repairPersonnelEntity
	 * @return
	 */
	List<RepairPersonnelEntity> queryRepairPersonnelEntityList(RepairPersonnelEntity  repairPersonnelEntity);
}
