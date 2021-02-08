package com.spring.common.cache.util;

import org.apache.commons.lang.ArrayUtils;

public class CacheStringUtil {
	
	/**
	 * 验证字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (null == str || str.equals(""))
			return true;
		return false;
	}

	/**
	 * 清除字符串两端的特殊字符
	 * 
	 * 如："//a/b/c//" 处理后 "a/b/c"
	 * 
	 * @param src
	 *            待处理字符串
	 * @param symbol
	 *            特殊符号
	 * @return 处理后的字符串
	 */
	public static String clearFESymbolOfString(String src, char symbol) {
		return clearSymbolOfString(clearSymbolOfString(src, symbol, 0), symbol, 1);
	}

	/**
	 * 清除字符串两端的特殊字符
	 * 
	 * @param src
	 *            待处理字符串
	 * @param symbol
	 *            特殊符号
	 * @param position
	 *            0 -- 前端 1--后端
	 * @return 处理后的字符串
	 */
	public static String clearSymbolOfString(String src, char symbol, int position) {
		if (CacheStringUtil.isEmpty(src))
			return "";
		byte[] byteStr = src.getBytes();
		if (1 == position)
			ArrayUtils.reverse(byteStr);
		int i = 0;
		boolean isContinue = true;
		while (i < byteStr.length && isContinue) {
			if (((char) byteStr[i]) == symbol) {
				byteStr[i] = 0;
			} else {
				isContinue = false;
			}
			i++;

		}
		if (1 == position)
			ArrayUtils.reverse(byteStr);

		return new String(byteStr).trim();
	}

}
