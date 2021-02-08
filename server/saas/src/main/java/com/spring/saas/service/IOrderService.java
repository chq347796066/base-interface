package com.spring.saas.service;


import com.spring.base.entity.saas.OrderEntity;
import com.spring.base.service.IBaseService;
import com.spring.base.vo.saas.OrderAddVo;
import com.spring.base.vo.saas.OrderUpdateVo;
import com.spring.base.vo.saas.*;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-08 14:55:54
 * @Desc类说明: 订单业务接口类
 */
public interface IOrderService extends IBaseService<OrderEntity,String> {

	/**
	 * 新增订单
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua
	 * @version 创建时间：2020-07-08 14:55:54
	 */
	 ApiResponseResult addOrder(OrderAddVo vo) throws Exception;

	/**
	 * 删除订单信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua
	 * @version 创建时间：2020-07-08 14:55:54
	 */
	 ApiResponseResult deleteOrder(String id) throws Exception;

	 /**
	  * 取消订单
	  * @param orderNum
	  * @return
	  * @throws Exception
	  * @author 作者：ZhaoJinHua
	  * @version 创建时间：2020-07-08 14:55:54
	  */
	 ApiResponseResult cancelOrder(String orderNum) throws Exception;

	 /**
	  * 支付成功修改订单信息
	  * @author 熊锋
	  * @param vo
	  * @date 2020/7/8 16:58
	  * @return com.spring.common.response.ApiResponseResult
	  * @throws Exception
	  */
	 ApiResponseResult updateOrder(OrderUpdateVo vo) throws Exception;

	/**
	 * 查询租户应用关系
	 *
	 * @param tenantIdList
	 * @return
	 * @throws Exception
	 * @author WuJiaQuan
	 * @date 2020/7/9 14:06
	 */
	List<TenantAppVo> queryTenantAppRelationByTenantIds(List<String> tenantIdList) throws Exception;

	/**
	 * 分页查询费用账单
	 *
	 * @author WuJiaQuan
	 * @param requestPageVO
	 * @date 2020/7/9 18:53
	 * @throws Exception
	 * @return
	 */
	ApiResponseResult queryExpenseBillPageList(RequestPageVO<ExpenseBillQueryVo> requestPageVO) throws Exception;

	/**
	 * 分页查询订单数据
	 * @author 熊锋
	 * @param requestPageVO
	 * @date 2020/7/10 15:55
	 * @return com.spring.common.response.ApiResponseResult
	 * @throws Exception
	 */
	ApiResponseResult queryOrderList(RequestPageVO<OrderQueryVo> requestPageVO) throws Exception;

	/**
	 * 根据Id查询订单详情
	 * @author 熊锋
	 * @param id
	 * @date 2020/7/10 16:30
	 * @return com.spring.common.response.ApiResponseResult
	 * @throws Exception
	 */
	ApiResponseResult queryOrderDetail(String id) throws Exception;
 }
