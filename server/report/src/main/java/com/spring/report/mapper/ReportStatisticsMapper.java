package com.spring.report.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring.base.entity.baseinfo.CompanyEntity;
import com.spring.base.entity.report.ArrearsReportEntity;
import com.spring.base.vo.baseinfo.company.CompanySearchVo;
import com.spring.base.vo.report.ArrearsReportInEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 报表统计
 * @author zwb
 * @date 2020-04-13 17:35:36
 */
@Mapper
public interface ReportStatisticsMapper extends BaseMapper<ArrearsReportEntity> {

    /**
     * 按照小区查询欠费
     * @return
     */
    List<ArrearsReportEntity> selectArrearReportByCIdList(ArrearsReportInEntity arrearsReportInEntity);

    /**
     * 按照公司查询欠费
     * @return
     */
    List<ArrearsReportEntity> selectArrearReportByPcIdList(@Param("arrearsReportInEntity") ArrearsReportInEntity arrearsReportInEntity);

    /**
     * 根据公司查子公司
     * @param
     * @return
     */
    CompanyEntity queryChlidTreeByParentId(@Param("parent") String companyId);

}
