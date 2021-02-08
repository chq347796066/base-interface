package com.spring.maintenance.controller;

import com.spring.base.entity.baseinfo.RoleEntity;
import com.spring.base.vo.baseinfo.role.RoleVo;
import com.spring.common.response.ApiResponseResult;
import com.spring.maintenance.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *角色列表
 * @author dell
 */
@RestController
@Api(value = "查看角色信息", tags = "查看角色信息")
@RequestMapping("/inner/role")
public class RoleController {
    @Autowired
    private IRoleService roleService;

    /**
     * @Desc:   角色列表
     * @param vo
     * @Author:邓磊
     * @UpdateDate:2020/4/22 20:13
     */
    @ApiOperation(value = "根据条件查询列表", response = RoleEntity.class)
    @PostMapping(value = "/queryList")
    public ApiResponseResult queryList(@RequestBody RoleEntity vo) throws Exception {
        return roleService.queryList(vo);
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

}
