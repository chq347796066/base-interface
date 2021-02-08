package com.spring.common.cache;

import com.spring.common.cache.constants.CacheConstants;

/**
 * @Description:缓存帮助类
 * @author  
 * @date 2017年11月7日上午9:08:39
 * @update
 */
public class TempCaheUtil {
	private static ICacheManager cache;

	static {
		cache = CacheManagerFactory.getInstance();
	}

	/**
	 * 获取缓存
	 * 
	 * @param cacheName
	 * @return
	 */
	public static ICustCache getCache(String cacheName) {
		return cache.getCache(cacheName);
	}

	/**
	 * 添加临时缓存信息
	 * 
	 * @param key
	 * @return
	 */
	public static void putTempCahce(Object key, Object value) {
		getCache(CacheConstants.TEMP_CACHE).put(key, value);
	}
	
	/**
	 * 添加系统缓存
	 * 
	 * @param key
	 * @return
	 */
	public static void putSysCahce(Object key, Object value,int timeToLive) {
		getCache(CacheConstants.SYSTEM_CACHE).put(key, value,0,timeToLive);
	}
	
	/**
	 * 获取系统缓存
	 * 
	 * @param key
	 * @return
	 */
	public static Object getSysCahce(Object key) {
		return getCache(CacheConstants.TEMP_CACHE).get(key);
	}
	
	/**
	 * 添加临时缓存信息
	 * 
	 * @param key
	 * @return
	 */
	public static void putTempCahce(Object key, Object value,int timeToLiveSeconds) {
		getCache(CacheConstants.TEMP_CACHE).put(key, value,0,timeToLiveSeconds);
	}

	/**
	 * 获取临时缓存
	 * 
	 * @param key
	 * @return
	 */
	public static Object getTempCahce(Object key) {
		return getCache(CacheConstants.TEMP_CACHE).get(key);
	}

	/**
	 * 删除临时缓存
	 * @param key
	 */
	public static void delTempCahce(Object key){
		delTempCahce(key);
	}
}
