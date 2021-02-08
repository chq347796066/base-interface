package com.spring.business.controller;

import com.spring.base.controller.BaseController;
import com.spring.base.entity.buiness.RepairEntity;
import com.spring.business.dto.HouseInfoDto;
import com.spring.business.dto.RepairDetailDto;
import com.spring.business.service.IRepairService;
import com.spring.business.vo.RepairAddVo;
import com.spring.business.vo.RepairCloseVo;
import com.spring.business.vo.RepairQueryVo;
import com.spring.common.exception.ValidationException;
import com.spring.common.response.ApiResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2021-01-05 14:57:47
 * @Desc类说明: 报事报修控制器
 */
@RestController
@Api(value = "报事报修", tags = {"报事报修接口"})
@RequestMapping("repair")
public class RepairController extends BaseController{

	@Autowired
	private IRepairService repairService;
	
	/**
	 * 创建报修工单
	 * @param vo
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "创建报修工单")
	@PostMapping(value = "/create")
	public ApiResponseResult createRepair(@RequestBody @Validated RepairAddVo vo, BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return repairService.createRepair(vo);
	}

	 /**
	  * 取消报修工单
	  * @param id
	  * @return
	  * @throws Exception
	  */
	 @ApiOperation(value = "取消报修工单")
	 @GetMapping(value = "/cancel")
	 public ApiResponseResult cancelRepair(
	 		@ApiParam(value = "报修id", required = true) @RequestParam(value = "id", required = true) Long id,
			@ApiParam(value = "取消原因", required = true) @RequestParam(value = "remark", required = true) String remark
	 					) throws Exception {
		 return repairService.cancelRepair(id,remark);
	 }

	/**
	 * 根据主键id查询对象
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "查询报修工单详情", response = RepairDetailDto.class)
	@GetMapping(value = "/queryRepairDetail")
	public ApiResponseResult queryRepairDetail(
			@ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id)
			throws Exception {
		return repairService.queryRepairDetail(id);
	}

	/**
	 * 根据条件分页查询
	 * @param repairQueryVo
	 * @return
	 */
	@ApiOperation(value = "查询报修单列表", response = RepairEntity.class)
	@PostMapping(value = "/queryRepairList")
	public ApiResponseResult queryRepairList(@RequestBody @Validated RepairQueryVo repairQueryVo) throws Exception {
		return repairService.queryRepairList(repairQueryVo);
	}

	 /**
	  * 管家关闭报修工单
	  * @param repairCloseVo
	  * @return
	  */
	 @ApiOperation(value = "管家关闭报修工单")
	 @PostMapping(value = "/closeRepair")
	 public ApiResponseResult closeRepair(@RequestBody RepairCloseVo repairCloseVo) throws Exception {
		 return repairService.closeRepair(repairCloseVo);
	 }

	 /**
	  * 查询报修人信息
	  * @return
	  */
	 @ApiOperation(value = "查询报修人信息",response = HouseInfoDto.class)
	 @GetMapping(value = "/queryRepairInfo")
	 public ApiResponseResult queryRepairInfo(
			 @ApiParam(value = "用户id", required = true) @RequestParam(value = "userId", required = true) String userId) throws Exception {
		 return repairService.queryRepairInfo(userId);
	 }
}
