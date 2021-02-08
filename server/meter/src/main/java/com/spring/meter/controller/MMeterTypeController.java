package com.spring.meter.controller;

import com.spring.base.controller.BaseController;
import com.spring.base.entity.meter.MMeterTypeEntity;
import com.spring.base.entity.pay.TransJournalsEntity;
import com.spring.base.vo.meter.MMeterTypeAddVo;
import com.spring.base.vo.meter.MMeterTypeUpdateVo;
import com.spring.base.vo.meter.MeterIds;
import com.spring.base.vo.pay.queryrecord.QueryRecordVo;
import com.spring.common.exception.ValidationException;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;
import com.spring.meter.service.IMMeterTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-10-28 14:30:45
 * @Desc类说明: 仪类型控制器
 */
@RestController
@Api(value = "仪表类型", tags = {"仪表类型接口"})
@RequestMapping("mmetertype")
public class MMeterTypeController extends BaseController{

	@Autowired
	private IMMeterTypeService mMeterTypeService;
	
	/**
	 * 新增
	 * @param vo
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "新增")
	@PostMapping(value = "/add")
	public ApiResponseResult add(@RequestBody @Validated MMeterTypeAddVo vo, BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return mMeterTypeService.addMMeterType(vo);
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
	public ApiResponseResult update(@RequestBody @Validated MMeterTypeUpdateVo vo,
			BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return mMeterTypeService.updateMMeterType(vo);
	}

	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "单个删除", notes = "单个删除")
	@GetMapping(value = "/singelDeleteById")
	public ApiResponseResult singelDeleteById(
			@ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) Long id)
			throws Exception {
		return mMeterTypeService.singelDelete(id);
	}

	 /**
	  * 批量删除
	  * @param id
	  * @return
	  */
	 @ApiOperation(value = "批量删除", notes = "批量删除")
	 @PostMapping(value = "/batchDeleteById")
	 public ApiResponseResult batchDeleteById(@RequestBody MeterIds ids)
			 throws Exception {
		 return mMeterTypeService.batchDeleteById(ids);
	 }

	/**
	 * 根据主键id查询对象
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据主键id查询对象", response = MMeterTypeEntity.class)
	@GetMapping(value = "/queryObject")
	public ApiResponseResult queryObject(
			@ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id)
			throws Exception {
		return mMeterTypeService.queryObject(id);
	}


	/**
	 * 根据条件查询列表
	 * @param vo
	 * @return
	 */
	@ApiOperation(value = "根据条件查询列表", response = MMeterTypeEntity.class)
	@PostMapping(value = "/queryList")
	public ApiResponseResult queryList(@RequestBody MMeterTypeEntity vo) throws Exception {
		return mMeterTypeService.queryList(vo);
	}

	/**
	 * 根据条件分页查询
	 * @param requestPageVO
	 * @return
	 */
	@ApiOperation(value = "根据条件分页查询", response = MMeterTypeEntity.class)
	@PostMapping(value = "/queryMeterTypePage")
	public ApiResponseResult queryMeterTypePage(@RequestBody RequestPageVO<MMeterTypeEntity> requestPageVO) throws Exception {
		return mMeterTypeService.queryMeterTypePage(requestPageVO);
	}

	/**
	 * @Desc:仪表类型记录导出
	 * @param vo
	 * @Author:宋雄
	 * @UpdateDate:2020/5/20 13:45
	 * @return: 返回
	 */
	@ApiOperation(value = "仪表类型记录导出", response = MMeterTypeEntity.class)
	@PostMapping(value = "/exportMeterTypeInfo")
	public void  exportRecordInfo(@RequestBody MMeterTypeEntity vo) throws Exception {
		mMeterTypeService.exportMeterTypeInfo(vo);
	}



}
