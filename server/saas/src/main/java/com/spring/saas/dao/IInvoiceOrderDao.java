package com.spring.saas.dao;


import com.spring.base.dao.BaseDao;
import com.spring.base.entity.saas.InvoiceOrderEntity;
import org.apache.ibatis.annotations.Mapper;


/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-10 09:43:02
 * @Desc类说明: 发票订单关系Dao
 */
@Mapper
public interface IInvoiceOrderDao extends BaseDao<InvoiceOrderEntity> {

}
