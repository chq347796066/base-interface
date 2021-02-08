package com.spring.common.util.algorithm;

import org.apache.commons.codec.digest.DigestUtils;

import java.security.SecureRandom;
import java.util.Date;


/**
* @author 作者：赵进华 
* @version 创建时间：2019年3月21日 上午9:36:54
* @Desc类说明:MD5加解密类
*/
public class Md5Helper {
	
	/**
	 * @Description: TODO
	 * @param input（二维码登录类型）
	 * 类型（二维码登录），时间戳（到ms的整数型），10位随机数,拼装的字符串
	 * @return String  
	 * @author 6011000137 pengxi
	 * @date 2016年6月22日
	 */
	public static String md5QrCode(String type){
		type = "TwoDIMAuth";
		Date date = new Date();
		StringBuffer input = new StringBuffer();
		input.append(type).append(date.getTime()).append(getRankData(10));
		StringBuffer qrCodeStr = new StringBuffer();
		qrCodeStr.append(type).append(":");
		String key = DigestUtils.md5Hex(input.toString());
		qrCodeStr.append(key).append(":");
		String value = DigestUtils.md5Hex(key + getRankData(10));
		qrCodeStr.append(value);
		
		return qrCodeStr.toString();
	}
	
	/**
	 * @Description: 生成96位长度的种子码
	 * @param workno 工号
	 * @param imei 手机设备号
	 * @return String 
	 * @throws
	 * @author 6011000137 pengxi
	 * @date 2016年6月22日
	 */
	public static String seedCode(String workno,String imei,Date date){
		StringBuffer seedCodeStr = new StringBuffer();
		StringBuffer input = new StringBuffer();
		input.append(date.getTime()).append(workno).append(imei).append(getRankData(10));
		
		seedCodeStr.append(DigestUtils.md5Hex(input.toString()));
		seedCodeStr.append(DigestUtils.md5Hex(seedCodeStr.toString()));
		seedCodeStr.append(DigestUtils.md5Hex(seedCodeStr.toString()));
		return seedCodeStr.toString();
	}
	
	/**
	 * @Description: 获取动态口令
	 * @param seedCode 种子码
	 * @param times 时间刷新的间隔次数
	 * @return String  
	 * @throws
	 * @author 6011000137 pengxi
	 * @date 2016年6月22日
	 */
	public static String dynamicCode(String seedCode,long times){
		StringBuffer dynamicCodeStr = new StringBuffer();
		String a1 = DigestUtils.md5Hex(seedCode + times);
		String a2 = DigestUtils.md5Hex(seedCode + a1);
		String seed = a2.substring(0, 6);
		char[] chars = seed.toCharArray();
		for(int i = 0 ; i < chars.length ; i++){
			char tmp = chars[i];
			if(Character.isDigit(tmp)){
				dynamicCodeStr.append(tmp);
			} else {
				int digitData = tmp;
				String digitStr = String.valueOf(digitData);
				dynamicCodeStr.append(digitStr.substring(digitStr.length()-1));
			}
		}
		return dynamicCodeStr.toString();
	}
	
	/**
	 * @Description: 参数的校验码
	 * @param input 按接口规范中定义的参数的顺序依次排列并进行字符的拼接后，进行MD5的计算
	 * @return String  
	 * @throws
	 * @author 6011000137 pengxi
	 * @date 2016年6月22日
	 */
	public static String verfiyCode(String input){
		return DigestUtils.md5Hex(input);
	}
	
	/**
	 * @Description: 令牌生成策略 MD5(工号||时间戳||随机数)
	 * @param workno
	 * @param times
	 * @return String  
	 * @throws
	 * @author 6011000137 pengxi
	 * @date 2016年6月27日
	 */
	public static String tokenCode(String workno,long times){
		StringBuffer tokenCodeStr = new StringBuffer();
		tokenCodeStr.append(workno).append(times).append(getRankData(10));
		return DigestUtils.md5Hex(tokenCodeStr.toString());
	}
	/**
	 * @Description: 需要生成length长度的随机数
	 * @param length
	 * @return String  
	 * @throws
	 * @author 6011000137 pengxi
	 * @date 2016年6月22日
	 */
	public static String getRankData(int length){
		StringBuffer data = new StringBuffer();
		SecureRandom ra =new SecureRandom();
		for(int i=0;i<length;i++){
			data.append(ra.nextInt(10));
		}
		return data.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(md5QrCode("zte"));
		System.out.println(seedCode("6011000137", "7867237462757",new Date()));
		System.out.println(dynamicCode("sss", 3));
		System.out.println(verfiyCode("sadaddasdadasdas210.21.236.134,广东省深圳市SSO-TESTPortal"));
	}
}
