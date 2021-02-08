package com.spring.baseinfo.dao;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.baseinfo.RoleMenuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-03-30 17:05:44
* @Desc类说明: 角色和菜单中间Dao
*/
@Mapper
public interface IRoleMenuDao extends BaseDao<RoleMenuEntity> {

/**
 * @description:根据roleId删除权限
 * @return:
 * @author: 赵进华
 * @time: 2020/3/30 17:33
 */
int deleteByRoleId(@Param("roleId") String roleId) throws Exception;

}
