package com.spring.saas.service;

import com.spring.base.vo.saas.OrderStatusQueryVo;
import com.spring.base.vo.saas.TenantOrderSituationQueryVo;
import com.spring.common.response.ApiResponseResult;

/**
 * 运营数据接口
 *
 * @author WuJiaQuan
 */
public interface IOperationDataService {

    /**
     * 查询运营数据统计对比
     *
     * @param
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @date 2020/7/20 18:35
     */
    ApiResponseResult queryOperationDataCountCompare() throws Exception;

    /**
     * 查询应用订购排行榜
     *
     * @param
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @date 2020/7/21 9:42
     */
    ApiResponseResult queryAppOrderingRanking() throws Exception;

    /**
     * 查询租户订购订单信息
     *
     * @param tenantOrderSituationQueryVo
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @date 2020/7/21 14:04
     */
    ApiResponseResult queryTenantOrderSituationCountInfo(TenantOrderSituationQueryVo tenantOrderSituationQueryVo) throws Exception;

    /**
     * 查询订单状况信息
     *
     * @param orderStatusQueryVo
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @date 2020/7/23 10:40
     */
    ApiResponseResult queryOrderStatusInfo(OrderStatusQueryVo orderStatusQueryVo) throws Exception;
}
