package com.spring.baseinfo.dao;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.baseinfo.BuildEntity;
import com.spring.base.entity.baseinfo.CellEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-31 19:02:26
 * @Desc类说明: 单元信息Dao
 */
@Mapper
public interface ICellDao extends BaseDao<CellEntity> {
 /**
  * @Desc:  删除单元
  * @param cellEntity
  * @Author:邓磊
  * @UpdateDate:2020/4/18 13:59
  * @return: int
  */
  int deleteCell(CellEntity cellEntity);


  /**
   * @Desc: 查询单元
   * @param cellEntity
   * @Author:邓磊
   * @UpdateDate:2020/4/27 17:37
   * @return: 返回
   */
  List<CellEntity> queryCellVoList(CellEntity cellEntity);

 /**
  * @Desc:单元批量新增
  * @param list
  * @Author:邓磊
  * @UpdateDate:2020/5/18 17:06
  * @return: 返回
  */
 @Override
 Integer addList(@Param("list") List<CellEntity> list);

 /**
  * 校验单元名称唯一性
  */
 List<CellEntity>  queryCellName(CellEntity cellEntity);

 /**
  * 查询单元列表
  */
 List<CellEntity> queryCellList(CellEntity cellEntity);
}
