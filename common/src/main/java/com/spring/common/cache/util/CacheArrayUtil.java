package com.spring.common.cache.util;

import net.sf.ehcache.search.Results;
import net.sf.ehcache.search.impl.ResultImpl;


public class CacheArrayUtil {

	/**
	 * 转换Ehcache缓存结果
	 * 
	 * @param result
	 *            Ehcache缓存结果
	 * @return返回转换结果数组
	 */
	public static Object[] convert(Results result) {
		if (!result.hasValues())
			return null;
		Object[] dest = new Object[result.all().size()];
		Object[] results = result.all().toArray();
		CacheArrayUtil.convert(results, dest, new IConverter<Object, Object>() {
			public Object convert(Object src) {
				return ((ResultImpl) src).getValue();
			}
		});
		return dest;

	}

	/**
	 * 数组间转换类型
	 * 
	 * @param src
	 *            原数组
	 * @param vs
	 *            目标数组，使用前需要先初始化
	 * @param convert
	 *            转换接口
	 * 
	 */
	public static <T, D> void convert(T[] src, D[] vs, IConverter<T, D> convert) {
		int length = src.length;
		if (vs.length < length) {
			length = vs.length;
		}
		for (int i = 0; i < length; i++) {
			vs[i] = convert.convert(src[i]);
		}
	}

}
