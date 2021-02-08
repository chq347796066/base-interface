package com.spring.common.cache;

import java.io.InputStream;

import com.spring.common.StringUtil;
import com.spring.common.cache.configuration.ConfigurateLoader;
import com.spring.common.cache.impl.EhcacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 缓存管理器工厂类
 */
public class CacheManagerFactory {
	static Logger logger = LoggerFactory.getLogger(CacheManagerFactory.class);

	private static ICacheManager manager = null;

	private static Object lock = new Object();

	private static volatile String ehcache;

	private static InputStream ehcaheInputStream;

	private static String redispath;

	public static void setEhcache(String ehcache) {
		CacheManagerFactory.ehcache = ehcache;
	}

	public static ICacheManager getInstance() {
		if(manager != null){
			return manager;
		}
		while (null == manager) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
		}
		return manager;
	}

	/**
	 * 创建缓存管理器 ecache缓存
	 * 
	 * @param filepath
	 */
	public static void create(String filepath) {
		if (null == manager)
			if (!StringUtil.isBlank(filepath))
				ehcache = filepath;
		synchronized (lock) {
			if (null == manager)
				manager = new EhcacheManager().create(ehcache);
		}
	}

	/**
	 * 创建缓存管理器 ecache缓存
	 * 
	 * @param filepath
	 */
	public static void create(InputStream inputStream) {
		if (null == manager)
			if (inputStream != null)
				ehcaheInputStream = inputStream;
		synchronized (lock) {
			if (null == manager)
				manager = new EhcacheManager().create(ehcaheInputStream);
		}
	}

	/**
	 * 
	 * @Description: REDIS 缓存
	 * @param @param filepath
	 * @param @param loaders
	 * @return void
	 * @throws
	 * @author
	 */
	@SuppressWarnings("rawtypes")
	public static void create(String filepath, ConfigurateLoader[] loaders) {
		if (null == manager) {
			if (!StringUtil.isBlank(filepath))
				ehcache = filepath;
			synchronized (lock) {
				if (null == manager)
					manager = new EhcacheManager().create(filepath, loaders);
			}
		}
	}

	/**
	 * 
	 * @Description: REDIS 缓存
	 * @param @param filepath
	 * @param @param loaders
	 * @return void
	 * @throws
	 * @author
	 */
	@SuppressWarnings("rawtypes")
	public static void create(InputStream inputStream, ConfigurateLoader[] loaders) {
		if (null == manager) {
			if (inputStream != null)
				ehcaheInputStream = inputStream;
			synchronized (lock) {
				if (null == manager)
					manager = new EhcacheManager().create(ehcaheInputStream, loaders);
			}
		}
	}

	public static void index(ConfigurateLoader<?> loader) {
		manager.registerCacheIndexLoader(loader);
	}

	public static void destroy() {
		manager.shutDown();
		manager = null;
		lock = null;
	}

}
