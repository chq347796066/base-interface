package com.spring.common.util.des;

import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
* @author 作者：赵进华 
* @version 创建时间：2019年3月21日 上午9:36:54
* @Desc类说明:DES加密解密
*/
public class DesToolUtil {
	private static final String DES= "DES";
	private static final String HEX = "0123456789abcdef";
	private static final String DEFAULT_KEY = "authcent";
	private static final String UTF8 = "UTF-8";
	/**
	 * 加密字符串，返回加密结果BASE64编码后字符串
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String encryptBase64(String data) throws Exception
	{
		return encryptBase64(data, DEFAULT_KEY);
	}
	
	
	/**
	 * 16进制字符串转字节数组
	 * @param str
	 * @return
	 */
	public static byte[] converHexString(String str)
	{
		byte[] digest = new byte[str.length() / 2];
		for (int i= 0; i< digest.length; i++) {
			String byteStr = str.substring(2* i, 2*i+2);
			int byteValue = Integer.parseInt(byteStr, 16);
			digest[i] = (byte)byteValue;
		}
		return digest;
	}
	
	/**
	 * 字节数组转字符串方法1
	 * @param bytes
	 * @return
	 */
	public static String toHexString(byte[] bytes)
	{
		StringBuilder hexStr = new StringBuilder();
		for (byte b : bytes) {
			String plainText = Integer.toHexString(0xff & b);
			if (plainText.length()< 2)
			{
				plainText = "0" + plainText;
			}
			hexStr.append(plainText);
		}
		return hexStr.toString();
	}
	
	/**
	 * 字节数组转字符串方法2
	 * @param bytes
	 * @return
	 */
	public static String byte2Hex(byte[] bytes)
	{
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		for (byte b : bytes) {
			//取出字节的高4位，与0x0F与运算，得到0-15之间的数据，通过HEX.charAt(0-15)即为16进制数
			sb.append(HEX.charAt((b >> 4)& 0x0f));
			//取出字节的低位，与0x0F与运算，得到0-15之间的数据，通过HEX.charAt(0-15)即为16进制数
			sb.append(HEX.charAt(b & 0x0f));
		}
		return sb.toString();
	}
	
	
	/**
	 * 加密字符串，返回加密结果转16进制的字符串
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String encryptToHex(String data) throws Exception
	{
		return encryptToHex(data, DEFAULT_KEY);
	}
	
	/**
	 * 加密字符串，返回加密结果转16进制的字符串
	 * @param data
	 * @param key 密钥
	 * @return
	 * @throws Exception
	 */
	public static String encryptToHex(String data, String key) throws Exception
	{
		return encryptToHex(data, key, UTF8);
	}
	
	/**
	 * 加密字符串，返回加密结果转16进制的字符串
	 * @param data
	 * @param key 密钥
	 * @param charSet 字符编码
	 * @return
	 * @throws Exception
	 */
	public static String encryptToHex(String data, String key, String charSet) throws Exception
	{
		byte[] encrypt = encryptByte(data, key, charSet);
		return byte2Hex(encrypt);
	}
	
	/**
	 * 加密字符串，返回加密结果BASE64编码的字符串
	 * @param data
	 * @param key 密钥
	 * @return
	 * @throws Exception
	 */
	public static String encryptBase64(String data, String key) throws Exception
	{
		return encryptBase64(data, key, UTF8);
	}
	
	/**
	 * 加密字符串，返回加密结果BASE64编码的字符串
	 * @param data
	 * @param key 密钥
	 * @param charSet 字符编码
	 * @return
	 * @throws Exception
	 */
	public static String encryptBase64(String data, String key, String charSet) throws Exception
	{
		byte[] encrypt = encryptByte(data, key, charSet);
		return Base64Utils.encodeToString(encrypt);
	}
	
	/**
	 * 加密字符串，返回加密字节数组
	 * @param data
	 * @param key 密钥
	 * @param charSet 字符编码
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByte(String data, String key, String charSet) throws Exception
	{
		byte[] keyByte = key.getBytes(charSet);
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		//从原始密钥数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(keyByte);
		//密钥工厂
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		//转成SecretKey对象
		SecretKey securekey = keyFactory.generateSecret(dks);
		IvParameterSpec iv = new IvParameterSpec(keyByte);
		//解密操作
		//用密钥初始化Cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, securekey, iv);
		byte[] encrypt = cipher.doFinal(data.getBytes(charSet));
		return encrypt;
	}
	
	/**
	 * 解密64编码的加密字符串
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String decryptBase64(String data) throws Exception
	{
		return decryptBase64(data, DEFAULT_KEY);
	}
	/**
	 * 解密64编码的加密字符串
	 * @param data
	 * @param key 密钥
	 * @return
	 * @throws Exception
	 */
	public static String decryptBase64(String data, String key) throws Exception
	{
		return decryptBase64(data, key, UTF8);
	}
	
	/**
	 * 解密64编码的加密字符串
	 * @param data
	 * @param key 密钥
	 * @param charSet 字符集
	 * @return
	 * @throws Exception
	 */
	public static String decryptBase64(String data, String key, String charSet) throws Exception
	{
		byte[] decodeFromString = Base64Utils.decodeFromString(data);
		byte[] keyByte = key.getBytes(charSet);
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		//从原始密钥数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(keyByte);
		//密钥工厂
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		//转成SecretKey对象
		SecretKey securekey = keyFactory.generateSecret(dks);
		IvParameterSpec iv = new IvParameterSpec(keyByte);
		//解密操作
		//用密钥初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, securekey, iv);
		byte[] encrypt = cipher.doFinal(decodeFromString);
		return new String(encrypt, charSet);
	}
	
	/**
	 * 解密16进制的加密字符串
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String decryptHex(String data) throws Exception
	{
		return decryptHex(data, DEFAULT_KEY);
	}
	/**
	 * 解密16进制的加密字符串
	 * @param data
	 * @param key 密钥
	 * @return
	 * @throws Exception
	 */
	public static String decryptHex(String data, String key) throws Exception
	{
		return decryptHex(data, key, UTF8);
	}
	
	/**
	 * 解密16进制的加密字符串
	 * @param data
	 * @param key 密钥
	 * @param charSet 字符集
	 * @return
	 * @throws Exception
	 */
	public static String decryptHex(String data, String key, String charSet) throws Exception
	{
		byte[] bytedata = converHexString(data);
		byte[] keyByte = key.getBytes(charSet);
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		//从原始密钥数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(keyByte);
		//密钥工厂
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		//转成SecretKey对象
		SecretKey securekey = keyFactory.generateSecret(dks);
		IvParameterSpec iv = new IvParameterSpec(keyByte);
		//解密操作
		//用密钥初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, securekey, iv);
		byte[] encrypt = cipher.doFinal(bytedata);
		return new String(encrypt, charSet);
	}
		
}
