package com.spring.common.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
* @author 作者：赵进华 
* @version 创建时间：2019年3月21日 上午9:36:54
* @Desc类说明:自动配置类
*/

@Configuration
@ComponentScan(basePackages = { "com.spring.*"})
public class CommonAutoConfig {

}
