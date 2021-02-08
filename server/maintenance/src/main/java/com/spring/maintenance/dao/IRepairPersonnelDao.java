package com.spring.maintenance.dao;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.buiness.RepairPersonnelEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-11 16:09:13
 * @Desc类说明: 报修派工人员Dao
 */
@Mapper
public interface IRepairPersonnelDao extends BaseDao<RepairPersonnelEntity> {
    /**
     * 查询
     * @param repairPersonnelEntity
     * @return
     */
    List<RepairPersonnelEntity> queryRepairPersonnelEntityList(RepairPersonnelEntity  repairPersonnelEntity);

    /**
     * @Desc:    delete派修人员
     * @param repairPersonnelEntity
     * @Author:邓磊
     * @UpdateDate:2020/4/22 13:59
     * @return: 返回
     */
    Integer updateBrepairPersonnel(RepairPersonnelEntity repairPersonnelEntity);
}
