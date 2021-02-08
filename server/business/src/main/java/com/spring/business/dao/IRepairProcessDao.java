package com.spring.business.dao;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.buiness.RepairProcessEntity;
import org.apache.ibatis.annotations.Mapper;

 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2021-01-05 14:57:47
 * @Desc类说明: 报修流程Dao
 */
@Mapper
public interface IRepairProcessDao extends BaseDao<RepairProcessEntity> {
	
}
