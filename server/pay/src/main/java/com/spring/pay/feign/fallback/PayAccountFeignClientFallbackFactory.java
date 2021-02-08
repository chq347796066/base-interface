package com.spring.pay.feign.fallback;

import com.spring.base.entity.account.SubAccountEntity;
import com.spring.base.entity.pay.BdHousePrepayInfoDto;
import com.spring.base.entity.pay.CommonResult;
import com.spring.base.vo.pay.payonekeypay.BillOffsetVo;
import com.spring.base.vo.pay.payonekeypay.PayOneVo;
import com.spring.pay.feign.PayAccountFeignClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Component
public class PayAccountFeignClientFallbackFactory implements FallbackFactory<PayAccountFeignClient> {


    //rivate WorkAspect workAspect;

    @Override
    public PayAccountFeignClient create(Throwable throwable) {
        return new PayAccountFeignClient() {
            @Override
            public CommonResult createPrePay(PayOneVo vo) {
                try {
                    //workAspect.doRecoveryActions(throwable);
                    log.error("我被服务降级了:{}",  throwable);
                    return new CommonResult("我被服务降级了",500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public CommonResult importPrePay(List<BdHousePrepayInfoDto> bdHousePrepayInfoDtos) {
                try {
                    //workAspect.doRecoveryActions(throwable);
                    log.error("我被服务降级了:{}",  throwable);
                    return new CommonResult("我被服务降级了",500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public List<SubAccountEntity> queryPrePayList(BillOffsetVo vo) {
                return null;
            }


            @Override
            public CommonResult decrease(Long userId, BigDecimal money) {
                try {
                    //workAspect.doRecoveryActions(throwable);
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
