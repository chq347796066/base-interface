package com.spring.baseinfo.controller;

import com.spring.base.controller.BaseController;
import com.spring.base.entity.baseinfo.PicEntity;
import com.spring.base.vo.baseinfo.pic.PicAddVo;
import com.spring.base.vo.baseinfo.pic.PicUpdateVo;
import com.spring.baseinfo.service.IPicService;
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

import java.util.List;


 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-14 15:31:38
 * @Desc类说明: 图片中间控制器
 */
@RestController
@Api(value = "图片中间", tags = "图片中间接口")
@RequestMapping("pic")
public class PicController extends BaseController{
	@Autowired
	private IPicService picService;
	
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
	public ApiResponseResult add(@RequestBody @Validated PicAddVo vo, BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return picService.addPic(vo);
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
	public ApiResponseResult update(@RequestBody @Validated PicUpdateVo vo,
			BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return picService.updatePic(vo);
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
		return picService.delete(id);
	}

	/**
	 * 根据主键id查询对象
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据主键id查询对象", response = PicEntity.class)
	@GetMapping(value = "/queryObject")
	public ApiResponseResult queryObject(
			@ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id)
			throws Exception {
		return picService.queryObject(id);
	}


	/**
	 * 根据条件查询列表
	 * 
	 * @param vo
	 * @return
	 */
	@ApiOperation(value = "根据条件查询列表", response = PicEntity.class)
	@PostMapping(value = "/queryList")
	public ApiResponseResult queryList(@RequestBody PicEntity vo) throws Exception {
		return picService.queryList(vo);
	}

	/**
	 * 根据条件分页查询
	 * 
	 * @param requestPageVO
	 * @return
	 */
	@ApiOperation(value = "根据条件分页查询", response = PicEntity.class)
	@PostMapping(value = "/queryPage")
	public ApiResponseResult queryPage(@RequestBody RequestPageVO<PicEntity> requestPageVO) throws Exception {
		return picService.queryPage(requestPageVO);
	}


	 /**
	  * 批量新增图片 maintenance模块调用
	  */
	 @ApiOperation(value = "批量新增图片Feing调用")
	 @PostMapping(value = "/addPicList")
	 public ApiResponseResult addPicList(@RequestBody List<PicEntity> vo) throws Exception {
		 return picService.addPicList(vo);
	 }

	 /**
	  * 删除图片
	  */
	 @ApiOperation(value = "删除图片Feing调用")
	 @PostMapping(value = "/deletePicDelFlag")
	 public ApiResponseResult deletePicDelFlag(@RequestBody PicEntity vo)throws Exception {
		 return picService.deletePicDelFlag(vo);
	 }

	 /**
	  * 查看图片List
	  */
	 @ApiOperation(value = "查看图片Feing调用")
	 @PostMapping(value = "/queryPicEntityVoList")
	 public ApiResponseResult queryPicEntityVoList(@RequestBody PicEntity vo)throws Exception {
		 return picService.queryPicEntityVoList(vo);
	 }

 }
