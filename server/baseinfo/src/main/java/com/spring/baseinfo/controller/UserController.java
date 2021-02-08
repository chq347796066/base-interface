package com.spring.baseinfo.controller;

import com.spring.base.controller.BaseController;
import com.spring.base.entity.baseinfo.UserEntity;
import com.spring.base.entity.baseinfo.UserRoleEntity;
import com.spring.base.entity.buiness.MyHouseEntity;
import com.spring.base.vo.baseinfo.user.*;
import com.spring.base.vo.buiness.myhouse.MyHouseUpdateVo;
import com.spring.baseinfo.service.ICompanyService;
import com.spring.baseinfo.service.IExcelService;
import com.spring.baseinfo.service.IUserService;
import com.spring.baseinfo.vo.UserJobVo;
import com.spring.common.annotation.CommunityPagePower;
import com.spring.common.annotation.TenantPagePower;
import com.spring.common.constants.MessageCode;
import com.spring.common.util.excel.EasyExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.spring.common.exception.ValidationException;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-31 19:02:26
 * @Desc类说明: 用户信息控制器
 */
@Slf4j
@RestController
@Api(value = "用户信息", tags = "用户信息接口")
@RequestMapping("user")
public class UserController extends BaseController{
	@Autowired
	private IUserService userService;

	@Autowired
	private IExcelService excelService;

	@Autowired
	private ICompanyService companyService;

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
	public ApiResponseResult add(@RequestBody @Validated UserAddVo vo, BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return userService.addUser(vo);
	}

	/**
	 * 修改角色
	 * @param vo
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "修改角色")
	@PostMapping(value = "/updateUserRole")
	public ApiResponseResult updateUserRole(@RequestBody @Validated UpadateUserRoleVo vo, BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return userService.updateUserRole(vo);
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
	public ApiResponseResult deleteUser(
			@ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id)
			throws Exception {
		return userService.deleteUser(id);
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
		return userService.queryUserById(id);
	}


	/**
	 * 根据条件查询列表
	 * 
	 * @param vo
	 * @return
	 */
	@ApiOperation(value = "根据条件查询列表", response = UserEntity.class)
	@PostMapping(value = "/queryList")
	public ApiResponseResult queryList(@RequestBody UserEntity vo) throws Exception {
		return userService.queryList(vo);
	}

	/**
	 * 根据条件角色用户分页查询
	 * @param requestPageVO
	 * @return
	 */
	@TenantPagePower
	@ApiOperation(value = "根据条件分页查询", response = UserEntityResponseVO.class)
	@PostMapping(value = "/queryPage")
	public ApiResponseResult queryPage(@RequestBody @Validated RequestPageVO<UserEntity> requestPageVO) throws Exception {
	    log.info("管理平台用户查询queryPage:"+requestPageVO.getEntity().toString());
		return userService.queryRoleUserPage(requestPageVO);
	}


	 /**
	  * @description:导入数据
	  * @return:
	  * @author: 赵进华
	  * @time: 2020/4/2 17:45
	  */
	 @ApiOperation(value = "导入数据")
	 @PostMapping(value = "/importExcel")

	 public ApiResponseResult importExcel(HttpServletRequest req, @RequestParam MultipartFile file) throws Exception {
		 ApiResponseResult result = new ApiResponseResult();
		 if (file == null || file.isEmpty()) {
			 result.setCode(MessageCode.FAIL);
			 result.setMsg("上传文件为空！");
			 return result;
		 }
		 userService.importExcel(req, file);
		 result.setCode(MessageCode.SUCCESS);
		 result.setMsg(MessageCode.SUCCESS_TEXT);
		 return result;
	 }

	 /**
	  * @description:app注册
	  * @return:
	  * @author: 赵进华
	  * @time: 2020/4/9 16:10
	  */
	@ApiOperation(value = "app注册")
	@PostMapping(value = "/appRegister")
	public ApiResponseResult appRegister(@RequestBody @Validated LoginVo vo, BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return userService.appRegister(vo);
	}

	/**
	 * @description:修改用户密码(返回true和false)
	 * @return:
	 * @author: 赵进华
	 * @time: 2020/4/9 16:43
	 */
	@ApiOperation(value = "修改用户密码(返回true和false)")
	@PostMapping(value = "/modifyPassword")
	public ApiResponseResult modifyPassword(@RequestBody LoginVo vo) throws Exception {
		return userService.modifyPassword(vo);
	}

	/**
	 * @description:忘记用户密码(返回true和false)
	 * @return:
	 * @author: 赵进华
	 * @time: 2020/4/9 16:43
	 */
	@ApiOperation(value = "忘记用户密码(返回true和false)")
	@PostMapping(value = "/forgetPassword")
	public ApiResponseResult forgetPassword(@RequestBody LoginVo vo) throws Exception {
		return userService.forgetPassword(vo);
	}


	/**
	 * @description:修改账号启用或禁用
	 * @return:
	 * @author: 吕文祥
	 * @time: 2020/4/9 16:43
	 */
	@ApiOperation(value = "修改账号启用或禁用")
	@PostMapping(value = "/modifyAccountStatus")
	public ApiResponseResult modifyAccountStatus(@RequestBody UserAccountStatusVo vo) throws Exception {
		return userService.modifyAccountStatus(vo);
	}


    /**
     * @description:重置密码
     * @return:
     * @author: 吕文祥
     * @time: 2020/4/9 16:43
     */
    @ApiOperation(value = "重置密码")
	@GetMapping("/resetPassword")
    public ApiResponseResult resetPassword(	@ApiParam(value = "用户ID", required = true) @RequestParam(value = "id", required = true) String id) throws Exception {

        return userService.resetPassword(id);
    }

	/**
	 * @description:异步导出Excel
	 * @return:
	 * @author: 吕文祥
	 * @time: 2020/3/28 22:00
	 */
	@ApiOperation(value = "异步导出员工Excel", httpMethod = "POST", response = Object.class)
	@RequestMapping(value = "/exportUserExcelAsync", method = RequestMethod.POST)
	@ResponseBody
	public ApiResponseResult exportUserExcelAsync(@RequestBody UserEntity vo)
			throws Exception {
		return excelService.exportUserExcelAsync(vo);
	}


    /**
     * 根据条件分页查询业主app用户
     * @param requestPageVO
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "根据条件分页查询业主app用户", response = UserEntity.class)
    @PostMapping(value = "/queryCustomerAppUserPage")
    public ApiResponseResult queryCustomerAppUserPage(@RequestBody RequestPageVO<CustomerAppUserPageVo> requestPageVO) throws Exception {
        return userService.queryCustomerAppUserPage(requestPageVO);
    }
    /**
     * 查看业主app用户详情
     * @param userId
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "查看业主app用户详情", response = UserEntityResponseVO.class)
	@GetMapping("/queryCustomerAppUserDetail")
    public ApiResponseResult queryCustomerAppUserDetail(@ApiParam(value = "用户ID", required = true) @RequestParam(value = "userId", required = true) String userId) throws Exception {
        return userService.queryCustomerAppUserDetail(userId);
    }

	/**
	 * 查询我的房产信息列表
	 * @param myHouseEntity
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "查询我的房产信息列表", response = UserEntity.class)
	@PostMapping(value = "/queryMyHouseInfoList")
	public ApiResponseResult queryMyHouseInfoList(@RequestBody MyHouseEntity myHouseEntity) throws Exception {
		return userService.queryMyHouseInfoList(myHouseEntity);
	}

	/**
	 *
	 * @Title: impUser
	 * @Description: excle导入
	 * @param file
	 * @return String
	 */
	@ApiOperation(value = "导入用户示例", httpMethod = "POST", response = Object.class)
	@PostMapping("/impUser")
	public String impUser(MultipartFile file){
		List<User> users = EasyExcelUtils.importData(file, 1, User.class);
		return "success";
	}


	/**
	 * 查询APP用户绑定房产审核分页
	 * @param requestPageVO
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "查询APP用户绑定房产审核分页", response = MyHouseEntity.class)
	@PostMapping(value = "/queryAppUserBindHouseAuditPage")
	public ApiResponseResult queryAppUserBindHouseAuditPage(@RequestBody RequestPageVO<MyHouseEntity> requestPageVO) throws Exception {
		return userService.queryAppUserBindHouseAuditPage(requestPageVO);
	}

	/**
	 * 查看APP用户绑定房产审核
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "查看APP用户绑定房产审核", response = MyHouseEntity.class)
	@GetMapping(value = "/queryAppUserBindHouseAudit")
	public ApiResponseResult queryAppUserBindHouseAudit(@ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id) throws Exception {
		return userService.queryAppUserBindHouseAudit(id);
	}

	/**
	 * 同意或拒绝房屋APP用户绑定房产认证
	 * @param vo
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "同意或拒绝房屋APP用户绑定房产认证", response = MyHouseEntity.class)
	@PostMapping(value = "/approveHouseAuthentication")
	public ApiResponseResult approveHouseAuthentication(@RequestBody @Validated MyHouseUpdateVo vo) throws Exception {
		return userService.approveHouseAuthentication(vo);

	}

	/**
	 * @Desc: 更新个人信息加用户头像
	 * @param vo
	 * @Author:邓磊
	 * @UpdateDate:2020/4/23 15:29
	 * @return: 返回
	 */
	@ApiOperation(value = "app业主端更新个人信息加用户头像")
	@PostMapping(value = "/updateUserLogo")
	public ApiResponseResult updateLogoUser(@RequestBody @Validated UserUpdateLogo vo) throws Exception {
		return userService.updateLogoUser(vo);
	}

	/**
	 * @Desc:    查看个人信息APP业主端
	 * @Author:邓磊
	 * @UpdateDate:2020/4/23 16:09
	 * @return: 返回
	 */
	@ApiOperation(value = "查看个人信息APP业主端")
    @GetMapping(value = "/queryLogoUserInfo")
	public ApiResponseResult queryLogoUserInfo(@ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id) throws Exception {
		return userService.queryLogoUserInfo(id);
	}

    /**
     * @description:查询excel文件导出列表
     * @return:
     * @author: 赵进华
     * @time: 2020/3/29 10:11
     */
    @ApiOperation(value = "查询excel文件导出列表(Excel类型:员工-user)")
    @GetMapping(value = "/queryExcelFile")
    public ApiResponseResult queryExcelFile(@ApiParam(value = "用户ID", required = true) @RequestParam(value = "userId", required = true) String userId,@ApiParam(value = "excel类型", required = true) @RequestParam(value = "excelType", required = true) String excelType) throws Exception {
        return excelService.queryExcelFile(userId,excelType);
    }


	/**
	 * @Desc: 更新手机号
	 * @param vo
	 * @Author:邓磊
	 * @UpdateDate:2020/4/24 9:40
	 * @return: 返回
	 */
	@ApiOperation(value = "更新手机号APP业主端")
	@PostMapping(value = "/updateUserMobile")
	public ApiResponseResult updateUserMobile(@RequestBody @Validated UserVo vo) throws Exception {
		return userService.updateUserMobile(vo);
	}


	/**
	 * @Desc: 查询用户拥有的角色
	 * @param userId
	 * @Author:吕文祥
	 * @UpdateDate:2020/4/25 15:09
	 * @return: 返回
	 */
	@ApiOperation(value = "查询用户拥有的角色")
	@GetMapping(value = "/getUserHadRoles")
	public ApiResponseResult getUserHadRoles(@ApiParam(value = "用户id", required = true) @RequestParam(value = "userId", required = true) String userId) throws Exception {
		return userService.getUserHadRoles(userId);
	}


	/**
	 *
	 * @Title: impUser
	 * @Description: excle导入
	 * @param file
	 * @return String
	 */
	@ApiOperation(value = "impUser用户信息批量导入", httpMethod = "POST", response = Object.class)
	@PostMapping("/importUser")
	public ApiResponseResult importUser(MultipartFile file,@ApiParam(value = "公司Id", required = true) @RequestParam(value = "tenantCompanyArray", required = true) String[] tenantCompanyArray,
										@ApiParam(value = "小区Id", required = true) @RequestParam(value = "communityId", required = true) String communityId){
		ApiResponseResult result = new ApiResponseResult();
		if (file == null || file.isEmpty()) {
			result.setCode(MessageCode.FAIL);
			result.setMsg("上传文件为空！");
			return result;
		}
		List<User> users = EasyExcelUtils.importData(file, 1, User.class);
		if(CollectionUtils.isNotEmpty(users)){
			if(StringUtils.isEmpty(users.get(0).getCompanyName()) && StringUtils.isEmpty(users.get(0).getCommunityName())){
				result.setCode(MessageCode.FAIL);
				result.setMsg("导入时，请删除第一行说明单元格！");
				return result;
			}
		}
		ApiResponseResult apiResponseResult = userService.importUser(users,tenantCompanyArray,communityId);
		return apiResponseResult;
	}

	/**
	 * @Desc: 车位信息批量导入下载模板
	 * @param response
	 * @Author:邓磊
	 * @UpdateDate:2020/4/24 16:16
	 */
	@ApiOperation(value = "用户信息批量导入下载模板", httpMethod = "GET", response = Object.class)
	@GetMapping("/expUserDemoDownload")
	public void expUserDemoDownload(HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		String fileName = "员工信息.xlsx";
		response.setHeader("Content-Disposition", "attachment;filename="+new String(fileName.getBytes(StandardCharsets.UTF_8),"iso8859-1"));

		try {
			InputStream inputStream =this.getClass().getClassLoader().getResourceAsStream("/excelModel/staff_template.xlsx");
			OutputStream os = response.getOutputStream();
			byte[] b = new byte[1024];
			int length;
			while((length = inputStream.read(b))>0){
				os.write(b,0,length);
			}
			inputStream.close();
		} catch (FileNotFoundException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}



	/**
	 * @Desc:    根据员工信息条件分页查询
	 * @param requestPageVO
	 * @Author:邓磊
	 * @UpdateDate:2020/4/28 9:36
	 * @return: 返回
	 */
	@CommunityPagePower
	@ApiOperation(value = "根据员工信息条件分页查询", response = UserEntityResponseVO.class)
	@PostMapping(value = "/queryUserPage")
	public ApiResponseResult queryUserPage(@RequestBody @Validated RequestPageVO<UserEntity> requestPageVO) throws Exception {
		return userService.queryUserPage(requestPageVO);
	}

	/**
	 *
	 * @param requestPageVO
	 * @return
	 * @throws Exception
	 */
	@CommunityPagePower
	@ApiOperation(value = "查询用户角色", response = UserRoleEntity.class)
	@PostMapping(value = "/selectUserRole")
	public ApiResponseResult selectUserRole(@RequestBody @Validated RequestPageVO<UserEntity> requestPageVO) throws Exception {
		return userService.selectUserRole(requestPageVO);
	}


    /**
     * @Desc:    根据员工信息详情
     * @param vo
     * @Author:邓磊
     * @UpdateDate:2020/4/28 9:36
     * @return: 返回
     */
    @ApiOperation(value = "根据员工信息条件分页查询", response = UserEntityResponseVO.class)
    @PostMapping(value = "/queryUserVoInfo")
    public ApiResponseResult queryUserVoInfo(@RequestBody UserEntity vo) throws Exception {
        return userService.queryUserVoInfo(vo);
    }
    
    
    
    /**
     * @Desc: 员工消息导出
     * @Author:邓磊
     * @UpdateDate:2020/5/14 17:13
     * @return: 返回
     */
	@ApiOperation(value = "员工导出数据信息", response = UserEntity.class)
	@GetMapping(value = "/exportUserEntityInfo")
	public void  exportUserEntityInfo(UserEntity userEntity) throws Exception {
		userService.exportUserEntityInfo(userEntity);
	}

	/**
	 * @description:获取岗位用户列表
	 * @return:
	 * @author: 赵进华
	 * @time: 2021/1/8 17:40
	 */
	@ApiOperation(value = "获取岗位用户列表", response = UserJobVo.class)
	@GetMapping(value = "/getUserJobList")
	public ApiResponseResult getUserJobList() throws Exception {
		return userService.getUserJobList();
	}

	/**
	 * @description:根据小区id获取岗位用户列表
	 * @return:
	 * @author: chq
	 */
	@ApiOperation(value = "获取当前小区岗位用户列表", response = UserJobVo.class)
	@GetMapping(value = "/getUserJobListByCommunityId")
	public ApiResponseResult getUserJobListByCommunityId(@RequestParam(value = "communityId")String communityId) throws Exception {
		List<String>communityIds=new ArrayList<>();
		communityIds.add(communityId);
		ApiResponseResult result = new ApiResponseResult();
		if(communityIds==null||communityIds.size()==0){
			result.setCode(MessageCode.FAIL);
			result.setMsg("小区id为空！");
			return result;
		}
		return userService.getUserJobListByCommunityId(communityIds);
	}


}
