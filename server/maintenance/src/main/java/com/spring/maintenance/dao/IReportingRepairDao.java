package com.spring.maintenance.dao;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.buiness.ReportingRepairEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-11 16:03:31
 * @Desc类说明: 业主报事报修Dao
 */
@Mapper
public interface IReportingRepairDao extends BaseDao<ReportingRepairEntity> {

  /**
   * ID查询对象
   * @param reportingRepairEntity
   * @return
   */
  ReportingRepairEntity getReportingRepair(ReportingRepairEntity reportingRepairEntity);

  /**
   * @Desc: 报事报修新增
   * @param reportingRepairEntity
   * @Author:邓磊
   * @UpdateDate:2020/4/22 10:02
   * @return: 返回
   */
    Integer addReportingRepair(ReportingRepairEntity reportingRepairEntity);

    /**
     * @Desc:  报事报修修改
     * @param reportingRepairEntity
     * @Author:邓磊
     * @UpdateDate:2020/4/22 14:11
     */
    Integer updateReportingRepair(ReportingRepairEntity reportingRepairEntity);

    /**
     * @Desc: reportingRepairEntity
     * @param reportingRepairEntity
     * @Author:邓磊
     * @UpdateDate:2020/4/29 14:42
     */
   List<ReportingRepairEntity> queryReportingRepairList(ReportingRepairEntity reportingRepairEntity);

}
