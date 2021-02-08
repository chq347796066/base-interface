package com.spring.pay.controller;

import com.spring.base.entity.pay.TransJournalsEntity;
import com.spring.base.vo.pay.transjournals.TodayPramVo;
import com.spring.base.vo.pay.transjournals.TodayResult;
import com.spring.base.vo.pay.transjournals.TransJournalsAddVo;
import com.spring.base.vo.pay.transjournals.TransJournalsUpdateVo;
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
import com.spring.pay.service.ITransJournalsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;


 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-23 09:49:14
 * @Desc类说明: 缴费记录控制器
 */
@RestController
@Api(value = "缴费记录", tags = "缴费记录接口")
@RequestMapping("transjournals")
public class TransJournalsController extends BaseController{
	@Autowired
	private ITransJournalsService transJournalsService;
	
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
	public ApiResponseResult add(@RequestBody @Validated TransJournalsAddVo vo, BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return transJournalsService.addTransJournals(vo);
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
	public ApiResponseResult update(@RequestBody @Validated TransJournalsUpdateVo vo,
			BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return transJournalsService.updateTransJournals(vo);
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
		return transJournalsService.delete(id);
	}

	/**
	 * 根据主键id查询对象
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据主键id查询对象", response = TransJournalsEntity.class)
	@GetMapping(value = "/queryObject")
	public ApiResponseResult queryObject(
			@ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id)
			throws Exception {
		return transJournalsService.queryObject(id);
	}


	/**
	 * 根据条件查询列表
	 * 
	 * @param vo
	 * @return
	 */
	@ApiOperation(value = "根据条件查询列表", response = TransJournalsEntity.class)
	@PostMapping(value = "/queryList")
	public ApiResponseResult queryList(@RequestBody TransJournalsEntity vo) throws Exception {
		return transJournalsService.queryList(vo);
	}

	/**
	 * 根据条件分页查询
	 * 
	 * @param requestPageVO
	 * @return
	 */
	@ApiOperation(value = "根据条件分页查询", response = TransJournalsEntity.class)
	@PostMapping(value = "/queryPage")
	public ApiResponseResult queryPage(@RequestBody RequestPageVO<TransJournalsEntity> requestPageVO) throws Exception {
		return transJournalsService.queryPage(requestPageVO);
	}

	 /**
	  * 查询首页今日收费
	  */
	 @ApiOperation(value = "查询首页今日收费" ,response = TodayResult.class)
	 @PostMapping(value = "/queryTodayFees")
	 public ApiResponseResult queryTodayFees(@RequestBody TodayPramVo todayPramVo) throws Exception {
		 return transJournalsService.queryTodayFees(todayPramVo);
	 }
	
}
