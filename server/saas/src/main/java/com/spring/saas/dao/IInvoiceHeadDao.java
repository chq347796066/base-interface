package com.spring.saas.dao;


import com.spring.base.dao.BaseDao;
import com.spring.base.entity.saas.InvoiceHeadEntity;
import org.apache.ibatis.annotations.Mapper;

 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-09 10:48:56
 * @Desc类说明: 发票抬头Dao
 */
@Mapper
public interface IInvoiceHeadDao extends BaseDao<InvoiceHeadEntity> {

   /**
    * 将租户的其他发票头设为非默认
    * @author 熊锋
    * @param entity
    * @date 2020/7/21 17:18
    * @return int
    * @throws Exception
    */
   int deEnableInvoiceHead(InvoiceHeadEntity entity);
}
