package com.spring.common.feign.client.fallback;

import com.spring.base.entity.pay.PayBillsEntity;
import com.spring.common.feign.client.PayFeignClient;
import com.spring.common.response.ApiResponseResult;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author dell
 * @Model: PayFeignClientFallbackFactory
 * @Description: PayFeignClientFallbackFactory
 * @Auter: Mollen
 * @Date: 2020/5/23  19:19
 *      
 **/
@Slf4j
@Component
public class PayFeignClientFallbackFactory implements FallbackFactory<PayFeignClient> {

    @Override
    public PayFeignClient create(Throwable throwable) {

        return new PayFeignClient() {
            @Override
            public ApiResponseResult queryList(PayBillsEntity vo) throws Exception {
                return null;
            }

        };

    }

}
