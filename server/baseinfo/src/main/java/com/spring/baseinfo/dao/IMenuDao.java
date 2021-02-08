package com.spring.baseinfo.dao;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.baseinfo.MenuEntity;
import com.spring.base.vo.baseinfo.menu.MenuOneVo;
import com.spring.base.vo.baseinfo.menu.MenuTwoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-13 11:18:07
 * @Desc类说明: 菜单Dao
 */
@Mapper
public interface IMenuDao extends BaseDao<MenuEntity> {

 /**
  * 查询第一级菜单
  * @param
  * @return
  * @throws Exception
  * @author 作者：赵进华
  * @version 创建时间：2019年6月26日 上午10:50:03
  */
 List<MenuOneVo> queryMenuOne(String systemCode)throws Exception;

 /**
  * 查询第二级菜单
  * @param menuId
  * @return
  * @throws Exception
  * @author 作者：赵进华
  * @version 创建时间：2019年6月26日 上午10:50:03
  */
 List<MenuTwoVo> queryMenuTwo(@Param("menuId") String menuId, @Param("systemCode") String systemCode)throws Exception;

 /**
  * 查询按钮列表
  * @param menuId
  * @return
  * @throws Exception
  * @author 作者：赵进华
  * @version 创建时间：2019年6月26日 上午10:50:03
  */
 List<MenuTwoVo> queryButton(@Param("menuId") String menuId, @Param("systemCode") String systemCode)throws Exception;

 /**
  * 查询角色第一级菜单
  * @param
  * @return
  * @throws Exception
  * @author 作者：赵进华
  * @version 创建时间：2019年6月26日 上午10:50:03
  */
 List<MenuOneVo> queryRoleMenuOne(@Param("idList") List<String> idList, @Param("systemCode") String systemCode)throws Exception;

 /**
  * 查询角色第二级菜单
  * @param
  * @return
  * @throws Exception
  * @author 作者：赵进华
  * @version 创建时间：2019年6月26日 上午10:50:03
  */
 List<MenuTwoVo> queryRoleMenuTwo(@Param("idList") List<String> idList, @Param("parentId") String parentId, @Param("systemCode") String systemCode)throws Exception;

 /**
  * 查询角色按钮列表
  * @param
  * @return
  * @throws Exception
  * @author 作者：赵进华
  * @version 创建时间：2019年6月26日 上午10:50:03
  */
 List<MenuTwoVo> queryRoleButton(@Param("idList") List<String> idList, @Param("parentId") String parentId, @Param("systemCode") String systemCode)throws Exception;

 /**
  * 查询管理树形菜单
  * @param
  * @return
  * @throws Exception
  * @author 作者：赵进华
  * @version 创建时间：2019年5月24日 下午3:16:17
  */
 List<MenuOneVo> queryManageMenu(String id)throws Exception;

 /**
  * 查询收费系统树形菜单
  * @param
  * @return
  * @throws Exception
  * @author 作者：赵进华
  * @version 创建时间：2019年5月24日 下午3:16:17
  */
 List<MenuOneVo> queryPaymentMenu(String id)throws Exception;

 /**
  * 查询APP树形菜单
  * @param
  * @return
  * @throws Exception
  * @author 作者：赵进华
  * @version 创建时间：2019年5月24日 下午3:16:17
  */
 List<MenuOneVo> queryAppMenu(String id)throws Exception;

 /**
  * @description:判断下级是否有页面
  * @return:
  * @author: 赵进华
  * @time: 2020/4/26 17:50
  */
 Integer checkPage(String id)throws Exception;
}
