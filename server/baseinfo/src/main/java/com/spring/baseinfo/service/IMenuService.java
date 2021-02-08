package com.spring.baseinfo.service;

import com.spring.base.entity.baseinfo.MenuEntity;
import com.spring.base.service.IBaseService;
import com.spring.base.vo.baseinfo.menu.MenuAddVo;
import com.spring.base.vo.baseinfo.menu.MenuOneVo;
import com.spring.base.vo.baseinfo.menu.MenuUpdateVo;
import com.spring.common.response.ApiResponseResult;

import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-13 11:18:07
 * @Desc类说明: 菜单业务接口类
 */
public interface IMenuService extends IBaseService<MenuEntity,String> {
	
	/**
	 * 新增菜单
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-03-13 11:18:07
	 */
    ApiResponseResult addMenu(MenuAddVo vo) throws Exception;
	
	/**
	 * 更菜单
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-03-13 11:18:07
	 */
    ApiResponseResult updateMenu(MenuUpdateVo vo) throws Exception;


	/**
	 * 查询管理员菜单
	 * @return
	 * @throws Exception
	 * @author 作者：赵进华
	 * @version 创建时间：2019年6月26日 上午11:39:32
	 */
    List<MenuOneVo> queryAdminMenu(String systemCode)throws Exception;

	/**
	 * 根据角色查询菜单按钮权限
	 * @return
	 * @throws Exception
	 * @author 作者：赵进华
	 * @version 创建时间：2019年6月26日 上午11:39:32
	 */
    List<MenuOneVo> queryRoleMenu(String roleId, String parentId, String systemCode)throws Exception;

	/**
	 * 查询管理系统树形菜单
	 * @return
	 * @throws Exception
	 * @author 作者：赵进华
	 * @version 创建时间：2019年5月24日 下午3:19:25
	 */
    ApiResponseResult queryManageTreeMenu()throws Exception;

	/**
	 * 查询收费系统树形菜单
	 * @return
	 * @throws Exception
	 * @author 作者：赵进华
	 * @version 创建时间：2019年5月24日 下午3:19:25
	 */
    ApiResponseResult queryPaymentTreeMenu()throws Exception;

	/**
	 * 查询App系统树形菜单
	 * @return
	 * @throws Exception
	 * @author 作者：赵进华
	 * @version 创建时间：2019年5月24日 下午3:19:25
	 */
    ApiResponseResult queryAppTreeMenu()throws Exception;

	/**
	 * 删除菜单
	 * @param id
	 * @return
	 */
    ApiResponseResult deleteMenu(String id) throws Exception;
}
