package com.spring.saas.controller.operation;

import com.spring.base.controller.BaseController;
import com.spring.base.entity.saas.IndustryEntity;
import com.spring.base.vo.saas.IndustryAddVo;
import com.spring.base.vo.saas.IndustryUpdateVo;
import com.spring.common.exception.ValidationException;
import com.spring.common.response.ApiResponseResult;
import com.spring.saas.service.IIndustryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-02 15:17:11
 * @Desc类说明: 行业控制器
 */
@RestController
@Api(value = "行业", tags = "行业接口")
@RequestMapping("industry")
public class IndustryController extends BaseController{

	@Autowired
	private IIndustryService industryService;
	
	/**
	 * 新增
	 * @param vo
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "新增")
	@PostMapping(value = "/add")
	public ApiResponseResult add(@RequestBody @Validated IndustryAddVo vo, BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return industryService.addIndustry(vo);
	}

	/**
	 * 更新
	 * @param vo
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "更新")
	@PostMapping(value = "/update")
	public ApiResponseResult update(@RequestBody @Validated IndustryUpdateVo vo,
			BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return industryService.updateIndustry(vo);
	}

//	/**
//	 * 删除
//	 * @param id
//	 * @return
//	 */
//	@ApiOperation(value = "删除", notes = "删除")
//	@GetMapping(value = "/delete")
//	public ApiResponseResult delete(
//			@ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id)
//			throws Exception {
//		return industryService.delete(id);
//	}

	/**
	 * 根据条件查询列表
	 * @param vo
	 * @return
	 */
	@ApiOperation(value = "根据条件查询列表", response = IndustryEntity.class)
	@PostMapping(value = "/queryList")
	public ApiResponseResult queryList(@RequestBody IndustryEntity vo) throws Exception {
		return industryService.queryList(vo);
	}
}
