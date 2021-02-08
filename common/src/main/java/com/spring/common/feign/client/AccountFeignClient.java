package com.spring.common.feign.client;

import com.spring.base.entity.pay.CommonResult;
import com.spring.base.vo.pay.payonekeypay.PayOneVo;
import com.spring.base.vo.pay.payonekeypay.PrePayVo;
import com.spring.common.constants.ServiceNameConstants;
import com.spring.common.feign.client.fallback.AccountFeignClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@FeignClient(name = ServiceNameConstants.ACCOUNT, fallbackFactory = AccountFeignClientFallbackFactory.class, decode404 = true)
public interface AccountFeignClient {

    @PostMapping(value = "/prePay/income")
    CommonResult createPrePay(@RequestBody PayOneVo vo);

    /**
     * 扣减库存
     */
    @GetMapping(value = "/account/decrease")
    CommonResult decrease(@RequestParam("userId") Long userId, @RequestParam("money") BigDecimal money);

}
