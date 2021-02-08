package com.spring.maintenance.dao;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.buiness.MyHouseEntity;
import com.spring.base.vo.buiness.myhouse.MyHouseVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-17 09:46:08
 * @Desc类说明: 我的房屋信息Dao
 */
@Mapper
public interface IMyHouseDao extends BaseDao<MyHouseEntity> {
     /**
      * @Desc:   添加房屋
      * @param myHouseEntity
      * @Author:邓磊
      * @UpdateDate:2020/4/18 10:14
      * @return: int
      */

     int insertMyHouse(MyHouseEntity myHouseEntity);
  /**
   * @Desc:  删除房屋
   * @param myHouseEntity
   * @Author:邓磊
   * @UpdateDate:2020/4/18 10:14
   * @return: int
   */
    int deleteMyHouse(MyHouseEntity myHouseEntity);

    /**
     * @Desc:    我的租客
     * @param myHouseVo
     * @Author:邓磊
     * @UpdateDate:2020/4/22 15:02
     * @return: 返回
     */
    List<MyHouseEntity> queryRenterList(MyHouseVo myHouseVo);

    /**
     * @Desc:　系统管理用户管理　业主APP用户
     * @param myHouseEntity
     * @Author:邓磊
     * @UpdateDate:2020/5/7 16:25
     * @return: 返回
     */
    @Override
    List<MyHouseEntity> queryList(MyHouseEntity myHouseEntity);

    /**
     * @Desc:    房产详情
     * @param myHouseEntity
     * @Author:邓磊
     * @UpdateDate:2020/5/8 20:01
     * @return: 返回
     */
    MyHouseEntity queryMyHouseInfo(MyHouseEntity myHouseEntity);

}
