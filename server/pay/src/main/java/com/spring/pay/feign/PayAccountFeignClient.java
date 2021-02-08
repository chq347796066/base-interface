package com.spring.pay.feign;

import com.spring.base.entity.account.SubAccountEntity;
import com.spring.base.entity.pay.BdHousePrepayInfoDto;
import com.spring.base.entity.pay.CommonResult;
import com.spring.base.vo.pay.payonekeypay.BillOffsetVo;
import com.spring.base.vo.pay.payonekeypay.PayOneVo;
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
public interface PayAccountFeignClient {

    /**
     * 预缴
     */
    @PostMapping(value = "/prePay/income")
    CommonResult createPrePay(@RequestBody PayOneVo vo);

    /**
     * 预缴导入
     * @param bdHousePrepayInfoDtos
     * @return
     */
    @PostMapping(value = "/prePay/importIncome")
    CommonResult importPrePay(@RequestBody List<BdHousePrepayInfoDto> bdHousePrepayInfoDtos);

    /**
     * 根据条件查询账户余额
     * @param vo
     * @return
     */
    @PostMapping(value = "/prePay/queryPrePayList")
    List<SubAccountEntity> queryPrePayList(BillOffsetVo vo);

    /**
     * 扣减库存
     */
    @GetMapping(value = "/account/decrease")
    CommonResult decrease(@RequestParam("userId") Long userId, @RequestParam("money") BigDecimal money);



}
