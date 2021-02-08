package com.spring.pay.controller;


import com.spring.base.entity.pay.TranspayJournalsEntity;
import com.spring.base.vo.pay.transpayjournals.TranspayJournalsAddVo;
import com.spring.base.vo.pay.transpayjournals.TranspayJournalsUpdateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.spring.base.controller.BaseController;
import com.spring.common.exception.ValidationException;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;
import com.spring.pay.service.ITranspayJournalsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;


 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-23 09:49:14
 * @Desc类说明: 支付记录控制器
 */
@RestController
@Api(value = "支付记录", tags = "支付记录接口")
@RequestMapping("transpayjournals")
public class TranspayJournalsController extends BaseController{
	@Autowired
	private ITranspayJournalsService transpayJournalsService;
	
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
	public ApiResponseResult add(@RequestBody @Validated TranspayJournalsAddVo vo, BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return transpayJournalsService.addTranspayJournals(vo);
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
	public ApiResponseResult update(@RequestBody @Validated TranspayJournalsUpdateVo vo,
			BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return transpayJournalsService.updateTranspayJournals(vo);
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
		return transpayJournalsService.delete(id);
	}

	/**
	 * 根据主键id查询对象
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据主键id查询对象", response = TranspayJournalsEntity.class)
	@GetMapping(value = "/queryObject")
	public ApiResponseResult queryObject(
			@ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id)
			throws Exception {
		return transpayJournalsService.queryObject(id);
	}


	/**
	 * 根据条件查询列表
	 * 
	 * @param vo
	 * @return
	 */
	@ApiOperation(value = "根据条件查询列表", response = TranspayJournalsEntity.class)
	@PostMapping(value = "/queryList")
	public ApiResponseResult queryList(@RequestBody TranspayJournalsEntity vo) throws Exception {
		return transpayJournalsService.queryList(vo);
	}

	/**
	 * 根据条件分页查询
	 * 
	 * @param requestPageVO
	 * @return
	 */
	@ApiOperation(value = "根据条件分页查询", response = TranspayJournalsEntity.class)
	@PostMapping(value = "/queryPage")
	public ApiResponseResult queryPage(@RequestBody RequestPageVO<TranspayJournalsEntity> requestPageVO) throws Exception {
		return transpayJournalsService.queryPage(requestPageVO);
	}
	
}
