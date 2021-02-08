package com.spring.saas.controller.operation;

import java.util.List;

import com.spring.base.entity.saas.UserEntity;
import com.spring.base.vo.baseinfo.user.LoginReturnVo;
import com.spring.base.vo.baseinfo.user.LoginVo;
import com.spring.base.vo.saas.UserAddVo;
import com.spring.base.vo.saas.UserUpdateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.spring.base.controller.BaseController;
import com.spring.common.exception.ValidationException;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;
import com.spring.saas.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;


 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-10 10:09:25
 * @Desc类说明: 运营用户列控制器
 */
@RestController
@Api(value = "运营用户列", tags = {"运营用户列接口"})
@RequestMapping("operationUser")
public class UserController extends BaseController{

	@Autowired
	private IUserService userService;
	
	/**
	 * 新增
	 * @param vo
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "新增")
	@PostMapping(value = "/add")
	public ApiResponseResult add(@RequestBody @Validated UserAddVo vo, BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return userService.addUser(vo);
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
	public ApiResponseResult update(@RequestBody @Validated UserUpdateVo vo,
			BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return userService.updateUser(vo);
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
		return userService.delete(id);
	}

	/**
	 * 根据主键id查询对象
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据主键id查询对象", response = UserEntity.class)
	@GetMapping(value = "/queryObject")
	public ApiResponseResult queryObject(
			@ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id)
			throws Exception {
		return userService.queryObject(id);
	}


	/**
	 * 根据条件查询列表
	 * @param vo
	 * @return
	 */
	@ApiOperation(value = "根据条件查询列表", response = UserEntity.class)
	@PostMapping(value = "/queryList")
	public ApiResponseResult queryList(@RequestBody UserEntity vo) throws Exception {
		return userService.queryList(vo);
	}

	/**
	 * 根据条件分页查询
	 * @param requestPageVO
	 * @return
	 */
	@ApiOperation(value = "根据条件分页查询", response = UserEntity.class)
	@PostMapping(value = "/queryPage")
	public ApiResponseResult queryPage(@RequestBody RequestPageVO<UserEntity> requestPageVO) throws Exception {
		return userService.queryPage(requestPageVO);
	}

	 /**
	  * 用户登录
	  *
	  * @param vo
	  * @return
	  * @throws Exception
	  * @author 作者：赵进华
	  * @version 创建时间：2019年5月21日 下午2:52:35
	  */
	 @ApiOperation(value = "用户登录", response = LoginReturnVo.class)
	 @PostMapping(value = "/userLogin")
	 public ApiResponseResult userLogin(@RequestBody LoginVo vo) throws Exception {
		 return userService.userLogin(vo);
	 }
}
