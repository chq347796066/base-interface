package com.spring.saas.controller.tenant;

import com.spring.base.vo.saas.ApplicationVo;
import com.spring.base.vo.saas.MyAccountVo;
import com.spring.base.vo.saas.TenantResponseVo;
import com.spring.common.response.ApiResponseResult;
import com.spring.saas.service.ITenantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 熊锋
 * @Date: 2020-07-06 15:03
 * @Description:
 */
@RestController
@Api(value = "我的账户", tags = {"我的账户接口"})
@RequestMapping("my/account")
public class MyAccountController {

    @Autowired
    private ITenantService tenantService;

    /**
     * @author 熊锋
     * @date 2020/7/6 17:31
     * @description: 查询我的应用
     * @return com.spring.common.response.ApiResponseResult
     * @throws Exception
     */
    @ApiOperation(value = "我的应用", response = ApplicationVo.class)
    @GetMapping(value = "/queryMyApplication")
    public ApiResponseResult queryMyApplication(@ApiParam(value = "手机号码", required = true) @RequestParam(value = "mobile", required = true) String mobile)
            throws Exception {
        return tenantService.queryMyApplication(mobile);
    }

    /**
     * @author 熊锋
     * @date 2020/7/6 17:31
     * @description: 查询基本资料
     * @return com.spring.common.response.ApiResponseResult
     * @throws Exception
     */
    @ApiOperation(value = "根据手机号码基本资料", response = TenantResponseVo.class)
    @GetMapping(value = "/queryBasicData")
    public ApiResponseResult queryBasicData(@ApiParam(value = "手机号码", required = true) @RequestParam(value = "mobile", required = true) String mobile)
            throws Exception {
        return tenantService.queryBasicData(mobile);
    }

    /**
     * @author 熊锋
     * @date 2020/7/6 17:31
     * @description: 查询我的应用（主应用）
     * @return com.spring.common.response.ApiResponseResult
     * @throws Exception
     */
    @ApiOperation(value = "我的应用", response = MyAccountVo.class)
    @GetMapping(value = "/queryMainApplication")
    public ApiResponseResult queryMainApplication(@ApiParam(value = "手机号码", required = true) @RequestParam(value = "mobile", required = true) String mobile)
            throws Exception {
        return tenantService.queryMainApplication(mobile);
    }
}
