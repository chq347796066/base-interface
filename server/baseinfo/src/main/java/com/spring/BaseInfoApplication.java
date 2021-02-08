package com.spring;

import com.spring.baseinfo.config.ICIPLoader;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

/**
* @author : 作者：赵进华 
* @version : 创建时间：2019年3月21日 上午9:36:54
* @Desc类说明 : 微服务启动类
*/
@MapperScan("com.spring.baseinfo.dao")
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class BaseInfoApplication {

	@Autowired
	private Environment env;

    public static void main(String[] args) {
    	try {
    		//启动springboot
    		SpringApplication.run(BaseInfoApplication.class, args);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    		System.exit(-1);
    	}
    }

	@Bean(initMethod="Initialized",destroyMethod="destroyed")
	public ICIPLoader getICIPLoader() {
		String path = System.getProperty("user.dir");
		ICIPLoader loader = new ICIPLoader(env.getProperty(path + "/src/main/resources/"));
		return loader;
	}

}
