package com.spring.base.vo.pay.paidstatis;

import lombok.Data;

import java.util.List;

/**
 * @author dell
 */
@Data
public class PaidReportVo {
    private SummaryVo summaryVo;
    private List<PaidStatisVo> paidStatisVoList;
}
