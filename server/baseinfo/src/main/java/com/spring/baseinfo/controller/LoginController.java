package com.spring.baseinfo.controller;

import com.spring.base.vo.baseinfo.menu.MenuOneVo;
import com.spring.base.vo.baseinfo.role.GetRolePowerVo;
import com.spring.base.vo.baseinfo.user.LoginReturnVo;
import com.spring.base.vo.baseinfo.user.LoginVo;
import com.spring.common.cache.TempCaheUtil;
import com.spring.common.response.ApiResponseResult;
import com.spring.baseinfo.service.ILoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:登录控制器
 * @author: 赵进华
 * @time: 2020/3/13 10:24
 */
@RestController
@Api(value = "登录接口", tags = "登录接口")
@RequestMapping("lgonin")
public class LoginController {
    @Autowired
    private ILoginService loginService;

    /**
     * 用户登录
     *
     * @param vo
     * @return
     * @throws Exception
     * @author 作者：赵进华
     * @version 创建时间：2019年5月21日 下午2:52:35|
     */
    @ApiOperation(value = "用户登录", response = LoginReturnVo.class)
    @PostMapping(value = "/userLogin")
    public ApiResponseResult userLogin(@RequestBody LoginVo vo) throws Exception {
        return loginService.userLogin(vo);
    }

    /**
     * @description:根据角色查询用户权限
     * @return:
     * @author: 赵进华
     * @time: 2020/4/3 15:17
     */
    @ApiOperation(value = "根据角色id查询用户权限" , response = MenuOneVo.class)
    @PostMapping(value = "/getUserPower")
    public ApiResponseResult getUserPower(@RequestBody GetRolePowerVo vo) throws Exception {
        return loginService.getUserPower(vo);
    }
}
