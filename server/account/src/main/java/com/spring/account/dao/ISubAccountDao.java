package com.spring.account.dao;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.account.SubAccountEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-11-07 17:18:11
 * @Desc类说明: Dao
 */
@Mapper
public interface ISubAccountDao extends BaseDao<SubAccountEntity> {

     void updateBatch(@Param("list") List<SubAccountEntity> list);
	
}
