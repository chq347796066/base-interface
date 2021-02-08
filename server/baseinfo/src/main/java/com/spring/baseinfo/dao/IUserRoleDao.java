package com.spring.baseinfo.dao;


import com.spring.base.dao.BaseDao;
import com.spring.base.entity.baseinfo.UserEntity;
import com.spring.base.entity.baseinfo.UserRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-15 10:32:48
 * @Desc类说明: 用户角色Dao
 */
@Mapper
public interface IUserRoleDao extends BaseDao<UserRoleEntity> {

 /**
  * @description:删除租户下的用户
  * @return:
  * @author: 赵进华
  * @time: 2020/7/15 10:45
  */
 int deleteTenantUser(String tenantId);

 /**
  * 查询公司用户角色
  * @param companyId
  * @return
  */
 List<UserRoleEntity> selectUserRole(@Param("userEntity") UserEntity userEntity);
}
