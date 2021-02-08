package com.spring.common.config;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;

/**
 * @ProjectName: springboot-upload
 * @Package: com.example.springboot.fs
 * @ClassName: FdfsConfiguration
 * @Description:
 * @Author: Xiao Nong
 * @Version: 1.0
 */
@Configuration
@EnableMBeanExport(registration= RegistrationPolicy.IGNORE_EXISTING)
@Import(FdfsClientConfig.class)
public class FdfsConfiguration {
}
