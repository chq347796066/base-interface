package com.spring.maintenance.service;

import com.spring.base.entity.buiness.ComplaintProposalEntity;
import com.spring.base.entity.buiness.ReportingRepairEntity;
import com.spring.common.response.ApiResponseResult;


/**
 * @description:excel接口
 * @return:
 * @author: 赵进华
 * @time: 2020/4/2 14:26
 */
public interface IAsynExcelService {

    /**
     * @Desc: 导出投诉建议
     * @param vo
     * @Author:邓磊
     * @UpdateDate:2020/4/20 10:26
     * @return: ApiResponseResult
     */
    ApiResponseResult asynExportComplaintProposal(ComplaintProposalEntity vo) throws Exception;

    /**
     * @Desc:  导出报事报修
     * @param vo
     * @Author:邓磊
     * @UpdateDate:2020/4/24 19:34
     * @return: 返回
     */
    ApiResponseResult asynExportReportingRepair(ReportingRepairEntity vo) throws Exception;

}
