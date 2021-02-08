package com.spring.baseinfo.controller;

import com.spring.base.entity.baseinfo.BuildEntity;
import com.spring.base.vo.baseinfo.build.BuildAddVo;
import com.spring.base.vo.baseinfo.build.BuildUpdateVo;
import com.spring.common.annotation.CommunityPagePower;
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
import com.spring.baseinfo.service.IBuildService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;


 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-31 19:02:26
 * @Desc类说明:  楼栋控制器
 */
@RestController
@Api(value = " 楼栋", tags = " 楼栋接口")
@RequestMapping("build")
public class BuildController extends BaseController{
	@Autowired
	private IBuildService buildService;
	
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
	public ApiResponseResult add(@RequestBody @Validated BuildAddVo vo, BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return buildService.addBuild(vo);
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
	public ApiResponseResult update(@RequestBody @Validated BuildUpdateVo vo,
			BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return buildService.updateBuild(vo);
	}

	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "删除", notes = "删除")
	@GetMapping(value = "/delete")
	public ApiResponseResult delete(
			@ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id)
			throws Exception {
		return buildService.delete(id);
	}

	 /**
	  * 关联删除
	  * @param id
	  * @return
	  */
	 @ApiOperation(value = "关联删除", notes = "关联删除")
	 @GetMapping(value = "/deleteCell")
	 public ApiResponseResult deleteCell(@ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id) throws Exception {
		 return buildService.deleteCell(id);
	 }



	 /**
	 * 根据主键id查询对象
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据主键id查询对象", response = BuildEntity.class)
	@GetMapping(value = "/queryObject")
	public ApiResponseResult queryObject(
			@ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id)
			throws Exception {
		return buildService.queryObject(id);
	}

	 /**
	  * 根据主键id查询对象
	  *
	  * @param id
	  * @return
	  */
	 @ApiOperation(value = "查询楼栋信息", response = BuildEntity.class)
	 @PostMapping(value = "/queryBuildInfo")
	 public BuildEntity queryBuildInfo(@RequestBody BuildEntity vo)
			 throws Exception {
		 return buildService.queryBuildInfo(vo);
	 }


	/**
	 * 根据条件查询列表
	 * 
	 * @param vo
	 * @return
	 */
	@ApiOperation(value = "根据条件查询列表", response = BuildEntity.class)
	@PostMapping(value = "/queryList")
	public ApiResponseResult queryList(@RequestBody BuildEntity vo) throws Exception {
		return buildService.queryList(vo);
	}

	/**
	 * 根据条件分页查询
	 * 
	 * @param requestPageVO
	 * @return
	 */
	@CommunityPagePower
	@ApiOperation(value = "根据条件分页查询", response = BuildEntity.class)
	@PostMapping(value = "/queryPage")
	public ApiResponseResult queryPage(@RequestBody @Validated RequestPageVO<BuildEntity> requestPageVO) throws Exception {
		return buildService.queryBuildPage(requestPageVO);
	}
	
}
