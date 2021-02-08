package com.spring.saas.dao;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.saas.IndustryEntity;
import org.apache.ibatis.annotations.Mapper;

 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-02 15:17:11
 * @Desc类说明: 行业Dao
 */
@Mapper
public interface IIndustryDao extends BaseDao<IndustryEntity> {
	
}
