package com.spring.common.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
* @author 作者：赵进华 
* @version 创建时间：2019年3月21日 上午9:36:54
* @Desc类说明:SpringBeans工具类
*/
public class SpringBeansUtils implements ApplicationContextAware {

	private static ApplicationContext context;

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		return (T) context.getBean(name);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBeane(Class<?> requiredType) {
		return (T) context.getBean(requiredType);
	}

	public static ApplicationContext getContext() {
		return context;
	}

	@Override
	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException {
		context = ctx;
	}

}
