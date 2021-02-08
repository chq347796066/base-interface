package com.spring.pay.dao;

import com.spring.base.entity.pay.RevokeInfoEntity;
import com.spring.base.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-05-20 10:37:32
 * @Desc类说明: 撤销记录Dao
 */
@Mapper
public interface IRevokeInfoDao extends BaseDao<RevokeInfoEntity> {
	
}
