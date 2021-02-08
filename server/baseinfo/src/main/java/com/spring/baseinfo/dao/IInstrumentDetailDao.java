package com.spring.baseinfo.dao;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.baseinfo.InstrumentDetailEntity;
import org.apache.ibatis.annotations.Mapper;

 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-03 16:03:23
 * @Desc类说明: 仪明细Dao
 */
@Mapper
public interface IInstrumentDetailDao extends BaseDao<InstrumentDetailEntity> {
	
}
