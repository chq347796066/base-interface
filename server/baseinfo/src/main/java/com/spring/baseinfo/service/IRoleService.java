package com.spring.baseinfo.service;

import com.spring.base.entity.baseinfo.RoleEntity;
import com.spring.base.service.IBaseService;
import com.spring.base.vo.baseinfo.role.*;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;
import org.apache.poi.ss.formula.functions.T;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-18 15:37:54
 * @Desc类说明: 角色业务接口类
 */
public interface IRoleService extends IBaseService<RoleEntity,String> {
	
	/**
	 * 新增角色
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-03-18 15:37:54
	 */
    ApiResponseResult addRole(RoleAddVo vo) throws Exception;
	
	/**
	 * 更角色
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-03-18 15:37:54
	 */
    ApiResponseResult updateRole(RoleUpdateVo vo) throws Exception;

	/**
	 * @description:获取角色列表
	 * @return:
	 * @author: 赵进华
	 * @time: 2020/3/19 11:10
	 */
    ApiResponseResult getRoleList() throws Exception;

	/**
	 * @description:根据角色ID获取已授权的菜单
	 * @return:
	 * @author: 赵进华
	 * @time: 2020/3/30 16:59
	 */
    ApiResponseResult getMenuByRoleId(String roleId, String systemCode) throws Exception;

	/**
	 * @description:更新菜单权限
	 * @return:
	 * @author: 赵进华
	 * @time: 2020/3/30 17:30
	 */
    ApiResponseResult updateMenuRole(UpdateMenuRoleVo vo) throws Exception;



	/**
	 * @Desc:   获取角色下所有的用户
	 * @param vo
	 * @Author:邓磊
	 * @UpdateDate:2020/4/22 20:44
	 */
    ApiResponseResult queryRoleUserList(RoleVo vo) throws Exception;

    /**
     * @description:saas角色新增
     * @return:
     * @author: 赵进华
     * @time: 2020/7/2 10:33
     */
    ApiResponseResult addSaasRole(SaasRoleVo vo) throws Exception;

	/**
	 * @description:saas角色更新
	 * @return:
	 * @author: 赵进华
	 * @time: 2020/7/2 14:32
	 */
    ApiResponseResult updateSaasRole(SaasRoleVo vo) throws Exception;

	/**
	 * @description:删除角色
	 * @return:
	 * @author: 赵进华
	 * @time: 2020/7/2 14:52
	 */
	ApiResponseResult deleteRole(String id) throws Exception;

	/**
	 * 物业根据条件分页查询
	 * @param requestPageVO
	 * @return
	 * @throws Exception
	 */
	ApiResponseResult queryBasePage(RequestPageVO<RoleEntity> requestPageVO) throws Exception;

	/**
	 * saas根据条件分页查询
	 * @param requestPageVO
	 * @return
	 * @throws Exception
	 */
	ApiResponseResult querySaasPage(RequestPageVO<RoleEntity> requestPageVO) throws Exception;
}
