package com.spring.pay.controller;


import com.spring.base.entity.pay.RevokeInfoEntity;
import com.spring.base.vo.pay.revoke.RevokeInfoAddVo;
import com.spring.base.vo.pay.revoke.RevokeInfoUpdateVo;
import com.spring.base.vo.pay.revoke.RevokeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.spring.base.controller.BaseController;
import com.spring.common.exception.ValidationException;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;
import com.spring.pay.service.IRevokeInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;


 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-05-20 10:37:32
 * @Desc类说明: 撤销记录控制器
 */
@RestController
@Api(value = "撤销记录", tags = "撤销记录接口")
@RequestMapping("revokeinfo")
public class RevokeInfoController extends BaseController{
	@Autowired
	private IRevokeInfoService revokeInfoService;
	
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
	public ApiResponseResult add(@RequestBody @Validated RevokeInfoAddVo vo, BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return revokeInfoService.addRevokeInfo(vo);
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
	public ApiResponseResult update(@RequestBody @Validated RevokeInfoUpdateVo vo,
			BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return revokeInfoService.updateRevokeInfo(vo);
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
		return revokeInfoService.delete(id);
	}

	/**
	 * 根据主键id查询对象
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据主键id查询对象", response = RevokeInfoEntity.class)
	@GetMapping(value = "/queryObject")
	public ApiResponseResult queryObject(
			@ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id)
			throws Exception {
		return revokeInfoService.queryObject(id);
	}


	/**
	 * 根据条件查询列表
	 * 
	 * @param vo
	 * @return
	 */
	@ApiOperation(value = "根据条件查询列表", response = RevokeInfoEntity.class)
	@PostMapping(value = "/queryList")
	public ApiResponseResult queryList(@RequestBody RevokeInfoEntity vo) throws Exception {
		return revokeInfoService.queryList(vo);
	}

	/**
	 * 根据条件分页查询
	 * 
	 * @param requestPageVO
	 * @return
	 */
	@ApiOperation(value = "根据条件分页查询", response = RevokeInfoEntity.class)
	@PostMapping(value = "/queryPage")
	public ApiResponseResult queryPage(@RequestBody RequestPageVO<RevokeInfoEntity> requestPageVO) throws Exception {
		return revokeInfoService.queryPage(requestPageVO);
	}


	 /**
	  * 撤销收费
	  *
	  * @return
	  * @throws Exception
	  */
	 @ApiOperation(value = "撤销收费")
	 @PostMapping(value = "/payRevoke")
	 public ApiResponseResult payRevoke(@RequestBody RevokeVo revokeVo) throws Exception {
		 return revokeInfoService.payRevoke(revokeVo);
	 }

	
}
