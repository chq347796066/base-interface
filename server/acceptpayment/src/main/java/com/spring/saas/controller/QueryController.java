package com.spring.saas.controller;

import com.spring.enums.BestPayPlatformEnum;
import com.spring.model.OrderQueryRequest;
import com.spring.model.OrderQueryResponse;
import com.spring.saas.service.BestPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 查询
 * @author dell
 */
@RestController
@RequestMapping("/query")
public class QueryController {

	@Autowired
	private BestPayService bestPayService;

	@GetMapping("/payOrder")
	public OrderQueryResponse payOrder(@RequestParam(required = false) String orderId,
									   @RequestParam(required = false) String outOrderId,
									   @RequestParam BestPayPlatformEnum platformEnum) {
		OrderQueryRequest request = new OrderQueryRequest();
		request.setPlatformEnum(platformEnum);
		request.setOrderId(orderId);
		request.setOutOrderId(outOrderId);
		return bestPayService.query(request);
	}
}
