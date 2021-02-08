package com.spring.common.feign.client;


import com.spring.base.entity.pay.CommonResult;
import com.spring.common.constants.ServiceNameConstants;
import com.spring.common.feign.client.fallback.ItemFeignClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @author dell
 * @Model: ItemFeignClient
 * @Description: ItemFeignClient
 * @Auter: Mollen
 * @Date: 2020/5/23  19:18
 *      
 **/
@FeignClient(name = ServiceNameConstants.ITEM, fallbackFactory = ItemFeignClientFallbackFactory.class, decode404 = true)
public interface ItemFeignClient {

    /**
     * 扣减库存
     */
    @GetMapping(value = "/storage/decrease")
    CommonResult decrease(@RequestParam("productId") Long productId, @RequestParam("count") Integer count);

}
