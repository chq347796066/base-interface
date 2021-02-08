package com.spring;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
* @author : 作者：赵进华 
* @version : 创建时间：2019年3月21日 上午9:36:54
* @Desc类说明 : 微服务启动类
*/
@SpringBootApplication
@EnableCaching
@EnableScheduling
public class IotDataApplication {

    public static void main(String[] args) {
    	try {
    		//启动springboot
    		SpringApplication.run(IotDataApplication.class, args);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    		System.exit(-1);
    	}
    }


}
