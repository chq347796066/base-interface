package com.spring.common.feign.client;


import com.spring.common.constants.ServiceNameConstants;
import com.spring.common.feign.client.fallback.ReportFeignClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author dell
 * @Model: ReportFeignClient
 * @Description: ReportFeignClient
 * @Auter: Mollen
 * @Date: 2020/5/23  19:19
 *      
 **/
@FeignClient(name = ServiceNameConstants.REPORT, fallbackFactory = ReportFeignClientFallbackFactory.class, decode404 = true)
public interface ReportFeignClient {


}
