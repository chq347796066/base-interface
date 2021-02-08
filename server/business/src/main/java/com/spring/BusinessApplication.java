package com.spring;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
* @author : 作者：赵进华 
* @version : 创建时间：2019年3月21日 上午9:36:54
* @Desc类说明 : 微服务启动类
*/
@SpringBootApplication
@EnableCaching
@MapperScan("com.spring.business.dao")
@EnableFeignClients
public class BusinessApplication {

    public static void main(String[] args) {
    	try {
    		//启动springboot
    		SpringApplication.run(BusinessApplication.class, args);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    		System.exit(-1);
    	}
    }


}
