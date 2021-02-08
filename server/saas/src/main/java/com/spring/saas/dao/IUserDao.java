package com.spring.saas.dao;


import com.spring.base.dao.BaseDao;
import com.spring.base.entity.saas.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-10 10:09:25
 * @Desc类说明: 运营用户列Dao
 */
@Mapper
public interface IUserDao extends BaseDao<UserEntity> {

 /**
  * 根据用户名和密码查询用户
  *
  * @param userId
  * @param password
  * @return
  * @throws Exception
  * @author 作者：赵进华
  * @version 创建时间：2019年5月21日 下午3:07:32
  */
 UserEntity getUserByUserIdPassword(@Param("userId") String userId, @Param("password") String password) throws Exception;
	
}
