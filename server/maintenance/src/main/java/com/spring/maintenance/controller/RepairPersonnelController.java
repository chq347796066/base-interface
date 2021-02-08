package com.spring.maintenance.controller;

import com.spring.base.entity.buiness.RepairPersonnelEntity;
import com.spring.base.vo.buiness.repairpersonnel.RepairPersonnelAddVo;
import com.spring.base.vo.buiness.repairpersonnel.RepairPersonnelUpdateVo;
import com.spring.common.page.RequestPageVO;
import com.spring.maintenance.service.IRepairPersonnelService;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;


 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-11 16:09:13
 * @Desc类说明: 报修派工人员控制器
 */
@RestController
@Api(value = "报修派工人员", tags = "报修派工人员接口")
@RequestMapping("repairpersonnel")
public class RepairPersonnelController extends BaseController{
	@Autowired
	private IRepairPersonnelService repairPersonnelService;
	
	/**
	 * 新增
	 * @param vo
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "新增")
	@PostMapping(value = "/add")
	public ApiResponseResult add(@RequestBody @Validated RepairPersonnelAddVo vo, BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return repairPersonnelService.addRepairPersonnel(vo);
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
	public ApiResponseResult update(@RequestBody @Validated RepairPersonnelUpdateVo vo,
			BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return repairPersonnelService.updateRepairPersonnel(vo);
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
		return repairPersonnelService.delete(id);
	}

	/**
	 * 根据主键id查询对象
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据主键id查询对象", response = RepairPersonnelEntity.class)
	@GetMapping(value = "/queryObject")
	public ApiResponseResult queryObject(
			@ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id)
			throws Exception {
		return repairPersonnelService.queryObject(id);
	}


	/**
	 * 根据条件查询列表
	 * @param vo
	 * @return
	 */
	@ApiOperation(value = "根据条件查询列表", response = RepairPersonnelEntity.class)
	@PostMapping(value = "/queryList")
	public ApiResponseResult queryList(@RequestBody RepairPersonnelEntity vo) throws Exception {
		return repairPersonnelService.queryList(vo);
	}

	/**
	 * 根据条件分页查询
	 * @param requestPageVO
	 * @return
	 */
	@ApiOperation(value = "根据条件分页查询", response = RepairPersonnelEntity.class)
	@PostMapping(value = "/queryPage")
	public ApiResponseResult queryPage(@RequestBody RequestPageVO<RepairPersonnelEntity> requestPageVO) throws Exception {
		return repairPersonnelService.queryPage(requestPageVO);
	}
	
}
