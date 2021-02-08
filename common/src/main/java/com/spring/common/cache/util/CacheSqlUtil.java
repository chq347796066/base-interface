package com.spring.common.cache.util;

public class CacheSqlUtil {

	/**
	 *	根据配置文件路径取得要执行的sql ID,即DAO接口名
	 *	@param String 配置文件中namingSql
	 */
	public static String getSqlId(String namingSql){
		int i = namingSql.lastIndexOf(".");
		String sqlId = namingSql.substring(i+1);
		return sqlId;
	}
	
}
