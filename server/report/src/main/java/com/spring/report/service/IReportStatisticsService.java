package com.spring.report.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.spring.base.entity.report.ArrearsReportEntity;
import com.spring.base.vo.report.ArrearsReportInEntity;
import com.spring.base.vo.report.ArrearsReportOutEntity;
import com.spring.common.page.RequestPageVO;


/**
 * zwb
 */
public interface IReportStatisticsService extends IService<ArrearsReportEntity> {

    /**
     * 按照小区查询欠费
     * @return
     */
    PageInfo<ArrearsReportEntity> selectArrearReportByCIdList(RequestPageVO<ArrearsReportInEntity> arrearsReportInEntity);

    /**
     * 按照公司查询欠费
     * @return
     */
    PageInfo<ArrearsReportOutEntity> selectArrearReportByPcIdList(RequestPageVO<ArrearsReportInEntity> arrearsReportInEntity);
}
