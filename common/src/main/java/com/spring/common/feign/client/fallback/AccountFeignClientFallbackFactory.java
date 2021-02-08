package com.spring.common.feign.client.fallback;

import com.spring.base.entity.pay.CommonResult;
import com.spring.base.vo.pay.payonekeypay.PayOneVo;
import com.spring.base.vo.pay.payonekeypay.PrePayVo;
import com.spring.common.feign.client.AccountFeignClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Component
public class AccountFeignClientFallbackFactory implements FallbackFactory<AccountFeignClient> {


//    @Autowired
//    private WorkAspect workAspect;

    @Override
    public AccountFeignClient create(Throwable throwable) {
        return new AccountFeignClient() {
            @Override
            public CommonResult createPrePay(PayOneVo vo) {
                try {
//                    workAspect.doRecoveryActions(throwable);
                    log.error("我被服务降级了:{}",  throwable);
                    return new CommonResult("我被服务降级了",500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public CommonResult decrease(Long userId, BigDecimal money) {
                try {
//                    workAspect.doRecoveryActions(throwable);
                    log.error("我被服务降级了:{}", userId+":"+money, throwable);
                    return new CommonResult("我被服务降级了",500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

        };
    }
}
