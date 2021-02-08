package com.spring.saas.controller.operation;

import com.spring.base.controller.BaseController;
import com.spring.base.entity.saas.CompanyScaleEntity;
import com.spring.base.vo.saas.CompanyScaleAddVo;
import com.spring.base.vo.saas.CompanyScaleUpdateVo;
import com.spring.common.exception.ValidationException;
import com.spring.common.response.ApiResponseResult;
import com.spring.saas.service.ICompanyScaleService;
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
 * @date : 创建时间：2020-07-02 15:17:12
 * @Desc类说明: 公司规模控制器
 */
@RestController
@Api(value = "公司规模", tags = "公司规模接口")
@RequestMapping("companyScale")
public class CompanyScaleController extends BaseController{

	@Autowired
	private ICompanyScaleService companyScaleService;
	
	/**
	 * 新增
	 * @param vo
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "新增")
	@PostMapping(value = "/add")
	public ApiResponseResult add(@RequestBody @Validated CompanyScaleAddVo vo, BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return companyScaleService.addCompanyScale(vo);
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
	public ApiResponseResult update(@RequestBody @Validated CompanyScaleUpdateVo vo,
			BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return companyScaleService.updateCompanyScale(vo);
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
//		return companyScaleService.delete(id);
//	}

	/**
	 * 根据条件查询列表
	 * @param vo
	 * @return
	 */
	@ApiOperation(value = "根据条件查询列表", response = CompanyScaleEntity.class)
	@PostMapping(value = "/queryList")
	public ApiResponseResult queryList(@RequestBody CompanyScaleEntity vo) throws Exception {
		return companyScaleService.queryList(vo);
	}

}
