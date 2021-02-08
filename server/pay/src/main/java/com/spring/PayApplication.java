package com.spring;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
* @author : 作者：赵进华
* @version : 创建时间：2019年3月21日 上午9:36:54
* @Desc类说明 : 微服务启动类
*/
@MapperScan("com.spring.pay.dao")
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class PayApplication {

    public static void main(String[] args) {
    	try {
    		//启动springboot
    		SpringApplication.run(PayApplication.class, args);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    		System.exit(-1);
    	}
    }

}
