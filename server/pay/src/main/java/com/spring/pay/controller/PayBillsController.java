package com.spring.pay.controller;

import com.spring.base.entity.pay.PayBillsEntity;
import com.spring.base.vo.pay.paybills.BillParamVo;
import com.spring.base.vo.pay.paybills.PayBillsAddVo;
import com.spring.base.vo.pay.paybills.PayBillsUpdateVo;
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
import com.spring.pay.service.IPayBillsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;


 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-23 09:49:14
 * @Desc类说明: 缴费账单控制器
 */
@RestController
@Api(value = "缴费账单", tags = "缴费账单接口")
@RequestMapping("paybills")
public class PayBillsController extends BaseController{
	@Autowired
	private IPayBillsService payBillsService;
	
	/**
	 * 新增
	 * 
	 * @param vo
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "新增")
	@PostMapping(value = "/add")
	public ApiResponseResult add(@RequestBody @Validated PayBillsAddVo vo, BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return payBillsService.addPayBills(vo);
	}

	/**
	 * 更新
	 * 
	 * @param vo
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "更新")
	@PostMapping(value = "/update")
	public ApiResponseResult update(@RequestBody @Validated PayBillsUpdateVo vo,
			BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return payBillsService.updatePayBills(vo);
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "删除", notes = "删除")
	@GetMapping(value = "/delete")
	public ApiResponseResult delete(
			@ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id)
			throws Exception {
		return payBillsService.delete(id);
	}

	/**
	 * 根据主键id查询对象
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据主键id查询对象", response = PayBillsEntity.class)
	@GetMapping(value = "/queryObject")
	public ApiResponseResult queryObject(
			@ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id)
			throws Exception {
		return payBillsService.queryObject(id);
	}


	/**
	 * 根据条件查询列表
	 * 
	 * @param vo
	 * @return
	 */
	@ApiOperation(value = "根据条件查询列表", response = PayBillsEntity.class)
	@PostMapping(value = "/queryList")
	public ApiResponseResult queryList(@RequestBody PayBillsEntity vo) throws Exception {
		return payBillsService.queryList(vo);
	}

	/**
	 * 根据条件分页查询
	 * 
	 * @param requestPageVO
	 * @return
	 */
	@ApiOperation(value = "根据条件分页查询", response = PayBillsEntity.class)
	@PostMapping(value = "/queryPage")
	public ApiResponseResult queryPage(@RequestBody RequestPageVO<PayBillsEntity> requestPageVO) throws Exception {
		return payBillsService.queryPage(requestPageVO);
	}

	 /**
	  * 查询账单预览
	  *
	  * @param requestPageVO
	  * @return
	  */
	 @ApiOperation(value = "查询账单预览", response = PayBillsEntity.class)
	 @PostMapping(value = "/queryPreview")
	 public ApiResponseResult queryPreview(@RequestBody BillParamVo billParamVo) throws Exception {
		 return payBillsService.queryPreview(billParamVo);
	 }


	 /**
	  * 账单生成
	  */
	 @ApiOperation(value = "账单生成")
	 @PostMapping(value = "/createBill")
	 public ApiResponseResult createBill(@RequestBody BillParamVo billParamVo) throws Exception{
		 return payBillsService.createBill(billParamVo);
	 }

}
