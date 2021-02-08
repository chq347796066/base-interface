package com.spring.saas.service;

import com.spring.base.entity.saas.UserEntity;
import com.spring.base.service.IBaseService;
import com.spring.base.vo.baseinfo.user.LoginVo;
import com.spring.base.vo.saas.UserAddVo;
import com.spring.base.vo.saas.UserUpdateVo;
import com.spring.common.response.ApiResponseResult;

 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-10 10:09:25
 * @Desc类说明: 运营用户列业务接口类
 */
public interface IUserService extends IBaseService<UserEntity,String>{
	
	/**
	 * 新增运营用户列
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-07-10 10:09:25
	 */
	 ApiResponseResult addUser(UserAddVo vo) throws Exception;
	
	/**
	 * 更运营用户列
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-07-10 10:09:25
	 */
	 ApiResponseResult updateUser(UserUpdateVo vo) throws Exception;

	 /**
	  * 运营后台用户登录
	  *
	  * @param loginVo
	  * @return
	  * @throws Exception
	  * @author 作者：赵进华
	  * @version 创建时间：2019年5月21日 下午2:50:57
	  */
     ApiResponseResult userLogin(LoginVo loginVo) throws Exception;
}
