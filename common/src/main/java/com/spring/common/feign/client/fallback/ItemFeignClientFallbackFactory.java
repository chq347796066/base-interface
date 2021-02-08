package com.spring.common.feign.client.fallback;

import com.spring.base.entity.pay.CommonResult;
import com.spring.common.feign.client.ItemFeignClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author dell
 * @Model: ItemFeignClientFallbackFactory
 * @Description: ItemFeignClientFallbackFactory
 * @Auter: Mollen
 * @Date: 2020/5/23  19:19
 *      
 **/
@Slf4j
@Component
public class ItemFeignClientFallbackFactory implements FallbackFactory<ItemFeignClient> {
//    @Autowired
//    private WorkAspect workAspect;


    @Override
    public ItemFeignClient create(Throwable throwable) {
        return new ItemFeignClient() {
                @Override
                public CommonResult decrease(Long productId, Integer count) {
                    try {
                        //workAspect.doRecoveryActions(throwable);
                        log.error("我被服务降级了:{}", productId+":"+count, throwable);
                        return new CommonResult("我被服务降级了",500);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            };
        };
}
