package com.spring.baseinfo.controller;


import com.spring.base.controller.BaseController;
import com.spring.base.entity.baseinfo.InstrumentTypeEntity;
import com.spring.base.vo.baseinfo.instrumenttype.InstrumentTypeAddVo;
import com.spring.base.vo.baseinfo.instrumenttype.InstrumentTypeUpdateVo;
import com.spring.baseinfo.service.IInstrumentTypeService;
import com.spring.common.exception.ValidationException;
import com.spring.common.page.RequestPageVO;
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
 * @date : 创建时间：2020-04-02 17:17:10
 * @Desc类说明: 仪类型控制器
 */
@RestController
@Api(value = "仪类型", tags = "仪类型接口")
@RequestMapping("instrumenttype")
public class InstrumentTypeController extends BaseController{
	@Autowired
	private IInstrumentTypeService instrumentTypeService;
	
	/**
	 * 新增
	 * @param vo
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "新增")
	@PostMapping(value = "/add")
	public ApiResponseResult add(@RequestBody @Validated InstrumentTypeAddVo vo, BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return instrumentTypeService.addInstrumentType(vo);
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
	public ApiResponseResult update(@RequestBody @Validated InstrumentTypeUpdateVo vo,
			BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return instrumentTypeService.updateInstrumentType(vo);
	}

	 /**
	  * 关联删除
	  * @param id
	  * @return
	  */
	 @ApiOperation(value = "删除", notes = "关联删除")
	 @GetMapping(value = "/delete")
	 public ApiResponseResult deleteHouseinstrument(@ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) Long id) throws Exception {
		 return instrumentTypeService.deleteHouseinstrument(id);
	 }

	 /**
	 * 根据主键id查询对象
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据主键id查询对象", response = InstrumentTypeEntity.class)
	@GetMapping(value = "/queryObject")
	public ApiResponseResult queryObject(
			@ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id)
			throws Exception {
		return instrumentTypeService.queryObject(id);
	}


	/**
	 * 根据条件查询列表
	 * @param vo
	 * @return
	 */
	@ApiOperation(value = "根据条件查询列表", response = InstrumentTypeEntity.class)
	@PostMapping(value = "/queryList")
	public ApiResponseResult queryList(@RequestBody InstrumentTypeEntity vo) throws Exception {
		return instrumentTypeService.queryList(vo);
	}

	/**
	 * 根据条件分页查询
	 * @param requestPageVO
	 * @return
	 */
	@ApiOperation(value = "根据条件分页查询", response = InstrumentTypeEntity.class)
	@PostMapping(value = "/queryPage")
	public ApiResponseResult queryPage(@RequestBody RequestPageVO<InstrumentTypeEntity> requestPageVO) throws Exception {
		return instrumentTypeService.queryPage(requestPageVO);
	}

	/**
	 * @Desc:仪表导出
	 * @param voEntity
	 * @Author:邓磊
	 * @UpdateDate:2020/6/2 13:52
	 * @return: 返回
	 */
	@ApiOperation(value = "导出仪表类型管理信息", response = InstrumentTypeEntity.class)
	@GetMapping(value = "/exportInstrumentTypeEntityInfo")
	public void  exportInstrumentTypeEntityInfo(InstrumentTypeEntity voEntity) throws Exception {
		instrumentTypeService.exportInstrumentTypeEntityInfo(voEntity);
	}

}
