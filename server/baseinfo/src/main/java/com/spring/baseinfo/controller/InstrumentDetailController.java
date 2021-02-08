package com.spring.baseinfo.controller;

import com.spring.base.entity.baseinfo.InstrumentDetailEntity;
import com.spring.base.vo.baseinfo.instrumentdetail.InstrumentDetailAddVo;
import com.spring.base.vo.baseinfo.instrumentdetail.InstrumentDetailUpdateVo;
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
import com.spring.baseinfo.service.IInstrumentDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;


 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-03 16:03:23
 * @Desc类说明: 仪明细控制器
 */
@RestController
@Api(value = "仪明细", tags = "仪明细接口")
@RequestMapping("instrumentdetail")
public class InstrumentDetailController extends BaseController{
	@Autowired
	private IInstrumentDetailService instrumentDetailService;
	
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
	public ApiResponseResult add(@RequestBody @Validated InstrumentDetailAddVo vo, BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return instrumentDetailService.addInstrumentDetail(vo);
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
	public ApiResponseResult update(@RequestBody @Validated InstrumentDetailUpdateVo vo,
			BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return instrumentDetailService.updateInstrumentDetail(vo);
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
		return instrumentDetailService.delete(id);
	}

	/**
	 * 根据主键id查询对象
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据主键id查询对象", response = InstrumentDetailEntity.class)
	@GetMapping(value = "/queryObject")
	public ApiResponseResult queryObject(
			@ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id)
			throws Exception {
		return instrumentDetailService.queryObject(id);
	}


	/**
	 * 根据条件查询列表
	 * 
	 * @param vo
	 * @return
	 */
	@ApiOperation(value = "根据条件查询列表", response = InstrumentDetailEntity.class)
	@PostMapping(value = "/queryList")
	public ApiResponseResult queryList(@RequestBody InstrumentDetailEntity vo) throws Exception {
		return instrumentDetailService.queryList(vo);
	}

	/**
	 * 根据条件分页查询
	 * 
	 * @param requestPageVO
	 * @return
	 */
	@ApiOperation(value = "根据条件分页查询", response = InstrumentDetailEntity.class)
	@PostMapping(value = "/queryPage")
	public ApiResponseResult queryPage(@RequestBody RequestPageVO<InstrumentDetailEntity> requestPageVO) throws Exception {
		return instrumentDetailService.queryPage(requestPageVO);
	}
	
}
