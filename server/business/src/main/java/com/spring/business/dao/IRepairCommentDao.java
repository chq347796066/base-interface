package com.spring.business.dao;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.buiness.RepairCommentEntity;
import com.spring.base.entity.buiness.RepairImageEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2021-01-05 14:57:47
 * @Desc类说明: 报修评价论Dao
 */
@Mapper
public interface IRepairCommentDao extends BaseDao<RepairCommentEntity> {

  /**
   * @Author 熊锋
   * @param list
   * @Description批量新增图片
   * @Date 2021/1/6 17:00
   */
  @Override
  Integer addList(List<RepairCommentEntity> list) throws Exception;
	
}
