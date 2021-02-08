package com.spring.common.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
* @author 作者：赵进华 
* @version 创建时间：2019年3月21日 上午9:36:54
* @Desc类说明:SpringBean获取工具类,可在系统任意地方获取,如:SpringContextUtil.getBean("userService");
*/
@Configuration
public class SpringContextUtil implements BeanFactoryAware {
    private static BeanFactory beanFactory = null;
 
    private static SpringContextUtil servlocator = null;
    @Override
    public void setBeanFactory(BeanFactory factory) throws BeansException {
        beanFactory = factory;
    }
 
    public BeanFactory getBeanFactory() {
        return beanFactory;
    }
  @Bean(name="serviceLocator")
   public SpringContextUtil springContextUtil()
   {
	   return new SpringContextUtil();
   }
    
    public static SpringContextUtil getInstance() {
        if (servlocator == null)
        {
            servlocator = (SpringContextUtil) beanFactory.getBean("serviceLocator");
        }
        return servlocator;
    }
 
    /**
    * 根据提供的bean名称得到相应的服务类     
    * @param servName bean名称     
    */
    public static Object getBean(String servName) {
        return beanFactory.getBean(servName);
    }
 
    /**
    * 根据提供的bean名称得到对应于指定类型的服务类
    * @param servName bean名称
    * @param clazz 返回的bean类型,若类型不匹配,将抛出异常
    */
    public static Object getBean(String servName, Class clazz) {
        return beanFactory.getBean(servName, clazz);
    }


}