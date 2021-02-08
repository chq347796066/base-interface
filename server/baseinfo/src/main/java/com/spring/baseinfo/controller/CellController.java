package com.spring.baseinfo.controller;

import com.spring.base.entity.baseinfo.CellEntity;
import com.spring.base.vo.baseinfo.cell.CellAddVo;
import com.spring.base.vo.baseinfo.cell.CellUpdateVo;
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
import com.spring.baseinfo.service.ICellService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;


 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-31 19:02:26
 * @Desc类说明: 单元信息控制器
 */
@RestController
@Api(value = "单元信息", tags = "单元信息接口")
@RequestMapping("cell")
public class CellController extends BaseController{
	@Autowired
	private ICellService cellService;
	
	/**
	 * 新增
	 * @param vo
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "新增")
	@PostMapping(value = "/add")
	public ApiResponseResult add(@RequestBody @Validated CellAddVo vo, BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return cellService.addCell(vo);
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
	public ApiResponseResult update(@RequestBody @Validated CellUpdateVo vo,
			BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return cellService.updateCell(vo);
	}


	 /**
	  * 关联删除
	  * @param id
	  * @return
	  */
	 @ApiOperation(value = "关联删除", notes = "关联删除")
	 @GetMapping(value = "/deleteHouse")
	 public ApiResponseResult deleteHouse(@ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id) throws Exception {
		 return cellService.deleteHouse(id);
	 }



	 /**
	 * 根据主键id查询对象
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据主键id查询对象", response = CellEntity.class)
	@GetMapping(value = "/queryObject")
	public ApiResponseResult queryObject(
			@ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id)
			throws Exception {
		return cellService.queryObject(id);
	}


	/**
	 * 根据条件查询列表
	 * @param vo
	 * @return
	 */
	@ApiOperation(value = "根据条件查询列表", response = CellEntity.class)
	@PostMapping(value = "/queryList")
	public ApiResponseResult queryList(@RequestBody CellEntity vo) throws Exception {
		return cellService.queryList(vo);
	}

	/**
	 * 根据条件分页查询
	 * @param requestPageVO
	 * @return
	 */
	@CommunityPagePower
	@ApiOperation(value = "根据条件分页查询", response = CellEntity.class)
	@PostMapping(value = "/queryPage")
	public ApiResponseResult queryPage(@RequestBody @Validated RequestPageVO<CellEntity> requestPageVO) throws Exception {
		return cellService.queryCellPage(requestPageVO);
	}
	
}
