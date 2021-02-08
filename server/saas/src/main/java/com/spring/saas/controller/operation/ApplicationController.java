package com.spring.saas.controller.operation;


import com.spring.base.controller.BaseController;
import com.spring.base.entity.saas.ApplicationEntity;
import com.spring.base.vo.saas.ApplicationAddVo;
import com.spring.base.vo.saas.ApplicationUpdateVo;
import com.spring.common.exception.ValidationException;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;
import com.spring.saas.service.IApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-01 14:52:37
 * @Desc类说明: 应用控制器
 */
@RestController
@Api(value = "应用", tags= {"应用接口"})
@RequestMapping("application")
public class ApplicationController extends BaseController{

	@Autowired
	private IApplicationService applicationService;
	
	/**
	 * 新增
	 * @param vo
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "创建应用")
	@PostMapping(value = "/add")
	public ApiResponseResult add(@RequestBody @Validated ApplicationAddVo vo, BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return applicationService.addApplication(vo);
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "删除应用", notes = "删除应用")
	@GetMapping(value = "/delete")
	public ApiResponseResult delete(
			@ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id)
			throws Exception {

		return applicationService.deleteApp(id);
	}

	/**
	 * 根据主键id查询对象
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据主键id查询应用详情", response = ApplicationEntity.class)
	@GetMapping(value = "/queryObject")
	public ApiResponseResult queryObject(
			@ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id)
			throws Exception {
		return applicationService.queryObject(id);
	}

	 /**
	  * 更新
	  * @param vo
	  * @param result
	  * @return
	  * @throws Exception
	  */
	 @ApiOperation(value = "更新应用信息")
	 @PostMapping(value = "/update")
	 public ApiResponseResult update(@RequestBody @Validated ApplicationUpdateVo vo,
									 BindingResult result) throws Exception {
		 if (result != null && result.hasErrors()) {
			 throw new ValidationException(result);
		 }
		 return applicationService.updateApplication(vo);
	 }

	/**
	 * 根据条件查询列表
	 * @param vo
	 * @return
	 */
	@ApiOperation(value = "根据条件查询列表(不分页)", response = ApplicationEntity.class)
	@PostMapping(value = "/queryList")
	public ApiResponseResult queryList(@RequestBody ApplicationEntity vo) throws Exception {
		return applicationService.queryList(vo);
	}

	/**
	 * 根据条件分页查询
	 * @param requestPageVO
	 * @return
	 */
	@ApiOperation(value = "根据条件分页查询", response = ApplicationEntity.class)
	@PostMapping(value = "/queryPage")
	public ApiResponseResult queryPage(@RequestBody RequestPageVO<ApplicationEntity> requestPageVO) throws Exception {
		return applicationService.queryPage(requestPageVO);
	}
	
}
