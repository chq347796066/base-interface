package com.spring.pay.dao;

import com.spring.base.entity.pay.BusinessExtendEntity;
import com.spring.base.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-23 09:49:14
 * @Desc类说明: 业务流水扩展Dao
 */
@Mapper
public interface IBusinessExtendDao extends BaseDao<BusinessExtendEntity> {
	
}
