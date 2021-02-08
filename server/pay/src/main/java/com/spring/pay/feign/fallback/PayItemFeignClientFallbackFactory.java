package com.spring.pay.feign.fallback;

import com.spring.base.entity.pay.CommonResult;
import com.spring.pay.feign.PayItemFeignClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
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
public class PayItemFeignClientFallbackFactory implements FallbackFactory<PayItemFeignClient> {
//    @Autowired
//    private WorkAspect workAspect;


    @Override
    public PayItemFeignClient create(Throwable throwable) {
        return new PayItemFeignClient() {
                @Override
                public CommonResult decrease(Long productId, Integer count) {
                    try {
                      //  workAspect.doRecoveryActions(throwable);
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
