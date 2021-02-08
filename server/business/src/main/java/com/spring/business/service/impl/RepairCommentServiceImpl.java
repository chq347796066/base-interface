package com.spring.business.service.impl;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.buiness.RepairCommentEntity;
import com.spring.base.entity.buiness.RepairEntity;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.business.dao.IRepairCommentDao;
import com.spring.business.dao.IRepairDao;
import com.spring.business.service.IRepairCommentService;
import com.spring.business.vo.RepairCommentAddVo;
import com.spring.business.vo.RepairCommentVo;
import com.spring.common.constants.RepairConstants;
import com.spring.common.request.RequestUtils;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.SnowflakeIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2021-01-05 14:57:47
 * @Desc类说明: 报修评价论业务接口实现类
 */

 @Slf4j
@Service("repairCommentService")
public class RepairCommentServiceImpl extends BaseServiceImpl<RepairCommentEntity, Long> implements IRepairCommentService {
	
	@Autowired
	private IRepairCommentDao repairCommentDao;

	@Autowired
	private IRepairDao repairDao;

	@Override
	public BaseDao getBaseMapper() {
		return repairCommentDao;
	}
	
	/**
	 * 新增报修评价论
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua
	 * @version 创建时间：2021-01-05 14:57:47
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ApiResponseResult ownerComments(RepairCommentAddVo vo) throws Exception {
		// 返回的对象
		List<RepairCommentEntity> repairList=new ArrayList<>();
		RepairEntity repairEntity=new RepairEntity();
		List<RepairCommentVo> list=vo.getRepairInfoList();
		list.stream().forEach(comment->{
			RepairCommentEntity entity = new RepairCommentEntity();
			entity.setCommentStar(vo.getCommentStar());
			entity.setRepairId(vo.getRepairId());
			entity.setId(SnowflakeIdWorker.generateId());
			entity.setCommentId(comment.getCommentId());
			entity.setCommentName(comment.getCommentName());
			repairList.add(entity);
		});
		//往评论表插入数据
		int no = repairCommentDao.addList(repairList);
		//修改报修状态
		repairEntity.setRepairStatus(RepairConstants.COMMENTS);
		repairEntity.setId(vo.getRepairId());
		int flag=repairDao.updateById(repairEntity);
		if (no > 0 && flag>0) {
			return createSuccessResult(null);
		} else {
			return createFailResult("评论失败");
		}
	}
}
