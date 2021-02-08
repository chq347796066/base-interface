package com.spring.common.feign.client.fallback;

import com.spring.common.feign.client.WorkflowFeignClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * @author dell
 * @Model: WorkflowFeignClientFallbackFactory
 * @Description: WorkflowFeignClientFallbackFactory
 * @Auter: Mollen
 * @Date: 2020/5/23  19:19 
 *      
 **/
@Slf4j
@Component
public class WorkflowFeignClientFallbackFactory implements FallbackFactory<WorkflowFeignClient> {

    @Override
    public WorkflowFeignClient create(Throwable throwable) {
        return null;
    }
}
