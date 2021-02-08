package com.spring.saas.controller.tenant;

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
import com.spring.saas.service.IInvoiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;


 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-09 10:48:56
 * @Desc类说明: 发票信息控制器
 */
@RestController
@Api(value = "发票信息", tags = {"发票信息接口"})
@RequestMapping("invoice")
public class InvoiceController extends BaseController{

	@Autowired
	private IInvoiceService invoiceService;

	/**
	 * 新增
	 * @param vo
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "索取发票")
	@PostMapping(value = "/getInvoice")
	public ApiResponseResult add(@RequestBody @Validated InvoiceAddVo vo, BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return invoiceService.addInvoice(vo);
	}

	/**
	 * 根据主键id查询对象
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "查询开票详情", response = OrderResponseVo.class)
	@GetMapping(value = "/queryMakeInvoiceDetail")
	public ApiResponseResult queryObject(
			@ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id)
			throws Exception {
		return invoiceService.queryMakeInvoiceDetail(id);
	}

	/**
	 * 根据条件分页查询
	 * @param requestPageVO
	 * @return
	 */
	@ApiOperation(value = "查询发票列表", response = InvoiceResponseVo.class)
	@PostMapping(value = "/queryInvoice")
	public ApiResponseResult queryInvoice(@RequestBody @Validated  RequestPageVO<InvoiceQueryVo> requestPageVO) throws Exception {
		return invoiceService.queryInvoice(requestPageVO);
	}

     /**
      * 分页查询开票查询（运营后台）
      *
      * @param requestPageVO
      * @return
      * @throws Exception
      * @author WuJiaQuan
      * @date 2020/7/10 19:29
      */
     @ApiOperation(value = "分页查询开票查询（运营后台）", response = InvoiceInquiryPageVo.class)
     @PostMapping(value = "/queryInvoiceInquiryByPage")
     public ApiResponseResult invoiceInquiryQueryPage(@RequestBody RequestPageVO<InvoiceInquiryQueryVo> requestPageVO) throws Exception {
         return invoiceService.invoiceInquiryQueryPage(requestPageVO);
     }

     /**
      * 查询开票摘要
      *
      * @param invoiceId
      * @return
      * @throws Exception
      * @author WuJiaQuan
      * @date 2020/7/13 15:04
      */
     @ApiOperation(value = "查询开票摘要", response = InvoiceSummaryVo.class)
     @PostMapping(value = "/queryInvoiceSummary")
     public ApiResponseResult queryInvoiceSummary(
             @ApiParam(value = "开票Id", required = true) @RequestParam(value = "invoiceId", required = true) String invoiceId) throws Exception {
         return invoiceService.queryInvoiceSummary(invoiceId);
     }

     /**
      * 查询开票详情
      *
      * @param invoiceId
      * @return
      * @throws Exception
      * @author WuJiaQuan
      * @date 2020/7/13 15:23
      */
     @ApiOperation(value = "查询开票详情", response = InvoiceDetailVo.class)
     @PostMapping(value = "/queryInvoiceDetail")
     public ApiResponseResult queryInvoiceDetailList(
             @ApiParam(value = "开票Id", required = true) @RequestParam(value = "invoiceId", required = true) String invoiceId) throws Exception {
         return invoiceService.queryInvoiceDetailList(invoiceId);
     }

     /**
      * 审核发票
      *
      * @param invoiceReviewVo
      * @return
      * @throws Exception
      * @author WuJiaQuan
      * @date 2020/7/13 15:33
      */
     @ApiOperation(value = "审核发票", response = Boolean.class)
     @PostMapping(value = "/reviewInvoice")
     public ApiResponseResult reviewInvoice(@RequestBody InvoiceReviewVo invoiceReviewVo) throws Exception {
         return invoiceService.reviewInvoice(invoiceReviewVo);
     }

	 /**
	  * 查询开票摘要
	  * @param id
	  * @return
	  */
	 @ApiOperation(value = "查询开票摘要", response = MakeSummaryVo.class)
	 @GetMapping(value = "/queryMakeInvoiceSummary")
     public ApiResponseResult queryMakeInvoiceSummary(
			 @ApiParam(value = "发票id", required = true) @RequestParam(value = "id", required = true) String id)
	 			throws Exception {

     	return invoiceService.queryMakeInvoiceSummary(id);
	 }
 }
