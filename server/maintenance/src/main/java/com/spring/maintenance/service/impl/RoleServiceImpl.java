package com.spring.maintenance.service.impl;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.baseinfo.RoleEntity;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.base.vo.baseinfo.role.RoleVo;
import com.spring.common.feign.client.BaseInfoFeignClient;
import com.spring.common.response.ApiResponseResult;
import com.spring.maintenance.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dell
 */
@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<RoleEntity, String> implements IRoleService {
    @Autowired
    private BaseInfoFeignClient roleFeignCilnet;

    @Override
    public BaseDao getBaseMapper() {
        return null;
    }
    /**
     * @Desc:  角色列表
     * @param vo
     * @Author:邓磊
     * @UpdateDate:2020/4/22 20:19
     * @return: ApiResponseResult
     */
    @Override
    public ApiResponseResult queryRoleEntityList(RoleEntity vo) throws Exception {
        return  roleFeignCilnet.queryList(vo);
    }


    /**
     * @Desc:    角色下的所有用户
     * @param vo
     * @Author:邓磊
     * @UpdateDate:2020/4/22 21:27
     */
    @Override
    public ApiResponseResult queryRoleUserList(RoleVo vo) throws Exception {
        return roleFeignCilnet.queryRoleUserList(vo);
    }
}
