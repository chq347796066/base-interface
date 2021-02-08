package com.spring.saas.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.base.entity.saas.OrderEntity;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.base.vo.saas.*;
import com.spring.common.exception.ServiceException;
import com.spring.common.page.RequestPageVO;
import com.spring.common.request.RequestUtils;
import com.spring.common.util.date.DateHelperExt;
import com.spring.common.util.date.DateStyle;
import com.spring.common.util.date.DateTime;
import com.spring.saas.dao.ITenantDao;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.base.dao.BaseDao;
import com.spring.saas.dao.IOrderDao;
import com.spring.saas.service.IOrderService;
import org.springframework.beans.BeanUtils;
import com.spring.common.constants.MessageCode;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.id.UUIDFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-08 14:55:54
 * @Desc类说明: 订单业务接口实现类
 */

 @Slf4j
@Service("orderService")
public class OrderServiceImpl extends BaseServiceImpl<OrderEntity, String> implements IOrderService {
	
	@Autowired
	private IOrderDao orderDao;

	@Autowired
	private ITenantDao tenantDao;

	@Override
	public BaseDao getBaseMapper() {
		return orderDao;
	}
	
	/**
	 * 新增订单
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua
	 * @version 创建时间：2020-07-08 14:55:54
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ApiResponseResult addOrder(OrderAddVo vo) throws Exception {
		// 返回的对象
		ApiResponseResult result = new ApiResponseResult();
		StringBuilder builder=new StringBuilder();
		OrderVo orderVo=new OrderVo();
		builder.append(",");
		builder.append(vo.getAppId());
		//生成订单号
		String orderNum=DateTime.createOderNum();
		OrderEntity entity = new OrderEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setOrderNum(orderNum);
		entity.setOrderStatus(1);
		entity.setMakeInvoiceStatus(4);
		entity.setOrderDate(new Date());
		entity.setId(UUIDFactory.createId());
		entity.setCreateUser(RequestUtils.getUserId());
		entity.setCreateDate(new Date());
		entity.setTenantId(RequestUtils.getTenantId());
		orderVo.setId(entity.getId());
		orderVo.setOrderNum(orderNum);
		//往订单表插入数据
		int no = orderDao.insert(entity);
		//更新租户表绑定的应用id(用户购买了哪些应用)
		int num=tenantDao.updateTenantApp(vo.getMobile(),builder.toString());
		if (no > 0 && num>0) {
			result.setCode(MessageCode.SUCCESS);
			result.setMsg("成功");
			result.setData(orderVo);
		} else {
			result.setCode(MessageCode.FAIL);
			result.setMsg("新增失败");
		}
		return result;
	}

	/**
	 * 删除订单信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-07-08 14:55:54
	 */
	@Override
	public ApiResponseResult deleteOrder(String id) throws Exception {
		OrderEntity entity = new OrderEntity();
		entity.setId(id);
		entity.setDelFlag(1);
		//
		int no = orderDao.updateById(entity);
		if (no > 0) {
			return createSuccessResult(null);
		}
		return createFailResult();
	}

	/**
	 * 取消订单
	 * @author 熊锋
	 * @param orderNum
	 * @date 2020/7/8 16:30
	 * @return com.spring.common.response.ApiResponseResult
	 * @throws Exception
	 */
	 @Override
	 public ApiResponseResult cancelOrder(String orderNum) throws Exception {
	 	 ApiResponseResult result=new ApiResponseResult();
	 	 int no=orderDao.cancelOrder(orderNum);
	 	 if(no>0){
	 	 	result.setCode(MessageCode.SUCCESS);
	 	 	result.setMsg("订单取消成功");
		 }else{
			 result.setCode(MessageCode.FAIL);
			 result.setMsg("订单取消失败");
		 }
		 return result;
	 }

	 /**
	  * 支付成功修改订单信息
	  * @author 熊锋
	  * @param vo
	  * @date 2020/7/8 17:03
	  * @return com.spring.common.response.ApiResponseResult
	  * @throws Exception
	  */
	 @Override
	 public ApiResponseResult updateOrder(OrderUpdateVo vo) throws Exception {
		 ApiResponseResult result=new ApiResponseResult();
		 int no=orderDao.updatePay(vo);
		 if (no>0){
		 	result.setCode(MessageCode.SUCCESS);
		 	result.setMsg("成功");
		 }else{
		 	result.setCode(MessageCode.FAIL);
		 	result.setMsg("失败");
		 }
		 return result;

	 }
	/**
	 * 查询租户应用关系
	 *
	 * @param tenantIdList
	 * @return
	 * @throws Exception
	 * @author WuJiaQuan
	 * @date 2020/7/9 14:06
	 */
	@Override
	public List<TenantAppVo> queryTenantAppRelationByTenantIds(List<String> tenantIdList) throws Exception {
		List<TenantAppVo> tenantAppQueryList = null;
		if (CollectionUtils.isNotEmpty(tenantIdList)) {
			tenantAppQueryList = orderDao.queryTenantAppRelationByTenantIds(tenantIdList);
		}
		return tenantAppQueryList;
	}

	/**
	 * 分页查询费用账单
	 *
	 * @param requestPageVO
	 * @return
	 * @throws Exception
	 * @author WuJiaQuan
	 * @date 2020/7/9 18:53
	 */
	@Override
	public ApiResponseResult queryExpenseBillPageList(RequestPageVO<ExpenseBillQueryVo> requestPageVO) throws Exception {
		// 设置分页参数
		PageHelper.startPage(requestPageVO.getCurrentPage(), requestPageVO.getPageSize(), true);

		ExpenseBillQueryVo expenseBillQueryVo = requestPageVO.getEntity();

		if (expenseBillQueryVo != null) {
			if (StringUtils.isNotEmpty(expenseBillQueryVo.getOrderNumOrTenantCode())) {
				expenseBillQueryVo.setOrderNumOrTenantCode("%" + expenseBillQueryVo.getOrderNumOrTenantCode() + "%");
			}

			if (StringUtils.isNotEmpty(expenseBillQueryVo.getStartDate())
					&& StringUtils.isEmpty(expenseBillQueryVo.getEndDate())) {
				throw new ServiceException("请选择结束时间");
			}

			if (StringUtils.isEmpty(expenseBillQueryVo.getStartDate())
					&& StringUtils.isNotEmpty(expenseBillQueryVo.getEndDate())) {
				throw new ServiceException("请选择起始时间");
			}

			if (StringUtils.isNotEmpty(expenseBillQueryVo.getStartDate())
					&& StringUtils.isNotEmpty(expenseBillQueryVo.getEndDate())) {

				Date startDate = DateHelperExt.stringToDate(expenseBillQueryVo.getStartDate(), DateStyle.YYYY_MM_DD);
				Date endDate = DateHelperExt.stringToDate(expenseBillQueryVo.getEndDate(), DateStyle.YYYY_MM_DD);

				// 开始时间不能大于结束时间
				if (startDate.after(endDate)) {
					throw new ServiceException("起始时间时间不能大于结束时间");
				}

				expenseBillQueryVo.setStartDate(expenseBillQueryVo.getStartDate() + " 00:00:00");
				expenseBillQueryVo.setEndDate(expenseBillQueryVo.getEndDate() + " 23:59:59");
			}
		}

		// 查询费用账单
		List<ExpenseBillPageVo> expenseBillPageVoList = orderDao.queryExpenseBillPageList(expenseBillQueryVo);

		// 设置分页
		PageInfo<ExpenseBillPageVo> pageInfo = new PageInfo<>(expenseBillPageVoList);
		return createSuccessResult(pageInfo);
	}

	/**
	 * 分页查询订单数据
	 * @author 熊锋
	 * @param requestPageVO
	 * @date 2020/7/10 15:55
	 * @return com.spring.common.response.ApiResponseResult
	 * @throws Exception
	 */
	@Override
	public ApiResponseResult queryOrderList(RequestPageVO<OrderQueryVo> requestPageVO) throws Exception {
		PageHelper.startPage(requestPageVO.getCurrentPage(), requestPageVO.getPageSize(), true);
		List<OrderResponseVo> list=orderDao.queryOrderList(requestPageVO.getEntity());
		PageInfo<OrderResponseVo> pageInfo=new PageInfo<>(list);
		return createSuccessResult(pageInfo);
	}

	/**
	 * 根据Id查询订单详情
	 * @author 熊锋
	 * @param id
	 * @date 2020/7/10 16:30
	 * @return com.spring.common.response.ApiResponseResult
	 * @throws Exception
	 */
	@Override
	public ApiResponseResult queryOrderDetail(String id) throws Exception{
		OrderResponseVo vo=orderDao.queryOrderDetail(id);
		return createSuccessResult(vo);
	}
}
