package com.spring.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: springboot-upload
 * @Package: com.example.springboot.fs
 * @ClassName: FdfsConfig
 * @Description:
 * @Author: Xiao Nong
 * @Version: 1.0
 */
@Component
@Data
public class FdfsConfig {

    @Value("${fdfs.resHost}")
    private String resHost;

    @Value("${fdfs.storagePort}")
    private String storagePort;

}
