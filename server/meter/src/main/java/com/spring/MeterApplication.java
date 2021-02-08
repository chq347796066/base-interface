package com.spring;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

/**
* @author : 作者：赵进华 
* @version : 创建时间：2019年3月21日 上午9:36:54
* @Desc类说明 : 微服务启动类
*/
@MapperScan("com.spring.meter.dao")
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class MeterApplication {

    public static void main(String[] args) {
    	try {
    		//启动springboot
    		SpringApplication.run(MeterApplication.class, args);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    		System.exit(-1);
    	}
    }

	@Bean
	public PaginationInterceptor paginationInterceptor() {
		return new PaginationInterceptor();
	}

	@Bean
	public PerformanceInterceptor performanceInterceptor() {

		PerformanceInterceptor performanceInterceptor =new PerformanceInterceptor();
		//格式化sql语句
		Properties properties =new Properties();
		properties.setProperty("format", "true");
		performanceInterceptor.setProperties(properties);
		return performanceInterceptor;
	}
}
