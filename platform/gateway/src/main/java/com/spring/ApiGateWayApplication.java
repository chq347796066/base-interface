package com.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
* @author 作者：赵进华
* @version 创建时间：2019年3月21日 上午9:36:54
* @Desc类说明:网关启动类
*/
@SpringCloudApplication
public class ApiGateWayApplication {
	public static void main(String[] args) {
	    SpringApplication.run(ApiGateWayApplication.class, args);
	  }

}
