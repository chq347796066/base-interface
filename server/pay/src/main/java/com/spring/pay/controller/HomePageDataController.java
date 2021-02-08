package com.spring.pay.controller;

import com.spring.base.controller.BaseController;
import com.spring.common.response.ApiResponseResult;
import com.spring.pay.service.IHomePageDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dell
 * @Method: HomePageDataController
 * @Description: 首页统计数据
 * @Auter: Mollen
 * @Date: 2020/5/19  13:44
 * @return 
 **/
@RestController
@Api(value = "首页统计数据", tags = "首页统计数据")
@RequestMapping("HomePageDataController")
public class HomePageDataController extends BaseController {

    @Autowired
    private IHomePageDataService homePageDataService;

    /**
     * @Method: GetTotalAmount
     * @Description: 今日总收费
     * @Auter: Mollen
     * @Date: 2020/5/19  13:49
     * @return 
     **/
    @ApiOperation(value = "统计当日总收入")
    @GetMapping(value = "/GetTotalAmount")
    public ApiResponseResult getTotalAmount()throws Exception{

        return homePageDataService.getTotalAmount();

    }


    /**
     * @Method: getMaxAmoutByCid
     * @Description: 当日收入最高的小区
     * @Auter: Mollen
     * @Date: 2020/5/19  13:49
     * @return
     **/
    @ApiOperation(value = "当日收入最高的小区")
    @GetMapping(value = "/getMaxAmoutByCid")
    public ApiResponseResult getMaxAmoutByCid()throws Exception{

        return homePageDataService.getMaxAmoutByCid();

    }


    /**
     * @Method: getOrderSum
     * @Description: 统计当日总订单
     * @Auter: Mollen
     * @Date: 2020/5/19  13:49
     * @return
     **/
    @ApiOperation(value = "统计当日总订单")
    @GetMapping(value = "/getOrderSum")
    public ApiResponseResult getOrderSum()throws Exception{

        return homePageDataService.getOrderSum();

    }


    /**
     * @Method: getMaxByPayType
     * @Description: 统计当日收费最多渠道
     * @Auter: Mollen
     * @Date: 2020/5/19  13:49
     * @return
     **/
    @ApiOperation(value = "统计当日收费最多渠道")
    @GetMapping(value = "/getMaxByPayType")
    public ApiResponseResult getMaxByPayType()throws Exception{

        return homePageDataService.getMaxByPayType();

    }


}
