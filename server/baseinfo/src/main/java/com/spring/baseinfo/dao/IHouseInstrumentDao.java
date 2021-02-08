package com.spring.baseinfo.dao;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.baseinfo.HouseInstrumentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-03 09:35:57
 * @Desc类说明: 房屋仪管理Dao
 */
@Mapper
public interface IHouseInstrumentDao extends BaseDao<HouseInstrumentEntity> {
   /**
    * @Desc:物理删除
    * @param houseInstrumentEntity
    * @Author:邓磊
    * @UpdateDate:2020/6/2 15:10
    * @return: 返回
    */
   int modifyDelFlag(HouseInstrumentEntity houseInstrumentEntity);


   /**
    * @Desc:查看详情
    * @param houseInstrumentEntity
    * @Author:邓磊
    * @UpdateDate:2020/6/2 15:34
    * @return: 返回
    */
   HouseInstrumentEntity  queryHouseInstrumentInfo(HouseInstrumentEntity houseInstrumentEntity);


   /**
    * @Desc:房屋仪表批量插入
    * @param list
    * @Author:邓磊
    * @UpdateDate:2020/6/3 15:57
    * @return: 返回
    */
   @Override
   Integer addList(@Param("list") List<HouseInstrumentEntity> list);
 }
