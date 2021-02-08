package com.spring.pay.service;

import com.spring.common.response.ApiResponseResult;

/**
 * @author dell
 * @Model: IHomePageDataService
 * @Description: IHomePageDataService
 * @Auter: Mollen
 * @Date: 2020/5/19  19:23 
 *      
 **/
public interface IHomePageDataService  {

    ApiResponseResult getTotalAmount() throws Exception;

    ApiResponseResult getMaxAmoutByCid() throws Exception;

    ApiResponseResult getOrderSum() throws Exception;

    ApiResponseResult getMaxByPayType() throws Exception;

}
