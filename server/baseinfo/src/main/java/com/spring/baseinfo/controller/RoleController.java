package com.spring.baseinfo.controller;

import com.spring.base.controller.BaseController;
import com.spring.base.entity.baseinfo.RoleEntity;
import com.spring.base.vo.baseinfo.role.*;
import com.spring.common.annotation.TenantPagePower;
import com.spring.common.exception.ValidationException;
import com.spring.common.page.RequestPageVO;
import com.spring.common.request.RequestUtils;
import com.spring.common.response.ApiResponseResult;
import com.spring.baseinfo.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-18 15:37:54
 * @Desc类说明: 角色控制器
 */
@RestController
@Api(value = "角色", tags = "角色接口")
@RequestMapping("role")
public class RoleController extends BaseController {
    @Autowired
    private IRoleService roleService;

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
    public ApiResponseResult add(@RequestBody @Validated RoleAddVo vo, BindingResult result) throws Exception {
        if (result != null && result.hasErrors()) {
            throw new ValidationException(result);
        }
        return roleService.addRole(vo);
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
    public ApiResponseResult update(@RequestBody @Validated RoleUpdateVo vo,
                                    BindingResult result) throws Exception {
        if (result != null && result.hasErrors()) {
            throw new ValidationException(result);
        }
        return roleService.updateRole(vo);
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
        return roleService.deleteRole(id);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "批量删除", notes = "批量删除")
    @GetMapping(value = "/batchDelete")
    public ApiResponseResult batchDelete(@ApiParam(value = "主键id数组", required = true) @RequestParam("ids") String[] ids)
            throws Exception {
        if (null == ids && ids.length == 0) {
            throw new Exception("批量删除列表id不能为空");
        }
        return roleService.batchDelete(ids);
    }

    /**
     * 根据主键id查询对象
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据主键id查询对象", response = RoleEntity.class)
    @GetMapping(value = "/queryObject")
    public ApiResponseResult queryObject(
            @ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id)
            throws Exception {
        return roleService.queryObject(id);
    }


    /**
     * 根据条件查询列表
     *
     * @param vo
     * @return
     */
    @ApiOperation(value = "根据条件查询列表", response = RoleEntity.class)
    @PostMapping(value = "/queryList")
    public ApiResponseResult queryList(@RequestBody RoleEntity vo) throws Exception {
        return roleService.queryList(vo);
    }

    /**
     * 根据条件分页查询
     *
     * @param requestPageVO
     * @return
     */
    @TenantPagePower
    @ApiOperation(value = "根据条件分页查询", response = RoleEntity.class)
    @PostMapping(value = "/queryPage")
    public ApiResponseResult queryPage(@RequestBody RequestPageVO<RoleEntity> requestPageVO) throws Exception {
        ApiResponseResult result=new ApiResponseResult();
        if (RequestUtils.getIsSaas()) {
            //查询saas用户角色
            result=roleService.querySaasPage(requestPageVO);
        }else
        {
            //查询平台角色
            result=roleService.queryBasePage(requestPageVO);
        }
        return result;
    }

    /**
     * @description:获取角色列表
     * @return:
     * @author: 赵进华
     * @time: 2020/3/19 11:14
     */
    @ApiOperation(value = "获取角色列表", notes = "获取角色列表")
    @GetMapping(value = "/getRoleList")
    public ApiResponseResult getRoleList()
            throws Exception {
        return roleService.getRoleList();
    }

    /**
     * @description:根据角色ID获取已授权的菜单
     * @return:
     * @author: 赵进华
     * @time: 2020/3/30 17:00
     */
    @ApiOperation(value = "根据角色ID获取已授权的菜单", response = String.class)
    @GetMapping(value = "/getMenuByRoleId")
    public ApiResponseResult getMenuByRoleId(
            @ApiParam(value = "角色id", required = true) @RequestParam(value = "roleId", required = true) String roleId,
            @ApiParam(value = "系统代码", required = true) @RequestParam(value = "systemCode", required = true) String systemCode
    )throws Exception {
        return roleService.getMenuByRoleId(roleId,systemCode);
    }

    /**
     * @description:更新菜单权限
     * @return:
     * @author: 赵进华
     * @time: 2020/3/30 17:31
     */
    @ApiOperation(value = "更新菜单权限")
    @PostMapping(value = "/updateMenuRole")
    public ApiResponseResult updateMenuRole(@RequestBody @Validated UpdateMenuRoleVo vo, BindingResult result)
            throws Exception{
        if (result != null && result.hasErrors()) {
            throw new ValidationException(result);
        }
        return roleService.updateMenuRole(vo);
    }



    /**
     * @Desc: 获取角色下所有的用户
     * @param vo
     * @Author:邓磊
     * @UpdateDate:2020/4/22 20:42
     * @return: 返回
     */
    @ApiOperation(value = "获取角色下所有的用户列表", response = RoleEntity.class)
    @PostMapping(value = "/queryRoleUserList")
    public ApiResponseResult queryRoleUserList(@RequestBody RoleVo vo) throws Exception {
        return roleService.queryRoleUserList(vo);
    }

    /**
     * @description:saas角色新增
     * @return:
     * @author: 赵进华
     * @time: 2020/7/2 10:38
     */
    @ApiOperation(value = "saas角色新增")
    @PostMapping(value = "/addSaasRole")
    public ApiResponseResult addSaasRole(@RequestBody @Validated SaasRoleVo vo, BindingResult result) throws Exception {
        if (result != null && result.hasErrors()) {
            throw new ValidationException(result);
        }
        return roleService.addSaasRole(vo);
    }

    /**
     * @description:saas角色更新
     * @return:
     * @author: 赵进华
     * @time: 2020/7/2 14:33
     */
    @ApiOperation(value = "saas角色更新")
    @PostMapping(value = "/updateSaasRole")
    public ApiResponseResult updateSaasRole(@RequestBody @Validated SaasRoleVo vo, BindingResult result) throws Exception {
        if (result != null && result.hasErrors()) {
            throw new ValidationException(result);
        }
        return roleService.updateSaasRole(vo);
    }
}
