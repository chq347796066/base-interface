package com.spring.common.util.arithmetic;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * @Description: BigDecimal 类型处理
 * @author
 * @date 2016年4月9日 下午2:11:43
 * @update
 */
@Slf4j
public class BigDecimalUtil {
	

	/**
	 * @Description: 加法运算 a1+a2 四舍五入，如果其中任意一个为空，则直接返回另一个
	 * @param String 第一个字符串
	 * @param String 第二个字符串
	 * @param decimal
	 *            返回保留几位小数位
	 * @return String
	 */
	public static String add(String a1, String a2, int decimal) {
		if(StringUtils.isEmpty(a1)){
			return a2;
		}
		if(StringUtils.isEmpty(a2)){
			return a1;
		}
		BigDecimal firstNum = new BigDecimal(a1);
		BigDecimal secondNum = new BigDecimal(a2);
		BigDecimal resultNum = firstNum.add(secondNum);
		return resultNum.setScale(decimal, BigDecimal.ROUND_HALF_UP).toString();
	}

	/**
	 * @Description: String 减法运算 a1-a2
	 * @param String
	 * @param String
	 * @return String
	 * @author
	 */
	public static String sub(String a1, String a2, int decimal) {
		BigDecimal firstNum = new BigDecimal(a1);
		BigDecimal secondNum = new BigDecimal(a2);
		BigDecimal resultNum = firstNum.subtract(secondNum);
		return resultNum.setScale(decimal, BigDecimal.ROUND_HALF_UP).toString();
	}

	/**
	 * @Description: String 乘法运算 a1*a2
	 * @param String
	 * @param String
	 * @return String
	 * @author
	 */
	public static String mul(String a1, String a2, int decimal) {
		BigDecimal firstNum = new BigDecimal(a1);
		BigDecimal secondNum = new BigDecimal(a2);
		BigDecimal resultNum = firstNum.multiply(secondNum);
		return resultNum.setScale(decimal, BigDecimal.ROUND_HALF_UP).toString();
	}

	/**
	 * @Description: BigDecimal 除法运算 a1/a2
	 * @param String
	 * @param int scale 小数位数
	 * @param String
	 * @param int roundingMode 默认赋值5
	 * @return BigDecimal
	 * @author
	 */

	// int ROUND_UP = 0; 总是在非 0 舍弃小数(即截断)之前增加数字。
	// int ROUND_DOWN = 1; 从不在舍弃(即截断)的小数之前增加数字。
	// int ROUND_CEILING = 2; 如果 BigDecimal 是正的，则做 ROUND_UP 操作；如果为负，则做
	// ROUND_DOWN 操作。
	// int ROUND_FLOOR = 3; 如果 BigDecimal 为正，则作 ROUND_UP ；如果为负，则作 ROUND_DOWN 。
	// int ROUND_HALF_UP = 4; 若舍弃部分>=.5，则作 ROUND_UP ；否则，作 ROUND_DOWN 。
	// int ROUND_HALF_DOWN = 5; 若舍弃部分> .5，则作 ROUND_UP；否则，作 ROUND_DOWN 。
	// int ROUND_HALF_EVEN =6; 如果舍弃部分左边的数字为奇数，则作 ROUND_HALF_UP ；如果它为偶数，则作
	// ROUND_HALF_DOWN 。
	// int ROUND_UNNECESSARY = 7; 该“伪舍入模式”实际是指明所要求的操作必须是精确的，，因此不需要舍入操作。

	public static BigDecimal div(String a1, int scale, String a2, int roundingMode) {
		BigDecimal firstNum = new BigDecimal(a1);
		BigDecimal secondNum = new BigDecimal(a2);
		BigDecimal resultNum = firstNum.divide(secondNum, scale, roundingMode);
		return resultNum;
	}
	
	public static BigDecimal div(Double a1, int scale, Double a2, int roundingMode) {
		BigDecimal firstNum = new BigDecimal(a1);
		BigDecimal secondNum = new BigDecimal(a2);
		BigDecimal resultNum = firstNum.divide(secondNum, scale, roundingMode);
		return resultNum;
	}

	/**
	 * @Description: String 比较大小，a1小于a2，返回-1 ; a1=a2，返回0; a1大于a2，返回1
	 * @param String
	 * @param String
	 * @return int
	 * @author
	 */
	public static int compareToOther(String a1, String a2) {
		BigDecimal firstNum = new BigDecimal(a1);
		BigDecimal secondNum = new BigDecimal(a2);

		return firstNum.compareTo(secondNum);
	}

	/**
	 * 提供精确的加法运算。
	 * 
	 * @param v1
	 *            被加数
	 * @param v2
	 *            加数
	 * @return 两个参数的和
	 */
	public static String adds(String v1, String v2){
		try{
			BigDecimal b1 = new BigDecimal(StringUtils.isEmpty(v1)?"0":v1);
			BigDecimal b2 = new BigDecimal(StringUtils.isEmpty(v2)?"0":v2);
			return b1.add(b2).toString();
		}catch(Exception e){
			log.error("adds execute field v1="+v1+" v2="+v2, e);
			throw e;
		}
	}
	
	public static Double adds(Double v1, Double v2) {
		if(v1 == null){
			v1 = 0.00;
		}
		if(v2 == null){
			v2 = 0.00;
		}
		//eager　改动double转BigDecimal必须用valueOf形式,否则出现精度莫名变长问题 
		try{
			return BigDecimal.valueOf(v1).add(BigDecimal.valueOf(v2)).doubleValue();
		}catch(Exception e){
			log.error("adds(Double v1, Double v2) execute field v1="+v1+" v2="+v2,e);
			throw e;
		}
	}

	/**
	 * 两数相加保留两位小数
	 * 
	 * @param v1
	 * @param v2
	 * @param decimal
	 * @return
	 */
	public static String addSavePart(String v1, String v2, int decimal) {
		try{
			BigDecimal b1 = new BigDecimal(StringUtils.isEmpty(v1) ? "0" : v1);
			BigDecimal b2 = new BigDecimal(StringUtils.isEmpty(v2) ? "0" : v2);
			BigDecimal add = b1.add(b2);
			return add.setScale(decimal, BigDecimal.ROUND_HALF_UP).toString();
		}catch(Exception e){
			log.error("addSavePart execute field v1="+v1+" v2="+v2+",decimal="+decimal,e);
			throw e;
		}
	}

	/**
	 * 提供精确的加法运算。
	 * 
	 * @param v1
	 *            被加数
	 * @param v2
	 *            加数
	 * @return 两个参数的和
	 */
	public static String adds(String v1, String v2, String v3) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		BigDecimal b3 = new BigDecimal(v3);
		return (b1.add(b2)).add(b3).toString();
	}

	/**
	 * 提供精确的减法运算。
	 * 
	 * @param v1
	 *            被减数
	 * @param v2
	 *            减数
	 * @return 两个参数的差
	 */
	public static String subs(String v1, String v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.subtract(b2).toString();
	}

	/**
	 * 提供精确的乘法运算。
	 * 
	 * @param v1
	 *            被乘数
	 * @param v2
	 *            乘数
	 * @return 两个参数的积
	 */
	public static String muls(String v1, String v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);

		return b1.multiply(b2).toString();
	}

	/**
	 * 提供精确的乘法运算。
	 * 
	 * @param v1
	 *            被乘数
	 * @param v2
	 *            乘数
	 * @return 两个参数的积
	 */
	public static String mulsSub(String v1, String v2, String v3, int decimal) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		BigDecimal b3 = new BigDecimal(v3);
		BigDecimal b4 = b1.multiply(b2);
		BigDecimal subtract = b4.subtract(b3);
		return subtract.setScale(decimal, BigDecimal.ROUND_HALF_UP).toString();
	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static String div(String v1, String v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.divide(b2).toString();
	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static String divs(String v1, String v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).toString();
	}

	/**
	 * 幂运算
	 * 
	 * @param v1
	 *            被乘数
	 * @param v2
	 *            BigDecimal的次幂
	 * @return
	 */
	public static String pow(String v1, int v2) {
		BigDecimal b1 = new BigDecimal(v1);

		return b1.pow(v2).toString();
	}

	public static boolean isZero(String v1) {
		boolean flag = false;
		if (BigDecimal.ZERO.floatValue() == new BigDecimal(StringUtils.isEmpty(v1)?"0":v1).floatValue()) {
			flag = true;
		}
		return flag;
	}
	
}
