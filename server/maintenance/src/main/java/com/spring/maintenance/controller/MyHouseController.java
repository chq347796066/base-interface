package com.spring.maintenance.controller;

import com.spring.base.controller.BaseController;
import com.spring.base.entity.buiness.MyHouseEntity;
import com.spring.base.vo.buiness.myhouse.MyHouseAddVo;
import com.spring.base.vo.buiness.myhouse.MyHouseUpdateVo;
import com.spring.base.vo.buiness.myhouse.MyHouseVo;

import com.spring.common.annotation.CommunityDataPower;
import com.spring.common.annotation.CommunityPagePower;
import com.spring.common.exception.ValidationException;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;
import com.spring.maintenance.service.IMyHouseService;
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
 * @date : 创建时间：2020-04-17 09:46:08
 * @Desc类说明: 我的房屋信息控制器
 */
@RestController
@Api(value = "我的房屋信息", tags = "我的房屋信息接口")
@RequestMapping("myhouse")
public class MyHouseController extends BaseController{
	@Autowired
	private IMyHouseService myHouseService;
	/**
	 * 新增
	 * @param vo
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "新增")
	@PostMapping(value = "/insert")
	public ApiResponseResult insertMyHouse(@RequestBody @Validated MyHouseAddVo vo, BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return myHouseService.addMyHouse(vo);
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
	public ApiResponseResult update(@RequestBody @Validated MyHouseUpdateVo vo,
			BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return myHouseService.updateMyHouse(vo);
	}

	/**
	 * 删除
	 * @param vo
	 * @return
	 */
	@ApiOperation(value = "删除", notes = "删除")
	@PostMapping(value = "/deleteMyHouse")
	public ApiResponseResult deleteMyHouse(@RequestBody  @Validated MyHouseEntity vo) throws Exception {
		return myHouseService.deleteMyHouse(vo);
	}

	/**
	 * 根据主键id查询对象
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据主键id查询对象", response = MyHouseEntity.class)
	@GetMapping(value = "/queryObject")
	public ApiResponseResult queryObject(
			@ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id)
			throws Exception {
		return myHouseService.queryObject(id);
	}

	/**
	 * 根据条件查询列表
	 * @param vo
	 * @return
	 */
	@CommunityDataPower
	@ApiOperation(value = "根据条件查询列表", response = MyHouseEntity.class)
	@PostMapping(value = "/queryList")
	public ApiResponseResult queryList(@RequestBody MyHouseEntity vo) throws Exception {
		return myHouseService.queryList(vo);
	}

	 /**
	  * 根据条件查询列表
	  * @param vo
	  * @return
	  */
	 @ApiOperation(value = "查询我的租户列表", response = MyHouseEntity.class)
	 @PostMapping(value = "/queryRenterList")
	 public ApiResponseResult queryRenterList(@RequestBody MyHouseVo vo) throws Exception {
		 return myHouseService.queryRenterList(vo);
	 }


	/**
	 * 根据条件分页查询
	 * @param requestPageVO
	 * @return
	 */
	@CommunityPagePower
	@ApiOperation(value = "根据条件分页查询", response = MyHouseEntity.class)
	@PostMapping(value = "/queryPage")
	public ApiResponseResult queryPage(@RequestBody  @Validated  RequestPageVO<MyHouseEntity> requestPageVO) throws Exception {
		return myHouseService.queryPage(requestPageVO);
	}


	 /**
	  * 根据条件系统管理用户管理业主APP用户查询列表
	  * @param vo
	  * @return
	  */
	 @ApiOperation(value = "根据条件系统管理用户管理业主APP用户查询列表", response = MyHouseEntity.class)
	 @PostMapping(value = "/queryUserAppHouseList")
	 public List<MyHouseEntity> queryUserAppHouseList(@RequestBody MyHouseEntity vo) throws Exception {
		 return myHouseService.queryUserAppHouseList(vo);
	 }
}
