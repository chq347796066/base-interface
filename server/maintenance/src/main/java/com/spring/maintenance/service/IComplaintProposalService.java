package com.spring.maintenance.service;

import com.spring.base.entity.buiness.ComplaintProposalEntity;
import com.spring.base.service.IBaseService;
import com.spring.base.vo.buiness.complaintproposal.ComplaintProposalAddVo;
import com.spring.base.vo.buiness.complaintproposal.ComplaintProposalUpdateVo;
import com.spring.common.response.ApiResponseResult;

import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-11 15:49:31
 * @Desc类说明: 业主投诉建议业务接口类
 */
public interface IComplaintProposalService extends IBaseService<ComplaintProposalEntity,String>{
	
	/**
	 * 新增业主投诉建议
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-04-11 15:49:31
	 */
    ApiResponseResult addComplaintProposal(ComplaintProposalAddVo vo) throws Exception;
	
	/**
	 * 更业主投诉建议
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-04-11 15:49:31
	 */
    ApiResponseResult updateComplaintProposal(ComplaintProposalUpdateVo vo) throws Exception;

	/**
	 *根据条件查询列表
	 * @param vo
	 * @return
	 * @throws Exception
	 */
    ApiResponseResult queryListComplaintProposal(ComplaintProposalEntity vo) throws Exception;

	/**
	 * 查询对象详情
	 * @param vo
	 * @return
	 * @throws Exception
	 */
    ApiResponseResult queryComplaintProposal(ComplaintProposalEntity vo) throws Exception;


	/**
	 * @Desc:物业管理系统-物业服务-投诉建议导出调用列表
	 * @param requestPageVO
	 * @Author:邓磊
	 * @UpdateDate:2020/5/15 17:07
	 * @return: 返回
	 */
	 List<ComplaintProposalEntity> queryProposalList(ComplaintProposalEntity requestPageVO) throws Exception;

}
