package com.spring.common.sms;

import com.github.qcloudsms.SmsSingleSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* @author 作者：赵进华 
* @version 创建时间：2019年4月23日 下午6:27:53
* @Desc类说明:获取腾讯发送短信工具类
*/
public class SmsThread extends Thread {

	private static final Logger LOGGER = LoggerFactory.getLogger(SmsThread.class);


	private final SmsSingleSender sender;
	private final String nationCode;
	private final String phoneNumber;
	private final String msg;

	public SmsThread(SmsSingleSender sender, String nationCode, String phoneNumber, String msg) {

		this.sender = sender;
		this.nationCode = nationCode;
		this.phoneNumber = phoneNumber;
		this.msg = msg;

	}

	/**
	 * 注册成功发送短信
	 * @Author 熊锋
	 * @param phoneNumbers
	 * @param data
	 * @param tag
	 * @Description //TODO
	 * @Date 2021/1/18 15:47
	 * @return
	 */
	public static void sendMessage(String[] phoneNumbers,String data,Integer tag) throws Exception{
		int appid =1400472466;
		//短信应用SDK AppKey
		String appkey = "805c2ea8118bc64a136e5d0bba74a7b3";
		//签名
		String smsSign = "法本信息智慧物业";
		SmsSingleSender sender = new SmsSingleSender(appid, appkey);
		int templateId=0;
		String[] params=null;
		if (tag==1){
			//短信模板ID，需要在短信应用中申请
			templateId =846982;
			params = new String[]{phoneNumbers[0], data};
		}else {
			//短信模板ID，需要在短信应用中申请
			templateId =841177;
			params = new String[]{data};
		}
		sender.sendWithParam("86", phoneNumbers[0], templateId, params, smsSign, "", "");
	}
}
