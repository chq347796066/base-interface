package com.spring.baseinfo.service;

import com.spring.base.entity.buiness.ComplaintProposalEntity;
import com.spring.base.service.IBaseService;
import com.spring.base.vo.buiness.complaintproposal.ComplaintProposalEntityVo;
import com.spring.base.vo.buiness.complaintproposal.ComplaintProposalUpdateVo;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-11 15:49:31
 * @Desc类说明: 业主投诉建议业务接口类
 */
public interface IComplaintProposaService extends IBaseService<ComplaintProposalEntityVo,String>{

	/**
	 * 根据条件分页查询
	 * @param requestPageVO
	 * @return
	 */
	 ApiResponseResult queryComplaintProposalPage(RequestPageVO<ComplaintProposalEntityVo> requestPageVO) throws Exception;

	/**
	 * 根据主键id查询对象
	 * @param vo
	 * @return
	 */
	 ApiResponseResult queryComplaintProposal(ComplaintProposalEntityVo vo) throws Exception;


	 /**
	  * @Desc:更新
	  * @param vo
	  * @Author:邓磊
	  * @UpdateDate:2020/4/23 9:46
	  * @return: 返回
	  */
	 ApiResponseResult update(ComplaintProposalUpdateVo vo) throws Exception;



	 /**
	  * @Desc:报事报修信息数据导出
	  * @param vo
	  * @Author:邓磊
	  * @UpdateDate:2020/5/15 17:22
	  * @return: 返回
	  */
	 void  exportComplaintProposalEntityInfo(ComplaintProposalEntity vo) throws Exception;

}
