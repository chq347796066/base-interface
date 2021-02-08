package com.spring.common.feign.client;


import com.spring.common.constants.ServiceNameConstants;
import com.spring.common.feign.client.fallback.WorkflowFeignClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author dell
 * @Model: WorkflowFeignClient
 * @Description: WorkflowFeignClient
 * @Auter: Mollen
 * @Date: 2020/5/23  19:18
 *      
 **/
@FeignClient(name = ServiceNameConstants.WORKFLOW, fallbackFactory = WorkflowFeignClientFallbackFactory.class, decode404 = true)
public interface WorkflowFeignClient {


}
