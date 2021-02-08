package com.spring.baseinfo.controller;

import com.spring.base.vo.baseinfo.common.DataVo;
import com.spring.baseinfo.service.ICommonService;
import com.spring.common.response.ApiResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description:公共控制器
 * @author: 赵进华
 * @time: 2020/4/9 14:06
 */
@RestController
@Api(value = "公共功能", tags = "公共功能接口")
@RequestMapping("common")
public class CommonController {

    @Autowired
    private ICommonService commonService;


    /**
     * @description:发送短信
     * @return:
     * @author: 赵进华
     * @time: 2020/4/9 14:34
     */
//    @ApiOperation(value = "发送短信")
//    @PostMapping(value = "/sendSms")
//    public ApiResponseResult sendSms(@RequestBody DataVo vo)
//            throws Exception {
//        return commonService.sendSms(vo.getData());
//    }

    /**
     * @description:判断短信是否过期
     * @return:
     * @author: 赵进华
     * @time: 2020/4/9 14:36
     */
    @ApiOperation(value = "判断短信是否过期(返回true,false)")
    @PostMapping(value = "/checkSms")
    public ApiResponseResult checkSms(@RequestBody DataVo vo)
            throws Exception {
        return commonService.checkSms(vo.getData());
    }

    /**
     * @description:发送短信验证码
     * @return:
     * @author: 赵进华
     * @time: 2020/4/9 14:34
     */
    @ApiOperation(value = "发送短信")
    @PostMapping(value = "/sendSms")
    public ApiResponseResult sendMessage(@RequestBody DataVo vo)
            throws Exception {
        return commonService.sendSms(vo.getData());
    }
}
