package com.spring.common.cache.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.spring.common.cache.*;
import com.spring.common.cache.configuration.CacheConfigLoader;
import com.spring.common.cache.configuration.ConfigurateLoader;
import com.spring.common.cache.configuration.IndexConfigLoader;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.ObjectExistsException;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Searchable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class EhcacheManager implements ICacheManager {
	
	private static final Log logger = LogFactory.getLog(EhcacheManager.class);

	/** Ehcache管理器类 */
	private CacheManager mgr;
	
	/** 系统启动时，加载配置信息类 */
	private Map<String, ConfigurateLoader<?>> loaders = new HashMap<String, ConfigurateLoader<?>>();

	@SuppressWarnings("rawtypes")
	private ConfigurateLoader indexLoader;
	
	public String getName() {
		return this.mgr.getName();
	}
	
	/*public void setName(String name) {
		this.mgr.setName(name);
	}*/
	
	/**
	 * CacheManager中注册缓存分区配置
	 */
	public ICustCache addCache(String name, ICacheConfig cacheConfig) {
		CacheConfiguration config = buildCacheConfig(cacheConfig);
		Cache cache = new Cache(config);
		try {
			mgr.addCache(cache);
			return new EhcacheCache(cache);
		} catch (IllegalStateException ex) {
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		} catch (ObjectExistsException ex) {
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		} catch (CacheException ex) {
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		}
	}
	
	public void addCache(String name){
		try {
			this.mgr.addCache(name);
		} catch (IllegalStateException ex) {
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		} catch (ObjectExistsException ex) {
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		} catch (CacheException ex) {
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		}
	}
	
	/**
	 * 构建配置信息
	 * 
	 * @param cacheConfig
	 * @return
	 */
	protected CacheConfiguration buildCacheConfig(ICacheConfig cacheConfig){
		CacheConfiguration ehConfig = new CacheConfiguration();
		if(null == cacheConfig)
			return ehConfig;
		EhcacheConfig config = (EhcacheConfig)cacheConfig;
		ehConfig.setEternal(false);
		ehConfig.setEternal(false);
		ehConfig.setName(config.getName());
		ehConfig.setTimeToIdleSeconds(config.getTimeToIdleSeconds());
		ehConfig.setTimeToLiveSeconds(config.getTimeToLiveSeconds());
		ehConfig.setMaxEntriesInCache(config.getMaxElementsInMemory());
		Searchable searchable = new Searchable();
		searchable.allowDynamicIndexing(true);
		ehConfig.addSearchable(searchable);
		return ehConfig;
	}
	
	/**
	 *	检查缓存分区是否存在 
	 */
	public boolean cacheExists(String cacheName) {
		try {
			return this.mgr.cacheExists(cacheName);
		} catch (IllegalStateException ex) {
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		}
	}
	
	/**
	 *	清空对应缓存分区 
	 */
	public void clear(String cacheName) {
		try {
			this.mgr.getCache(cacheName).removeAll();
		} catch (IllegalStateException ex) {
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		} catch (ObjectExistsException ex) {
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		} catch (CacheException ex) {
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		}
	}
	
	/**
	 *	清空所有缓存分区
	 */
	public void clearAll() {
		try {
			this.mgr.clearAll();
		} catch (CacheException ex) {
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		}
	}
	
	public void clearAllStartingWith(String prefix) {
		try {
			this.mgr.clearAllStartingWith(prefix);
		} catch (CacheException ex) {
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		}
	}
	
	/**
	 *	创建缓存管理器对应ehcache.xml 
	 */
	public ICacheManager create(String filename) {
		try {
			this.mgr = CacheManager.create(filename);
			return this;
		} catch (CacheException ex) {
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		}
	}
	
	/**
	 *	创建缓存管理器对应ehcache.xml 
	 */
	public ICacheManager create(InputStream inputStream) {
		try {
			this.mgr = CacheManager.create(inputStream);
			return this;
		} catch (CacheException ex) {
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		}
	}
	
	/**
	 *	创建缓存管理器 对应ConfigurateLoader
	 */
	public ICacheManager create(String filename, ConfigurateLoader<?>[] loaders) {
		create(filename);
		for (int i = 0; i < loaders.length; i++) {
			registerCacheConfigLoader(loaders[i]);
		}
		return this;
	}
	
	/**
	 *	创建缓存管理器 对应ConfigurateLoader
	 */
	public ICacheManager create(InputStream inputStream, ConfigurateLoader<?>[] loaders) {
		create(inputStream);
		for (int i = 0; i < loaders.length; i++) {
			registerCacheConfigLoader(loaders[i]);
		}
		return this;
	}
	
	/**
	 *	销毁缓存分区 
	 */
	public void destory(String cacheName) {
		this.clear(cacheName);
		try {
			this.mgr.getCache(cacheName).dispose();
		} catch (IllegalStateException ex) {
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		} catch (ClassCastException ex) {
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		}
	}
	
	/**
	 *	销毁所有缓存实例
	 */
	public void destoryAll() {
		this.clearAll();
		try {
			for (String cname : this.mgr.getCacheNames()) {
				this.mgr.getCache(cname).dispose();
			}
		} catch (IllegalStateException ex) {
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		} catch (ClassCastException ex) {
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		}
	}
	
	/**
	 *	获取缓存实例
	 */
	public ICustCache getCache(String name) {
		try {
			Cache cache = this.mgr.getCache(name);
			return new EhcacheCache(cache);
		} catch (IllegalStateException ex) {
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		} catch (ClassCastException ex) {
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		}
	}
	
	public String[] getCacheNames() {
		try {
			return this.mgr.getCacheNames();
		} catch (IllegalStateException ex) {
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		}
	}
	
	public ICustCache[] getCaches() {
		ArrayList<ICustCache> list = new ArrayList<ICustCache>();
		for (String cname : this.getCacheNames()) {
			list.add(this.getCache(cname));
		}
		return (ICustCache[]) list.toArray();
	}
	
	/**
	 *	注册缓存配置 
	 */
	public void registerCacheConfigLoader(ConfigurateLoader<?> loader) {
		if (loaders.containsKey(loader.getClass().getName()))
			return;
		loaders.put(loader.getClass().getName(), loader);
	}
	
	/**
	 * 注册索引配置
	 */
	public void registerCacheIndexLoader(ConfigurateLoader<?> loader) {
		if (loader instanceof IndexConfigLoader)
			this.indexLoader = loader;
	}
	
	/**
	 *	根据分区重载缓存 
	 */
	public void reload(String cacheName) {
		for (String key : loaders.keySet()) {
			ConfigurateLoader<?> cacheConfigLoader = loaders.get(key);
			if (cacheConfigLoader instanceof CacheConfigLoader) {
				((CacheConfigLoader) cacheConfigLoader).setCacheName(cacheName);
				cacheConfigLoader.execute();
			}
		}
	}
	
	/**
	 *	重载所有缓存 
	 */
	public void reloadAll() {
		this.clearAll();
		indexLoader.execute();
		this.registerCommonDynamicAttributesExtractor(new CommonDynamicAttributesExtractor());
		for (String key : loaders.keySet()) {
			loaders.get(key).execute();
		}
	}
	
	public void shutDown() {
		destoryAll();
		this.loaders.clear();
		this.indexLoader = null;
		this.mgr.shutdown();
	}
	
	public ArrayList<IStatistics> getStatisticses() {
		ArrayList<IStatistics> ist = new ArrayList<IStatistics>();
		for (String cacheName : this.getCacheNames()) {
			ist.add(this.getCache(cacheName).getStatistics());
		}
//		return  (IStatistics[]) ist.toArray();
		return ist;
	}
	
	public IStatistics getStatistics(String cacheName) {
		IStatistics ist = this.getCache(cacheName).getStatistics();
		return ist;
	}
	
	/**
	 * 注册可查询属性 
	 */
	public void registerCommonDynamicAttributesExtractor(IDynamicAttributesExtractor extractor) {
		String[] cacheNames = this.getCacheNames();
		for (int i = 0; i < cacheNames.length; i++) {
			this.getCache(cacheNames[i]).registerDynamicAttributesExtractor(extractor);
		}
	}
	
	public void registerDynamicAttributesExtractors(Map<String, IDynamicAttributesExtractor> extractors) {
		for (String key : extractors.keySet()) {
			this.getCache(key).registerDynamicAttributesExtractor(extractors.get(key));
		}
	}

	@Override
	public ICacheManager create(String filename, String configurationFileName) {
		return null;
	}
	
	@Override
	public ICacheManager create(InputStream inputStream, String configurationFileName) {
		return null;
	}
	
}
