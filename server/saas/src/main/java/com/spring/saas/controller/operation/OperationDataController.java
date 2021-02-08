package com.spring.saas.controller.operation;

import com.spring.base.controller.BaseController;
import com.spring.base.vo.saas.*;
import com.spring.common.response.ApiResponseResult;
import com.spring.saas.service.IOperationDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 运营后台首页数据接口
 *
 * @author WuJiaQuan
 */
@RestController
@Api(value = "运营后台首页数据", tags = "运营后台首页数据接口")
@RequestMapping("operationData")
public class OperationDataController extends BaseController {

    @Autowired
    private IOperationDataService operationDataService;

    @ApiOperation(value = "运营数据统计对比", response = OperationDataCountCompareVo.class)
    @GetMapping(value = "/operationDataCountCompare")
    public ApiResponseResult operationDataCountCompare() throws Exception {
        return operationDataService.queryOperationDataCountCompare();
    }

    @ApiOperation(value = "应用排行榜", response = OperationDataCountCompareVo.class)
    @GetMapping(value = "/appOrderingRanking")
    public ApiResponseResult queryAppOrderingRanking() throws Exception {
        return operationDataService.queryAppOrderingRanking();
    }

    @ApiOperation(value = "查询租户订购订单信息", response = TenantOrderSituationVo.class)
    @PostMapping(value = "/tenantOrderSituationInfo")
    public ApiResponseResult queryTenantOrderSituationCountInfo(@RequestBody TenantOrderSituationQueryVo tenantOrderSituationQueryVo) throws Exception {
        return operationDataService.queryTenantOrderSituationCountInfo(tenantOrderSituationQueryVo);
    }

    @ApiOperation(value = "查询订单状况信息", response = OrderStatusInfoVo.class)
    @PostMapping(value = "/orderStatusInfo")
    public ApiResponseResult queryTenantOrderSituationCountInfo(@RequestBody OrderStatusQueryVo orderStatusQueryVo) throws Exception {
        return operationDataService.queryOrderStatusInfo(orderStatusQueryVo);
    }
}
