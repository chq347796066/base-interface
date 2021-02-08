package com.spring.pay.dao;

import com.spring.base.entity.pay.TranspayJournalsEntity;
import com.spring.base.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-23 09:49:14
 * @Desc类说明: 支付记录Dao
 */
@Mapper
public interface ITranspayJournalsDao extends BaseDao<TranspayJournalsEntity> {
	
}
