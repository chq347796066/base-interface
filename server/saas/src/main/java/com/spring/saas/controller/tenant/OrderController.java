package com.spring.saas.controller.tenant;

import com.spring.base.entity.saas.OrderEntity;
import com.spring.base.vo.saas.*;
import com.spring.common.page.RequestPageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.spring.base.controller.BaseController;
import com.spring.common.exception.ValidationException;
import com.spring.common.response.ApiResponseResult;
import com.spring.saas.service.IOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;


 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-08 14:55:54
 * @Desc类说明: 订单控制器
 */
@RestController
@Api(value = "订单", tags = {"订单接口"})
@RequestMapping("order")
public class OrderController extends BaseController{

	@Autowired
	private IOrderService orderService;
	
	/**
	 * 新增
	 * @param vo
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "创建订单")
	@PostMapping(value = "/createOrder")
	public ApiResponseResult add(@RequestBody @Validated OrderAddVo vo, BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return orderService.addOrder(vo);
	}

	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "删除订单信息", notes = "删除订单信息")
	@GetMapping(value = "/deleteOrder")
	public ApiResponseResult delete(
			@ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id)
			throws Exception {
		return orderService.deleteOrder(id);
	}

	/**
	 * 根据主键id查询对象
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "查询订单详情", response = OrderResponseVo.class)
	@GetMapping(value = "/queryOrderDetail")
	public ApiResponseResult queryObject(
			@ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id)
			throws Exception {
		return orderService.queryOrderDetail(id);
	}

	/**
	 * 根据条件分页查询
	 * @param requestPageVO
	 * @return
	 */
	@ApiOperation(value = "根据条件查询订单列表(分页)", response = OrderResponseVo.class)
	@PostMapping(value = "/queryOrderListByPage")
	public ApiResponseResult queryOrderListByPage(@RequestBody RequestPageVO<OrderQueryVo> requestPageVO) throws Exception {
		return orderService.queryOrderList(requestPageVO);
	}

	 /**
	  * 取消订单
	  * @param orderNum
	  * @return
	  */
	 @ApiOperation(value = "取消订单", response = OrderEntity.class)
	 @GetMapping(value = "/cancelOrder")
	 public ApiResponseResult cancelOrder(
			 @ApiParam(value = "订单号", required = true) @RequestParam(value = "orderNum", required = true) String orderNum)
			 throws Exception {
		 return orderService.cancelOrder(orderNum);
	 }

	 @ApiOperation(value = "分页查询费用账单详情（运营后台）", response = ExpenseBillPageVo.class)
	 @PostMapping(value = "/queryExpenseBillByPage")
	 public ApiResponseResult queryExpenseBillPageList(@RequestBody RequestPageVO<ExpenseBillQueryVo> requestPageVO) throws Exception {
		 return orderService.queryExpenseBillPageList(requestPageVO);
	 }
}
