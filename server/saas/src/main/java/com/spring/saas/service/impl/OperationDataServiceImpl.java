package com.spring.saas.service.impl;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.saas.OrderEntity;
import com.spring.base.entity.saas.TenantEntity;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.base.vo.saas.*;
import com.spring.common.constants.MessageCode;
import com.spring.common.enums.OrderTypeEnum;
import com.spring.common.enums.StatisticsTimeTypeEnum;
import com.spring.common.exception.ServiceException;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.TimestampUtils;
import com.spring.common.util.date.DateHelperExt;
import com.spring.common.util.date.DateStyle;
import com.spring.saas.dao.IOrderDao;
import com.spring.saas.dao.ITenantDao;
import com.spring.saas.service.IOrderService;
import com.spring.saas.service.ITenantService;
import com.spring.saas.service.IOperationDataService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 运营接口
 *
 * @author WuJiaQuan
 */
@Service("operationService")
public class OperationDataServiceImpl extends BaseServiceImpl<TenantEntity, String> implements IOperationDataService {

    @Autowired
    private ITenantDao tenantDao;

    @Autowired
    private IOrderDao orderDao;

    @Override
    public BaseDao getBaseMapper() {
        return null;
    }

    /**
     * 查询运营数据统计对比
     *
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @date 2020/7/20 18:35
     */
    @Override
    public ApiResponseResult queryOperationDataCountCompare() throws Exception {
        // 统计本月新增租户
        Long newTenantNum = tenantDao.queryNewTenantNumByThisMonth();
        // 统计上月新增租户
        Long lastNewTenantNum = tenantDao.queryNewTenantNumByLastMonth();

        // 统计本月续费租户
        Long renewTenementNum = tenantDao.queryTenantRenewalNumByThisMonth();
        // 统计上月续费租户
        Long lastRenewTenementNum = tenantDao.queryTenantRenewalNumByLastMonth();

        // 统计本月订单金额
        BigDecimal orderAmount = orderDao.queryOrderAmountByThisMonth();
        // 统计上月订单金额
        BigDecimal lastOrderAmount = orderDao.queryOrderAmountByLastMonth();

        // 统计本月到期租户
        Long tenantDueNum = tenantDao.queryTenantsDueNumByThisMonth();
        // 统计上月到期租户
        Long lastTenantDueNum = tenantDao.queryTenantsDueNumByLastMonth();

        // 计算本月新增租户环比
        Double tenementComparePer = calculateRingRatio(lastNewTenantNum.doubleValue(), newTenantNum.doubleValue());

        // 计算本月续费租户环比
        Double renewTenementComparePer = calculateRingRatio(lastRenewTenementNum.doubleValue(), renewTenementNum.doubleValue());

        // 计算本月订单金额环比
        Double orderAmountComparePer = calculateRingRatio(lastOrderAmount.doubleValue(), orderAmount.doubleValue());

        // 计算本月到期租户环比
        Double maturityTenementComparePer = calculateRingRatio(lastTenantDueNum.doubleValue(), tenantDueNum.doubleValue());

        OperationDataCountCompareVo operationDataCountCompareVo = new OperationDataCountCompareVo();

        // 本月新增租户
        operationDataCountCompareVo.setNewTenantNum(newTenantNum.intValue());
        // 本月新增租户环比上月
        operationDataCountCompareVo.setNewTenantComparePer(tenementComparePer.intValue());

        // 本月续费租户
        operationDataCountCompareVo.setRenewTenementNum(renewTenementNum.intValue());
        // 本月续费租户环比上月
        operationDataCountCompareVo.setRenewTenementComparePer(renewTenementComparePer.intValue());

        // 本月订单金额
        operationDataCountCompareVo.setOrderAmount(orderAmount.intValue());
        // 本月订单金额环比上月
        operationDataCountCompareVo.setOrderAmountComparePer(orderAmountComparePer.intValue());

        // 本月到期用户
        operationDataCountCompareVo.setTenantDueNum(tenantDueNum.intValue());
        // 本月到期用户环比上月
        operationDataCountCompareVo.setTenantDueComparePer(maturityTenementComparePer.intValue());

        return createSuccessResult(operationDataCountCompareVo);
    }

    /**
     * 查询应用订购排行榜
     *
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @date 2020/7/21 9:42
     */
    @Override
    public ApiResponseResult queryAppOrderingRanking() throws Exception {
        // 查询所有已订购的应用数量
        Long allAppBuyCount = orderDao.queryAllAppBuyCount();
        if (allAppBuyCount <= 0L) {
            return createSuccessResult(null);
        }

        // 查询每个应用订购的数量
        List<AppOrderingRankingVo> appOrderingRankingVoList = orderDao.queryEachAppBuyCount();

        for (AppOrderingRankingVo vo : appOrderingRankingVoList) {
            // 计算每个应用的占比
            double proportion = (double) vo.getAppBuyCount() / allAppBuyCount * 100;
            // 百分比 2位小数
            vo.setProportion(BigDecimal.valueOf(proportion).setScale(2, BigDecimal.ROUND_HALF_UP));
        }

        // 数据降序排序
        appOrderingRankingVoList = CollectionUtils.isNotEmpty(appOrderingRankingVoList)
                ? appOrderingRankingVoList.stream().sorted(Comparator.comparing(AppOrderingRankingVo::getProportion)
                .reversed()).collect(Collectors.toList()) : null;

        return createSuccessResult(appOrderingRankingVoList);
    }

    /**
     * 查询租户订购订单信息
     *
     * @param tenantOrderSituationQueryVo
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @date 2020/7/21 14:04
     */
    @Override
    public ApiResponseResult queryTenantOrderSituationCountInfo(TenantOrderSituationQueryVo tenantOrderSituationQueryVo) throws Exception {
        List<TimestampYearVo> timestampYearVoList = null;
        Map<String, TenantOrderSituationCountVo> tenantOrderSituationCountVoMap = null;

        // 查询统计有效租户
        Long effectiveTenantCount = tenantDao.queryEffectiveTenantCount();

        if (tenantOrderSituationQueryVo != null) {
            if (StringUtils.isEmpty(tenantOrderSituationQueryVo.getStartDate())
                    || StringUtils.isEmpty(tenantOrderSituationQueryVo.getEndDate())) {
                throw new ServiceException("请选择查询时间范围");
            }

            if (StringUtils.isNotEmpty(tenantOrderSituationQueryVo.getStartDate())
                    && StringUtils.isEmpty(tenantOrderSituationQueryVo.getEndDate())) {
                throw new ServiceException("请选择结束时间");
            }

            if (StringUtils.isEmpty(tenantOrderSituationQueryVo.getStartDate())
                    && StringUtils.isNotEmpty(tenantOrderSituationQueryVo.getEndDate())) {
                throw new ServiceException("请选择起始时间");
            }

            // 起始时间
            Date startDate = DateHelperExt.stringToDate(tenantOrderSituationQueryVo.getStartDate(), DateStyle.YYYY_MM_DD);
            // 结束时间
            Date endDate = DateHelperExt.stringToDate(tenantOrderSituationQueryVo.getEndDate(), DateStyle.YYYY_MM_DD);

            // 开始时间不能大于结束时间
            if (startDate.after(endDate)) {
                throw new ServiceException("起始时间不能大于结束时间");
            }

            // 组装时间戳
            timestampYearVoList = TimestampUtils.getTimestamp(tenantOrderSituationQueryVo.getStartDate(),
                    tenantOrderSituationQueryVo.getEndDate(),
                    tenantOrderSituationQueryVo.getStatisticsTimeType());

            tenantOrderSituationQueryVo.setStartDate(tenantOrderSituationQueryVo.getStartDate() + " 00:00:00");
            tenantOrderSituationQueryVo.setEndDate(tenantOrderSituationQueryVo.getEndDate() + " 23:59:59");

            // 查询租户订购的订单信息
            List<OrderEntity> orderList = orderDao.queryTenantOrderInfo(tenantOrderSituationQueryVo);

            if (CollectionUtils.isNotEmpty(orderList)) {
                // 分组时间格式
                String pattern = tenantOrderSituationQueryVo.getStatisticsTimeType().equals(StatisticsTimeTypeEnum.STATICS_BY_YEAR.getEnumCode())
                        ? DateStyle.YYYY.getValue()
                        : tenantOrderSituationQueryVo.getStatisticsTimeType().equals(StatisticsTimeTypeEnum.STATICS_BY_MONTH.getEnumCode())
                        ? DateStyle.YYYY_MM.getValue()
                        : tenantOrderSituationQueryVo.getStatisticsTimeType().equals(StatisticsTimeTypeEnum.STATICS_BY_DAY.getEnumCode())
                        ? DateStyle.YYYY_MM_DD.getValue()
                        : DateStyle.YYYY_MM.getValue();

                // 按支付时间分组订单数据
                Map<String, List<OrderEntity>> orderGroupByMap = orderList.stream()
                        .collect(Collectors.groupingBy(o -> o.getPayDate().format(DateTimeFormatter.ofPattern(pattern))));

                // 组装统计每个时间段的订单报表数据
                tenantOrderSituationCountVoMap = new HashMap<>(16);
                for (Map.Entry<String, List<OrderEntity>> orderGroupBy : orderGroupByMap.entrySet()) {
                    TenantOrderSituationCountVo tenantOrderSituationCountVo = new TenantOrderSituationCountVo();
                    // 统计试用订单数量
                    tenantOrderSituationCountVo.setTrialCount(orderGroupBy.getValue().stream()
                            .filter(o -> o.getOrderType().equals(OrderTypeEnum.TRT_OUT.getEnumCode())).count());
                    // 统计订购订单数量
                    tenantOrderSituationCountVo.setOrderCount(orderGroupBy.getValue().stream()
                            .filter(o -> o.getOrderType().equals(OrderTypeEnum.ORDER.getEnumCode())).count());
                    // 统计续费订单数量
                    tenantOrderSituationCountVo.setRenewalCount(orderGroupBy.getValue().stream()
                            .filter(o -> o.getOrderType().equals(OrderTypeEnum.RENEW.getEnumCode())).count());
                    // 统计升级订单数量
                    tenantOrderSituationCountVo.setUpgradeCount(orderGroupBy.getValue().stream()
                            .filter(o -> o.getOrderType().equals(OrderTypeEnum.UPGRADE.getEnumCode())).count());

                    tenantOrderSituationCountVoMap.put(orderGroupBy.getKey(), tenantOrderSituationCountVo);
                }
            }
        }

        TenantOrderSituationVo tenantOrderSituationVo = new TenantOrderSituationVo();
        tenantOrderSituationVo.setEffectiveTenantCount(effectiveTenantCount == null ? 0L : effectiveTenantCount);
        tenantOrderSituationVo.setTenantOrderSituationCountVoMap(tenantOrderSituationCountVoMap);
        tenantOrderSituationVo.setTimestampVoList(timestampYearVoList);

        return createSuccessResult(tenantOrderSituationVo);
    }

    /**
     * 查询订单状况信息
     *
     * @param orderStatusQueryVo
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @date 2020/7/23 10:40
     */
    @Override
    public ApiResponseResult queryOrderStatusInfo(OrderStatusQueryVo orderStatusQueryVo) throws Exception {
        List<TimestampYearVo> timestampYearVoList = null;
        Map<String, BigDecimal> orderStatusInfoMap = null;

        if (orderStatusQueryVo != null) {
            if (StringUtils.isEmpty(orderStatusQueryVo.getStartDate())
                    || StringUtils.isEmpty(orderStatusQueryVo.getEndDate())) {
                throw new ServiceException("请选择查询时间范围");
            }

            if (StringUtils.isNotEmpty(orderStatusQueryVo.getStartDate())
                    && StringUtils.isEmpty(orderStatusQueryVo.getEndDate())) {
                throw new ServiceException("请选择结束时间");
            }

            if (StringUtils.isEmpty(orderStatusQueryVo.getStartDate())
                    && StringUtils.isNotEmpty(orderStatusQueryVo.getEndDate())) {
                throw new ServiceException("请选择起始时间");
            }

            // 起始时间
            Date startDate = DateHelperExt.stringToDate(orderStatusQueryVo.getStartDate(), DateStyle.YYYY_MM_DD);
            // 结束时间
            Date endDate = DateHelperExt.stringToDate(orderStatusQueryVo.getEndDate(), DateStyle.YYYY_MM_DD);

            // 开始时间不能大于结束时间
            if (startDate.after(endDate)) {
                throw new ServiceException("起始时间不能大于结束时间");
            }

            // 组装时间戳
            timestampYearVoList = TimestampUtils.getTimestamp(orderStatusQueryVo.getStartDate(),
                    orderStatusQueryVo.getEndDate(),
                    orderStatusQueryVo.getStatisticsTimeType());

            orderStatusQueryVo.setStartDate(orderStatusQueryVo.getStartDate() + " 00:00:00");
            orderStatusQueryVo.setEndDate(orderStatusQueryVo.getEndDate() + " 23:59:59");

            List<OrderAmountSumVo> orderAmountSumVoList = orderDao.queryOrderStatusInfo(orderStatusQueryVo);

            if (CollectionUtils.isNotEmpty(orderAmountSumVoList)) {
                // 分组时间格式
                String pattern = orderStatusQueryVo.getStatisticsTimeType().equals(StatisticsTimeTypeEnum.STATICS_BY_YEAR.getEnumCode())
                        ? DateStyle.YYYY.getValue()
                        : orderStatusQueryVo.getStatisticsTimeType().equals(StatisticsTimeTypeEnum.STATICS_BY_MONTH.getEnumCode())
                        ? DateStyle.YYYY_MM.getValue()
                        : orderStatusQueryVo.getStatisticsTimeType().equals(StatisticsTimeTypeEnum.STATICS_BY_DAY.getEnumCode())
                        ? DateStyle.YYYY_MM_DD.getValue()
                        : DateStyle.YYYY_MM.getValue();

                // 按支付时间分组订单数据
                Map<String, List<OrderAmountSumVo>> orderGroupByMap = orderAmountSumVoList.stream()
                        .collect(Collectors.groupingBy(o -> o.getPaymentDate().format(DateTimeFormatter.ofPattern(pattern))));

                // 组装统计每个时间段的订单报表数据
                orderStatusInfoMap = new HashMap<>(16);
                for (Map.Entry<String, List<OrderAmountSumVo>> orderGroupBy : orderGroupByMap.entrySet()) {
                    BigDecimal orderAmount = orderGroupBy.getValue().stream()
                            .map(OrderAmountSumVo::getOrderAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                    orderStatusInfoMap.put(orderGroupBy.getKey(), orderAmount);
                }
            }
        }

        // 查询累计成交金额
        BigDecimal amountByAll = orderDao.queryOrderAmountByAll();
        // 查询当月成交金额
        BigDecimal amountByThisMonth = orderDao.queryOrderAmountByThisMonth();
        // 查询上月成交金额
        BigDecimal amountByLastMonth = orderDao.queryOrderAmountByLastMonth();

        OrderStatusInfoVo orderStatusInfoVo = new OrderStatusInfoVo();
        orderStatusInfoVo.setAmountByAll(amountByAll);
        orderStatusInfoVo.setAmountByThisMonth(amountByThisMonth);
        orderStatusInfoVo.setAmountByLastMonth(amountByLastMonth);
        orderStatusInfoVo.setOrderStatusInfoMap(orderStatusInfoMap);
        orderStatusInfoVo.setTimestampVoList(timestampYearVoList);

        return createSuccessResult(orderStatusInfoVo);
    }

    /**
     * 计算环比
     *
     * @param last
     * @param current
     * @return double
     * @throws Exception
     * @author WuJiaQuan
     * @date 2020-1-9 11:15
     */
    private Double calculateRingRatio(Double last, Double current) {
        if (last.compareTo(Double.MIN_VALUE) < 0) {
            if (current.compareTo(Double.MIN_VALUE) > 0) {
                return 100d;
            }

            return 0d;
        }
        return (current - last) / last * 100;
    }
}
