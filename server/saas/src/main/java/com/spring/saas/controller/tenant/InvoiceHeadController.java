package com.spring.saas.controller.tenant;

import com.spring.base.entity.saas.InvoiceHeadEntity;
import com.spring.base.entity.saas.OrderEntity;
import com.spring.base.vo.saas.InvoiceHeadAddVo;
import com.spring.base.vo.saas.InvoiceHeadUpdateVo;
import com.spring.base.vo.saas.OrderResponseVo;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.spring.base.controller.BaseController;
import com.spring.common.exception.ValidationException;

import com.spring.saas.service.IInvoiceHeadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;


 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-09 10:48:56
 * @Desc类说明: 发票抬头控制器
 */
@RestController
@Api(value = "发票抬头", tags = {"发票抬头接口"})
@RequestMapping("invoice/head")
public class InvoiceHeadController extends BaseController{

	@Autowired
	private IInvoiceHeadService invoiceHeadService;
	
	/**
	 * 新增
	 * @param vo
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "新增发票抬头")
	@PostMapping(value = "/addInvoice")
	public ApiResponseResult add(@RequestBody @Validated InvoiceHeadAddVo vo, BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return invoiceHeadService.addInvoiceHead(vo);
	}

	/**
	 * 更新
	 * @param vo
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "更新发票抬头信息")
	@PostMapping(value = "/updateInvoice")
	public ApiResponseResult update(@RequestBody @Validated InvoiceHeadUpdateVo vo,
			BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return invoiceHeadService.updateInvoiceHead(vo);
	}

	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "删除发票抬头", notes = "删除发票抬头")
	@GetMapping(value = "/deleteInvoice")
	public ApiResponseResult delete(
			@ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id)
			throws Exception {

		return invoiceHeadService.deleteInvoiceHead(id);
	}

	/**
	 * 根据主键id查询对象
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "查询发票抬头详情", response = InvoiceHeadEntity.class)
	@GetMapping(value = "/queryInvoiceDetail")
	public ApiResponseResult queryObject(
			@ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id)
			throws Exception {

		return invoiceHeadService.queryObject(id);
	}

	/**
	 * 根据条件查询列表
	 * @param vo
	 * @return
	 */
	@ApiOperation(value = "查询发票抬头列表", response = InvoiceHeadEntity.class)
	@PostMapping(value = "/queryInvoiceList")
	public ApiResponseResult queryInvoiceList(@RequestBody @Validated InvoiceHeadEntity vo) throws Exception {

		return invoiceHeadService.queryList(vo);
	}

	 /**
	  * 设置默认发票抬头
	  * @return
	  */
	 @ApiOperation(value = "设置默认发票抬头")
	 @GetMapping(value = "/setDefault")
	 public ApiResponseResult setDefault(
			 @ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id)
			 throws Exception {

		 return invoiceHeadService.setDefault(id);
	 }

	 /**
	  * 查询默认发票信息
	  * @return
	  */
	 @ApiOperation(value = "查询默认发票信息")
	 @GetMapping(value = "/queryDefaultInvoice")
	 public ApiResponseResult queryDefaultInvoice(
	 		@ApiParam(value = "用户手机号", required = true) @RequestParam(value = "mobile", required = true) String mobile)
			throws Exception {

		return invoiceHeadService.queryDefaultInvoice(mobile);
	 }

	 /**
	  * 查询可索取发票金额
	  * @return
	  */
	 @ApiOperation(value = "查询可索取发票金额")
	 @GetMapping(value = "/queryInvoiceMoney")
	 public ApiResponseResult queryInvoiceMoney(
			 @ApiParam(value = "用户手机号", required = true) @RequestParam(value = "mobile", required = true) String mobile)
			 throws Exception {

		 return invoiceHeadService.queryInvoiceMoney(mobile);
	 }

	 /**
	  * 查询可开票订单
	  * @return
	  */
	 @ApiOperation(value = "查询可开票订单",response = OrderResponseVo.class)
	 @PostMapping(value = "/queryInvoiceOrder")
	 public ApiResponseResult queryInvoiceOrder(@RequestBody RequestPageVO<OrderEntity> requestPageVO)
			 throws Exception {

		 return invoiceHeadService.queryInvoiceOrder(requestPageVO);
	 }

}
