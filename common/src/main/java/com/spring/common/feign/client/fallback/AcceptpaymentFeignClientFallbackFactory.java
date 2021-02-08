package com.spring.common.feign.client.fallback;

import com.spring.common.feign.client.AcceptpaymentFeignClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * @author dell
 * @Model: AcceptpaymentFeignClientFallbackFactory
 * @Description: AcceptpaymentFeignClientFallbackFactory
 * @Auter: Mollen
 * @Date: 2020/5/23  19:19 
 *
 **/
@Slf4j
@Component
public class AcceptpaymentFeignClientFallbackFactory implements FallbackFactory<AcceptpaymentFeignClient> {


    @Override
    public AcceptpaymentFeignClient create(Throwable throwable) {
        return new AcceptpaymentFeignClient() {




        };
    }

}
