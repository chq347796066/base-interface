package com.spring.common.util.string;

import com.alibaba.fastjson.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @author 作者：赵进华 
* @version 创建时间：2019年3月21日 上午9:36:54
* @Desc类说明:字符串工具类
*/
public class StringHelper {

	private static final Pattern PATTERN_BLANK = Pattern.compile("[a-z0-9A-Z]*");

	/**
	 * 获取一个GUID
	 * 
	 * @return
	 */
	public static String getGuid() {
		UUID uuid = UUID.randomUUID();
		String id = uuid.toString();
		return id;
	}
	
	/**
	 * 生成各種編碼
	 * 
	 * @return
	 */
	public static String generateCodeValue(String head) {
		DateFormat formater = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		StringBuilder sb = new StringBuilder(formater.format(new Date()));
		SecureRandom randow = new SecureRandom();
		StringBuffer strAm1 = new StringBuffer();
		for(int i = 0 ; i < 3 ;i++) {
			strAm1.append(randow.nextInt(10));
		}
		String msgNo = head + sb.append(strAm1).toString();
		return msgNo;
	}
	
	public static boolean isEmpty(String str){
        return null == str || str.length() == 0 || "".equals(str.trim());
    }
	
	/**
	 * 判断对象为空
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		}
		if ((obj instanceof List)) {
			return ((List) obj).size() == 0;
		}
		if ((obj instanceof String)) {
			return "".equals(((String) obj).trim());
		}
		return false;
	}
	
	public static boolean isNotEmpty(Object obj) {
		return !isEmpty(obj);
	}
	
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(List list) {
        return list == null || list.size() == 0;
    }
	
	
	public static Object byteToObject(byte[] bytes) {
		Object obj = null;
		try {
			ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
			ObjectInputStream oi = new ObjectInputStream(bi);
			obj = oi.readObject();
			bi.close();
			oi.close();
		} catch (Exception e) {
			System.out.println("translation" + e.getMessage());
		}
	    return obj;
 }
 
	public static byte[] objectToByte(Object obj) {
	    byte[] bytes = null;  
	    try {  
	        ByteArrayOutputStream bo = new ByteArrayOutputStream();  
	        ObjectOutputStream oo = new ObjectOutputStream(bo);  
	        oo.writeObject(obj);  
	        bytes = bo.toByteArray();  
	        bo.close();  
	        oo.close();  
	    } catch (Exception e) {  
	        System.out.println("translation" + e.getMessage());  
	    }  
	    return bytes;  
 	} 
	
	public static String versionConvert(String str) {
		String regEx="[^0-9.]";   
		Pattern p = Pattern.compile(regEx);   
		Matcher m = p.matcher(str);  
		String number = m.replaceAll("").trim();
		if(isEmpty(number)) {
			return "0";
		}
		return number;
	}
	/***
	 * 比较UDS版本
	 */
	public static boolean compareudsVersion(String orgcurrUdsVersion,String orgdbversion){
		String currUdsVersion = versionConvert(orgcurrUdsVersion);
		String dbversion = versionConvert(orgdbversion);
		String[] c1 = currUdsVersion.split("\\.");
		String[] c2 = dbversion.split("\\.");
		int length = c1.length;
		if(c1.length == 0 && c2.length != 0) {
			return false;
		}
		if(c2.length == 0 && c1.length != 0) {
			return true;
		}
		if(length > c2.length) {
			length = c2.length;
		}
		boolean flag = false;
		int value1 = 0;
		int value2 = 0;
		for(int i = 0 ; i < length ; i++) {
			value1 = Integer.valueOf(c1[i]);
			value2 = Integer.valueOf(c2[i]);
			if(value1 > value2) {
				return true;
			}
			if(value1 < value2) {
				return false;
			}
			if(i == length-1) {
				flag = true;
			}
		}
		if(flag) {
			return c1.length>=c2.length;
		}
		return true;
	}
	
	/***
	 * 比较UDS版本
	 */
	public static boolean compareVersion(String orgcurrUdsVersion,String orgdbversion){
		String currUdsVersion = versionConvert(orgcurrUdsVersion);
		String dbversion = versionConvert(orgdbversion);
		String[] c1 = currUdsVersion.split("\\.");
		String[] c2 = dbversion.split("\\.");
		int length = c1.length;
		if(c1.length == 0 && c2.length != 0) {
			return false;
		}
		if(c2.length == 0 && c1.length != 0) {
			return true;
		}
		if(length > c2.length) {
			length = c2.length;
		}
		boolean flag = false;
		int value1 = 0;
		int value2 = 0;
		for(int i = 0 ; i < length ; i++) {
			value1 = Integer.valueOf(c1[i]);
			value2 = Integer.valueOf(c2[i]);
			if(value1 > value2) {
				return true;
			}
			if(value1 < value2) {
				return false;
			}
			if(i == length-1) {
				flag = true;
			}
		}
		if(flag) {
			return c1.length>=c2.length;
		}
		return true;
	}
	
	/**
     * 获取成员数组
     * @param memberObj 成员对象
     * @return 成员数组
     */
    public static String[] getMemberArray(Object memberObj)
    {
        String[] arrMember = null;
        if (memberObj != null)
        {
            // 多个组
            if (memberObj instanceof Object[])
            {
                Object[] memObj = (Object[]) memberObj;
                arrMember = new String[memObj.length];
                for (int i = 0; i < memObj.length; i++)
                {
                    arrMember[i] = (String) memObj[i];
                }
            }
            // 一个组
            else
            {
                arrMember = new String[1];
                arrMember[0] = (String) memberObj;
            }
        }
        return arrMember;
    }
    
    public static boolean isNumeric(String str) { 
	   if(isEmpty(str)) {
		   return false;
	   }
	   
	   Matcher isNum = PATTERN_BLANK.matcher(str);
        return isNum.matches();
    }
    //判断String中是否含有中文字符
    public static boolean hasChinese(String str) {
        String regex = "[\u4e00-\u9fa5]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        boolean flag = false;
        if (matcher.find()) {
            flag = true;
        }
        return flag;
    }
    public static String map2json(Map<String,String> map) {
    	return JSONObject.toJSONString(map);
	}
	public static String object2String(Object object) {
		return JSONObject.toJSONString(object);
	}
	public static boolean isEquals(Object first,Object second){
		if(first == null){
			return false;
		}else return first.equals(second);
    }
	public static boolean isEqualsIgnoreCase(String first,String second){
		if(first == null){
			return false;
		}else return first.equalsIgnoreCase(second);
    }
	
	public static String getObjectValue(Object obj){
        return obj==null?"":obj.toString();
    }
}
