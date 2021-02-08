package com.spring.pay.controller;

import com.spring.base.controller.BaseController;
import com.spring.base.entity.pay.BillDetailsEntity;
import com.spring.base.entity.pay.PayBillsEntity;
import com.spring.base.vo.pay.billdetail.BillDetailParamVo;
import com.spring.base.vo.pay.billdetail.BillDetailsAddVo;
import com.spring.base.vo.pay.billdetail.BillDetailsUpdateVo;
import com.spring.base.vo.pay.billdetail.BillDetailsVo;
import com.spring.common.exception.ValidationException;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;
import com.spring.pay.service.IBillDetailsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-23 09:49:13
 * @Desc类说明: 账单详情控制器
 */
@RestController
@Api(value = "账单详情", tags = "账单详情接口")
@RequestMapping("billdetails")
public class BillDetailsController extends BaseController{
	@Autowired
	private IBillDetailsService billDetailsService;
	
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
	public ApiResponseResult add(@RequestBody @Validated BillDetailsAddVo vo, BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return billDetailsService.addBillDetails(vo);
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
	public ApiResponseResult update(@RequestBody @Validated BillDetailsUpdateVo vo,
			BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return billDetailsService.updateBillDetails(vo);
	}

	/**
	 * 删除
	 * @param vo
	 * @return
	 */
	@ApiOperation(value = "已出账单删除", notes = "已出账单删除")
	@PostMapping(value = "/delete")
	public ApiResponseResult delete(@RequestBody BillDetailsVo vo) throws Exception {
		return billDetailsService.delete(vo);
	}

	/**
	 * 根据主键id查询对象
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据主键id查询对象", response = BillDetailsEntity.class)
	@GetMapping(value = "/queryObject")
	public ApiResponseResult queryObject(
			@ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id)
			throws Exception {
		return billDetailsService.queryObject(id);
	}


	/**
	 * 根据条件查询列表
	 * 
	 * @param vo
	 * @return
	 */
	@ApiOperation(value = "根据条件查询列表", response = BillDetailsEntity.class)
	@PostMapping(value = "/queryList")
	public ApiResponseResult queryList(@RequestBody BillDetailsEntity vo) throws Exception {
		return billDetailsService.queryList(vo);
	}

	/**
	 * 根据条件分页查询查询所有账单
	 * 
	 * @param requestPageVO
	 * @return
	 */
	@ApiOperation(value = "查询所有账单（多对一）", response = BillDetailsEntity.class)
	@PostMapping(value = "/queryBillDetail")
	public ApiResponseResult queryPage(@RequestBody RequestPageVO<BillDetailParamVo> requestPageVO) throws Exception {
		return billDetailsService.queryAllBill(requestPageVO);
	}

	 /**
	  * 根据条件分页查询查询所有账单
	  *
	  * @param requestPageVO
	  * @return
	  */
	 @ApiOperation(value = "查询所有账单（一对多）", response = BillDetailsEntity.class)
	 @PostMapping(value = "/queryBillDetailForOne")
	 public ApiResponseResult queryPageForOne(@RequestBody RequestPageVO<BillDetailParamVo> requestPageVO) throws Exception {
		 return billDetailsService.queryAllBillForOne(requestPageVO);
	 }


	 /**
	  * 根据条件分页查询查询所有欠费账单
	  *
	  * @param requestPageVO
	  * @return
	  */
	 @ApiOperation(value = "查询所有欠费账单（多对一）", response = BillDetailsEntity.class)
	 @PostMapping(value = "/queryDebtBillPage")
	 public ApiResponseResult queryDebtBillPage(@RequestBody BillDetailParamVo requestPageVO) throws Exception {
		 return billDetailsService.queryDebtBillPage(requestPageVO);
	 }

     /**
      * 根据条件分页查询查询所有欠费账单
      *
      * @param requestPageVO
      * @return
      */
     @ApiOperation(value = "查询所有欠费账单（一对多）", response = PayBillsEntity.class)
     @PostMapping(value = "/queryDebtBillPageForOne")
     public ApiResponseResult queryDebtBillPageForOne(@RequestBody BillDetailParamVo requestPageVO) throws Exception {
         return billDetailsService.queryDebtBillPageForOne(requestPageVO);
     }

     /**
      * @Desc:已出账单导出
      * @param billDetailParamVo
      * @Author:邓磊
      * @UpdateDate:2020/5/20 15:58
      * @return: 返回
      */
	 @ApiOperation(value = "已出账单导出", response = BillDetailParamVo.class)
	 @GetMapping(value = "/exportBillDetailParamInfo")
	 public void  exportBillDetailParamInfo(BillDetailParamVo billDetailParamVo)throws Exception {
		 billDetailsService.exportBillDetailParamInfo(billDetailParamVo);
	 }

}
