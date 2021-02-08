package com.spring.common.cache.util;

import com.spring.common.cache.CacheManagerFactory;
import com.spring.common.cache.ICacheManager;
import com.spring.common.cache.ICustCache;
import com.spring.common.cache.constants.CacheConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CacheUtil {
	static Logger logger = LoggerFactory.getLogger(CacheUtil.class);

	private static ICacheManager cache;

	private static Object lock = new Object();

	private static ICacheManager getCacheManager() {
		if (cache == null) {
			synchronized (lock) {
				try {
					cache = CacheManagerFactory.getInstance();
				} catch (Throwable t) {
					logger.error("Failure during static initialization", t);
				}
			}
		}
		return cache;
	}

	/**
	 * 获取缓存
	 * 
	 * @param cacheName
	 * @return
	 */
	public static ICustCache getCache(String cacheName) {
		return getCacheManager().getCache(cacheName);
	}

	/**
	 * 获取系统缓存
	 * 
	 * @param key
	 * @return
	 */
	public static Object getSysCahce(Object key) {
		return getCache(CacheConstants.SYSTEM_CACHE).get(key);
	}

	/**
	 * 获取用户缓存
	 * 
	 * @param key
	 * @return
	 */
	public static Object getUserCahce(Object key) {
		return getCache(CacheConstants.USER_CACHE).get(key);
	}

	/**
	 * 获取服务缓存
	 * 
	 * @param key
	 * @return
	 */
	public static Object getServiceCahce(Object key) {
		return getCache(CacheConstants.SERVICE_CACHE).get(key);
	}

	/**
	 * 添加系统缓存信息
	 * 
	 * @param key
	 * @param value
	 */
	public static void putSysCahce(Object key, Object value) {
		getCache(CacheConstants.SYSTEM_CACHE).put(key, value);
	}

	/**
	 * 添加用户缓存信息
	 * 
	 * @param key
	 * @return
	 */
	public static void putUserCahce(Object key, Object value) {
		getCache(CacheConstants.USER_CACHE).put(key, value);
	}

	/**
	 * 添加服务缓存 信息
	 * 
	 * @param key
	 * @return
	 */
	public static void putServiceCahce(Object key, Object value) {
		getCache(CacheConstants.SERVICE_CACHE).put(key, value);
	}

}
