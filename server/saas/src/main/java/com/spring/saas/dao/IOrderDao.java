package com.spring.saas.dao;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.saas.OrderEntity;
import com.spring.base.vo.saas.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-08 14:55:54
 * @Desc类说明: 订单Dao
 */
@Mapper
public interface IOrderDao extends BaseDao<OrderEntity> {

    /**
     * 取消订单
     * @author 熊锋
     * @param orderNum
     * @date 2020/7/8 16:55
     * @return int
     * @throws Exception
     */
    int cancelOrder(String orderNum) throws Exception;

     /**
      * 支付完成修改订单状态
      * @author 熊锋
      * @param vo
      * @date 2020/7/8 16:55
      * @return int
      * @throws Exception
      */
     int updatePay(OrderUpdateVo vo) throws Exception;

    /**
     * 查询已支付订单
     * @author 熊锋
     * @param mobile
     * @date 2020/7/8 16:58
     * @return com.spring.common.response.ApiResponseResult
     * @throws Exception
     */
     List<OrderResponseVo> queryAlreadyPayOrder(String mobile) throws Exception;

    /**
     * 查询租户应用关系
     *
     * @param tenantIdList
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @date 2020/7/9 14:06
     */
    List<TenantAppVo> queryTenantAppRelationByTenantIds(@Param("list") List<String> tenantIdList) throws Exception;

    /**
     * 分页查询费用账单
     *
     * @param expenseBillQueryVo
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @date 2020/7/9 19:12
     */
    List<ExpenseBillPageVo> queryExpenseBillPageList(ExpenseBillQueryVo expenseBillQueryVo) throws Exception;

    /**
     * 批量更新订单发票状态
     * @author 熊锋
     * @param orderIds
     * @date 2020/7/10 10:57
     * @return int
     * @throws Exception
     */
    int batchUpdate(@Param("orderIds") List<String> orderIds) throws Exception;

    /**
     * 根据订单id查询订单
     * @author 熊锋
     * @param orderIds
     * @date 2020/7/10 14:46
     * @return java.util.List<com.spring.base.vo.saas.OrderResponseVo>
     * @throws Exception
     */
    List<OrderResponseVo> queryOrderByOrderId(@Param("orderIds") List<String> orderIds) throws Exception;

    /**
     * 查询订单列表
     * @author 熊锋
     * @param vo
     * @date 2020/7/10 14:46
     * @return java.util.List<com.spring.base.vo.saas.OrderResponseVo>
     * @throws Exception
     */
    List<OrderResponseVo> queryOrderList(OrderQueryVo vo) throws Exception;

    /**
     * 根据Id查询订单详情
     * @author 熊锋
     * @param id
     * @date 2020/7/10 16:30
     * @return com.spring.common.response.ApiResponseResult
     * @throws Exception
     */
    OrderResponseVo queryOrderDetail(String id) throws Exception;

    /**
     * 查询本月订单金额
     *
     * @param
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @date 2020/7/20 17:28
     */
    BigDecimal queryOrderAmountByThisMonth() throws Exception;

    /**
     * 查询上月订单金额
     *
     * @param
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @date 2020/7/20 17:28
     */
    BigDecimal queryOrderAmountByLastMonth() throws Exception;

    /**
     * 查询统计所有订购应用的数量
     *
     * @param
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @date 2020/7/20 20:11
     */
    Long queryAllAppBuyCount() throws Exception;

    /**
     * 查询每个应用订购的数量
     *
     * @author WuJiaQuan
     * @param
     * @date 2020/7/20 20:17
     * @throws Exception
     * @return
     */
    List<AppOrderingRankingVo> queryEachAppBuyCount() throws Exception;

    /**
     * 查询租户订购订单信息
     *
     * @param tenantOrderSituationQueryVo
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @date 2020/7/21 14:02
     */
    List<OrderEntity> queryTenantOrderInfo(TenantOrderSituationQueryVo tenantOrderSituationQueryVo) throws Exception;

    /**
     * 查询订单状况信息
     *
     * @param orderStatusQueryVo
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @date 2020/7/23 10:39
     */
    List<OrderAmountSumVo> queryOrderStatusInfo(OrderStatusQueryVo orderStatusQueryVo) throws Exception;

    /**
     * 查询累计成交订单金额
     *
     * @param
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @date 2020/7/23 11:07
     */
    BigDecimal queryOrderAmountByAll() throws Exception;
}
