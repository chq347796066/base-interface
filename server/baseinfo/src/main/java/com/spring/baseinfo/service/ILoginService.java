package com.spring.baseinfo.service;

import com.spring.base.vo.baseinfo.role.GetRolePowerVo;
import com.spring.base.vo.baseinfo.user.LoginVo;
import com.spring.common.response.ApiResponseResult;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-13 16:29:15
 * @Desc类说明: 登录业务接口类
 */
public interface ILoginService {

    /**
     * 登录
     *
     * @param loginVo
     * @return
     * @throws Exception
     * @author 作者：赵进华
     * @version 创建时间：2019年5月21日 下午2:50:57
     */
    ApiResponseResult userLogin(LoginVo loginVo) throws Exception;

    /**
     * @description:根据角色查询用户权限
     * @return:
     * @author: 赵进华
     * @time: 2020/4/3 15:01
     */
    ApiResponseResult getUserPower(GetRolePowerVo vo) throws Exception;
}
