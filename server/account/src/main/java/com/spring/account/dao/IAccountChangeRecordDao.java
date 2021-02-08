package com.spring.account.dao;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.account.AccountChangeRecordEntity;
import org.apache.ibatis.annotations.Mapper;

 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-11-07 17:18:11
 * @Desc类说明: Dao
 */
@Mapper
public interface IAccountChangeRecordDao extends BaseDao<AccountChangeRecordEntity> {
	
}
