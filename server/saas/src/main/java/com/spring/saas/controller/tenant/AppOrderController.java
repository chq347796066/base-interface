package com.spring.saas.controller.tenant;

import com.spring.base.entity.saas.ApplicationEntity;
import com.spring.base.vo.saas.MyAccountVo;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;
import com.spring.saas.service.IApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 开发人员名字
 * @version 1.0.0
 * CreateDate 2020/7/7 9:43
 * description
 */
@RestController
@Api(value = "应用订购", tags = {"应用订购接口"})
@RequestMapping("app/order")
public class AppOrderController {

    @Autowired
    private IApplicationService applicationService;

    /**
     * @author 熊锋
     * @date 2020/7/6 17:31
     * @description: 查询可订购的应用列表
     * @return com.spring.common.response.ApiResponseResult
     * @throws Exception
     */
    @ApiOperation(value = "查询可订购的应用列表", response = MyAccountVo.class)
    @PostMapping(value = "/queryAppList")
    public ApiResponseResult queryAppList(@RequestBody RequestPageVO<ApplicationEntity> requestPageVO)
            throws Exception {
        return applicationService.queryAppList(requestPageVO);
    }
}
