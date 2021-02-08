package com.spring.maintenance.service;

import com.spring.base.entity.baseinfo.RoleEntity;
import com.spring.base.service.IBaseService;
import com.spring.base.vo.baseinfo.role.RoleVo;
import com.spring.common.response.ApiResponseResult;

/**
 * 查看角色列表
 * @author dell
 */
public interface IRoleService extends IBaseService<RoleEntity,String> {

    /**
     * 角色列表
     * @param vo
     * @return
     * @throws Exception
     */
     ApiResponseResult queryRoleEntityList(RoleEntity vo) throws Exception;



    /**
     * 角色下的所用用户
     * @param vo
     * @return
     * @throws Exception
     */
    ApiResponseResult queryRoleUserList(RoleVo vo) throws Exception;

}
