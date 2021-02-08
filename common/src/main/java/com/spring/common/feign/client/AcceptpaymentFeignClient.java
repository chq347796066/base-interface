package com.spring.common.feign.client;


import com.spring.common.constants.ServiceNameConstants;
import com.spring.common.feign.client.fallback.AcceptpaymentFeignClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author dell
 * @Model: AcceptpaymentFeignClient
 * @Description: AcceptpaymentFeignClient
 * @Date: 2020/5/23  19:18
 *      
 **/
@FeignClient(name = ServiceNameConstants.ACCEPTPAYMENT, fallbackFactory = AcceptpaymentFeignClientFallbackFactory.class, decode404 = true)
public interface AcceptpaymentFeignClient {


}
