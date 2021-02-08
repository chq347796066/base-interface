package com.spring.business.service;

import com.spring.base.entity.buiness.RepairEntity;
import com.spring.base.service.IBaseService;
import com.spring.business.vo.RepairAddVo;
import com.spring.business.vo.RepairCloseVo;
import com.spring.business.vo.RepairQueryVo;
import com.spring.business.vo.WebRepairQueryVo;
import com.spring.common.response.ApiResponseResult;

 /**
 * @author 作者：熊锋
 * @date : 创建时间：2021-01-05 14:57:47
 * @Desc类说明: 报事报修业务接口类
 */
public interface IRepairService extends IBaseService<RepairEntity,Long>{
	
	/**
	 * 新增报事报修
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：熊锋
	 * @version 创建时间：2021-01-05 14:57:47
	 */
	 ApiResponseResult createRepair(RepairAddVo vo) throws Exception;

	 /**
	  * 取消报事报修
	  * @param id
	  * @param remark
	  * @return
	  * @throws Exception
	  * @author 作者：熊锋
	  * @version 创建时间：2021-01-05 14:57:47
	  */
	 ApiResponseResult cancelRepair(Long id,String remark) throws Exception;

	 /**
	  * 查询报修列表
	  * @param repairQueryVo
	  * @return
	  * @throws Exception
	  * @author 作者：熊锋
	  * @version 创建时间：2021-01-05 14:57:47
	  */
	 ApiResponseResult queryRepairList(RepairQueryVo repairQueryVo) throws Exception;

	 /**
	  * 查询报修工单详情
	  * @param id
	  * @return
	  * @throws Exception
	  * @author 作者：熊锋
	  * @version 创建时间：2021-01-05 14:57:47
	  */
	 ApiResponseResult queryRepairDetail(String id) throws Exception;

	 /**
	  * 管家关闭工单
	  * @param repairCloseVo
	  * @return
	  * @throws Exception
	  * @author 作者：熊锋
	  * @version 创建时间：2021-01-05 14:57:47
	  */
	 ApiResponseResult closeRepair(RepairCloseVo repairCloseVo) throws Exception;


	 /**
	  * 查询报修人信息
	  * @param userId
	  * @return
	  * @throws Exception
	  * @author 作者：熊锋
	  * @version 创建时间：2021-01-05 14:57:47
	  */
	 ApiResponseResult queryRepairInfo(String userId) throws Exception;

	 /**
	  * web端查询工单列表
	  * @param webRepairQueryVo
	  * @return
	  * @throws Exception
	  * @author 作者：熊锋
	  * @version 创建时间：2021-01-05 14:57:47
	  * @return
	  */
	 ApiResponseResult queryWebRepairList(WebRepairQueryVo webRepairQueryVo) throws Exception;
}

