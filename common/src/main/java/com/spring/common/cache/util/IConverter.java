package com.spring.common.cache.util;

public interface IConverter<T, D> {
	/**
	 * 类型转换
	 * 
	 * @param src
	 *            被转换对象
	 * @return 转换后的对象
	 */
	D convert(T src);
	
}
