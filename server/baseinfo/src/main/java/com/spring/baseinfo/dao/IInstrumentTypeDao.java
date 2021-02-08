package com.spring.baseinfo.dao;

import com.spring.base.entity.baseinfo.InstrumentTypeEntity;
import com.spring.base.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-02 17:17:10
 * @Desc类说明: 仪类型Dao
 */
@Mapper
public interface IInstrumentTypeDao extends BaseDao<InstrumentTypeEntity> {

 /**
  * @Desc:物理删除
  * @param id
  * @Author:邓磊
  * @UpdateDate:2020/6/2 13:48
  * @return: 返回
  */
   int modifyDelFlag(@Param("id") Long id);

   /**
    * @Desc:校验仪表类型唯一性
    * @param instrumentTypeEntity
    * @Author:邓磊
    * @UpdateDate:2020/6/3 15:10
    * @return: 返回
    */
   List<InstrumentTypeEntity> queryInstrumentType(InstrumentTypeEntity instrumentTypeEntity);
}
