package com.spring.business.service;

import com.spring.base.entity.buiness.RepairCommentEntity;
import com.spring.base.service.IBaseService;
import com.spring.business.vo.RepairCommentAddVo;
import com.spring.common.response.ApiResponseResult;

 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2021-01-05 14:57:47
 * @Desc类说明: 报修评价论业务接口类
 */
public interface IRepairCommentService extends IBaseService<RepairCommentEntity,Long>{
	
	/**
	 * 新增报修评价论
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2021-01-05 14:57:47
	 */
	 ApiResponseResult ownerComments(RepairCommentAddVo vo) throws Exception;
}
