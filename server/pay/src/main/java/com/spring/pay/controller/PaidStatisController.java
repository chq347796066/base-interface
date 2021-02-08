package com.spring.pay.controller;

import com.spring.base.vo.pay.billdetail.BillDetailParamVo;
import com.spring.base.vo.pay.paidstatis.PaidQueryParam;
import com.spring.base.vo.pay.paidstatis.PaidReportVo;
import com.spring.common.response.ApiResponseResult;
import com.spring.pay.service.IPaidStatisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author dell
 */
@RestController
@Api(value = "实收统计报表", tags = "实收统计报表")
@RequestMapping("paidStatis")
public class PaidStatisController {

    @Autowired
    private IPaidStatisService  paidStatisService;

    @ApiOperation(value = "根据查询条件查询实收数据", response = PaidReportVo.class)
    @PostMapping(value = "/queryList")
    public ApiResponseResult queryList(@RequestBody PaidQueryParam param) throws Exception {
        return paidStatisService.queryPaidList(param);
    }

    /**
     * @Desc:实收统计报表导出
     * @param param
     * @Author:邓磊
     * @UpdateDate:2020/5/20 15:58
     * @return: 返回
     */
    @ApiOperation(value = "实收统计报表导出", response = BillDetailParamVo.class)
    @GetMapping(value = "/exportPaidQueryParamInfo")
    public void  exportPaidQueryParamInfo(PaidQueryParam param)throws Exception {
        paidStatisService.exportPaidQueryParamInfo(param);
    }


}
