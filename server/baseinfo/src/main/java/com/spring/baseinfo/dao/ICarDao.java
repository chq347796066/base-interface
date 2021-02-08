package com.spring.baseinfo.dao;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.spring.base.dao.BaseDao;
import com.spring.base.entity.baseinfo.CarEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-31 19:02:26
 * @Desc类说明:  车位信息Dao
 */
@Mapper
public interface ICarDao extends BaseDao<CarEntity> {

   /**
    * @Desc:   删除车位
    * @param carEntity
    * @Author:邓磊
    * @UpdateDate:2020/4/18 11:38
    * @return: 返回
    */
  int deleteCar(CarEntity carEntity);

  /**
   * @Desc:  小区ID加车位CODE查询车位对象
   * @param carEntity
   * @Author:邓磊
   * @UpdateDate:2020/4/21 13:52
   * @return: 返回
   */
  CarEntity queryCar(CarEntity carEntity);

  /**
   * @Desc:  批量插入
   * @param list
   * @Author:邓磊
   * @UpdateDate:2020/4/21 15:16
   * @return: 返回
   */
  @Override
  Integer addList(@Param("list") List<CarEntity> list);

}
