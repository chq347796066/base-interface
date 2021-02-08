package com.spring.common.feign.client.fallback;

import com.spring.common.feign.client.PayFeignClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * @author dell
 * @Model: ReportFeignClientFallbackFactory
 * @Description: ReportFeignClientFallbackFactory
 * @Auter: Mollen
 * @Date: 2020/5/23  19:19
 *      
 **/
@Slf4j
@Component
public class ReportFeignClientFallbackFactory implements FallbackFactory<PayFeignClient> {

    @Override
    public PayFeignClient create(Throwable throwable) {
        return null;
    }

}
