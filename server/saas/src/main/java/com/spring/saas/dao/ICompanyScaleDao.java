package com.spring.saas.dao;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.saas.CompanyScaleEntity;
import org.apache.ibatis.annotations.Mapper;

 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-02 15:17:12
 * @Desc类说明: 公司规模Dao
 */
@Mapper
public interface ICompanyScaleDao extends BaseDao<CompanyScaleEntity> {
	
}
