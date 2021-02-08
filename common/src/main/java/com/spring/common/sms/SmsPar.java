package com.spring.common.sms;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
* @author 作者：赵进华 
* @version 创建时间：2019年4月23日 下午6:27:53
* @Desc类说明:获取腾讯发送短信的参数
*/
@Component
public class SmsPar {
	private static String appId;
	
	private static String appkey;
	
	@Value("${sms.appid}")
	private  String appIdValue;
	
	@Value("${sms.appkey}")
	private  String appkeyValue;

	@PostConstruct
	public  void getAppid() {
		appId = appIdValue;
	}

	@PostConstruct
	public  void getAppkey() {
		appkey = appkeyValue;
	}
	
	public static String getAppidValue()
	{
		return appId;
	}
	
	public static String getAppkeyValue()
	{
		return appkey;
	}
}
