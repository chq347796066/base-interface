package com.spring.baseinfo.dao;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.baseinfo.HouseEntity;
import com.spring.base.vo.baseinfo.house.HouseEntityVo;
import com.spring.base.vo.baseinfo.housingcertification.HouseDeleteParamVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-31 19:02:26
 * @Desc类说明: 房产信息Dao
 */
@Mapper
public interface IHouseDao extends BaseDao<HouseEntity> {

  /**
   * 导出
   * @param houseEntity
   * @return
   */
  List<HouseEntityVo> queryExcelList(HouseEntity houseEntity);

  /**
   * @Desc:  删除房屋
   * @param houseEntity
   * @Author:邓磊
   * @UpdateDate:2020/4/18 15:15
   * @return: int
   */
  int deleteHouse(HouseEntity houseEntity);

  /**
   * @Desc: 房产信息批量插入
   * @param list
   * @Author:邓磊
   * @UpdateDate:2020/4/27 17:07
   * @return: 返回
   */
  @Override
  Integer addList(@Param("list") List<HouseEntity> list);


  /**
   * @Desc:    详情
   * @param  entity
   * @Author:邓磊
   * @UpdateDate:2020/5/13 10:16
   * @return: 返回
   */
  HouseEntity queryHouseInfo(HouseEntity entity);

  /**
   * @Desc:    房屋审核
   * @param entity
   * @Author:邓磊
   * @UpdateDate:2020/5/13 10:17
   * @return: 返回
   */
  HouseEntity queryOwnerNameMobile(HouseEntity entity);


  /**
   * @Desc:校验房屋编号唯一性
   * @param entity
   * @Author:邓磊
   * @UpdateDate:c 18:15
   * @return: 返回
   */
  List<HouseEntity> queryHouseCodeList(HouseEntity entity);

  List<LinkedHashMap<String, String>> queryExportList(Map<String, Object> params);


  int updateHouserUserStatus(HouseDeleteParamVo houseDeleteParamVo);

  List<HouseEntity> queryHouseList(HouseEntity houseEntity);

}
