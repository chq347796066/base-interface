package com.spring.maintenance.dao;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.buiness.ComplaintProposalEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-11 15:49:31
 * @Desc类说明: 业主投诉建议Dao
 */
@Mapper
public interface IComplaintProposalDao extends BaseDao<ComplaintProposalEntity> {
  /**
   * 新增投诉建议
   */
  int insertComplaintProposal(ComplaintProposalEntity complaintProposalEntity);

  /**
   * 修改投诉建议
   */
  int updateComplaintProposal(ComplaintProposalEntity complaintProposalEntity);

  /**
   * 查询列表
   */
  List<ComplaintProposalEntity> queryListComplaintProposal(ComplaintProposalEntity complaintProposalEntity);

  /**
   * 返回单个对象
   */
  ComplaintProposalEntity  queryComplaintProposal(ComplaintProposalEntity complaintProposalEntity);

}
