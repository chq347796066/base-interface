package com.spring.common.util.random;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


/**
* @author 作者：赵进华 
* @version 创建时间：2019年3月21日 上午9:36:54
* @Desc类说明:生成随机数工具类
*/
public class RandomUtil {
	 /** 
     * 生成随机数：当前年月日时分秒+五位随机数 
     *  
     * @return 
     */  
    public static String getRandomNo() {  
  
        SimpleDateFormat simpleDateFormat;  
  
        simpleDateFormat = new SimpleDateFormat("yyyyMMdd");  
  
        Date date = new Date();  
  
        String str = simpleDateFormat.format(date);  
  
        Random random = new Random();  
  
        int rannum = (int) (random.nextDouble() * (999 - 100 + 1)) + 100;
  
        return str+rannum;
    }  
    
    /**
     * 随机生成6位验证码
     * @return
     */
    public static String getRandomCode(Integer code){
        Random random = new Random();
        StringBuffer result= new StringBuffer();
        for (int i=0;i<code;i++){
            result.append(random.nextInt(10));
        }
        return result.toString();
    }
    
    public static void main(String[] args) {
		System.out.println(getRandomNo());
	}
}
