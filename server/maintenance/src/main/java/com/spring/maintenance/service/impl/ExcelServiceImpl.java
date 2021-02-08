package com.spring.maintenance.service.impl;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.baseinfo.ExcelExportEntity;
import com.spring.base.entity.buiness.ComplaintProposalEntity;
import com.spring.base.entity.buiness.ReportingRepairEntity;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.common.constants.MessageCode;
import com.spring.common.request.RequestUtils;
import com.spring.common.response.ApiResponseResult;
import com.spring.maintenance.dao.ExcelExportDao;
import com.spring.maintenance.service.IAsynExcelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:excel实现类
 * @author: 赵进华
 * @time: 2020/4/2 13:27
 */
@Slf4j
@Service("excelService")
public class ExcelServiceImpl extends BaseServiceImpl<ExcelExportEntity, String> implements IAsynExcelService {
    @Autowired
    private ExcelExportDao sqlDao;

    @Override
    public BaseDao getBaseMapper() {
        return sqlDao;
    }

    @Autowired
    private ExcelExportService excelExportService;

    /**
     * @Desc:   投诉建议导出
     * @param vo
     * @Author:邓磊
     * @UpdateDate:2020/4/20 10:32
     * @return: 返回
     */
    @Override
    public ApiResponseResult asynExportComplaintProposal(ComplaintProposalEntity vo) throws Exception {
        ApiResponseResult result = new ApiResponseResult();
        log.info("asynExportComplaintProposal userId:" + RequestUtils.getUserId() + ",vo:" + vo.toString());
        excelExportService.exportComplaintProposal(vo);
        result.setCode(MessageCode.SUCCESS);
        result.setMsg("异步导出excel中,请耐心等待！");
        return result;
    }


    /**
     * @Desc:  导出报事报修
     * @param vo
     * @Author:邓磊
     * @UpdateDate:2020/4/24 19:38
     */
    @Override
    public ApiResponseResult asynExportReportingRepair(ReportingRepairEntity vo) throws Exception {
        ApiResponseResult result = new ApiResponseResult();
        log.info("asynExportReportingRepair userId:" + RequestUtils.getUserId() + ",vo:" + vo.toString());
        excelExportService.asynExportReportingRepair(vo);
        result.setCode(MessageCode.SUCCESS);
        result.setMsg("异步导出excel中,请耐心等待！");
        return result;
    }

}
