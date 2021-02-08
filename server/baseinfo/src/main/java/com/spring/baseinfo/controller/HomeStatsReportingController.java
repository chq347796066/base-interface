package com.spring.baseinfo.controller;

import com.spring.base.controller.BaseController;
import com.spring.base.vo.baseinfo.community.CommunityStatisticsVo;
import com.spring.base.vo.baseinfo.home.HomeVo;
import com.spring.baseinfo.service.impl.HomeStatsReportingServiceImpl;
import com.spring.common.response.ApiResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Desc: 工单统计
 * @Author:邓磊
 * @UpdateDate:2020/4/28 20:02
 */
@RestController
@Api(value = "首页工单统计信息", tags = "首页工单统计信息接口")
@RequestMapping("home")
public class HomeStatsReportingController extends BaseController{
    @Autowired
    private HomeStatsReportingServiceImpl homeStatsReportingService;


    @ApiOperation(value = "工单统计信息")
    @PostMapping(value = "/statistics")
    public ApiResponseResult statisticsWorkOrder(@RequestBody @Validated HomeVo vo) throws Exception {
        return homeStatsReportingService.statisticsWorkOrder(vo);
    }


    @ApiOperation(value = "管理概况信息")
    @PostMapping(value = "/statistics/district")
    public ApiResponseResult managementOverview(@RequestBody @Validated CommunityStatisticsVo vo) throws Exception {
        return homeStatsReportingService.managementOverview(vo);
    }

}
