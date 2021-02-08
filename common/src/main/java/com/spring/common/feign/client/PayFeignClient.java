package com.spring.common.feign.client;


import com.spring.base.entity.pay.PayBillsEntity;
import com.spring.common.constants.ServiceNameConstants;
import com.spring.common.feign.client.fallback.ItemFeignClientFallbackFactory;
import com.spring.common.response.ApiResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author dell
 * @Model: PayFeignClient
 * @Description: PayFeignClient
 * @Auter: Mollen
 * @Date: 2020/5/23  19:19
 *
 **/
@FeignClient(name = ServiceNameConstants.PAY, fallbackFactory = ItemFeignClientFallbackFactory.class, decode404 = true)
public interface PayFeignClient {


    /**
     * @Desc:查看房产缴费账单
     * @Author:邓磊
     * @UpdateDate:2020/5/19 15:27
     * @return: 返回
     */
    @PostMapping(value = "paybills/queryList")
    ApiResponseResult queryList(PayBillsEntity vo) throws Exception;

}
