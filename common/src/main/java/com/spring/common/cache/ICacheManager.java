package com.spring.common.cache;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

import com.spring.common.cache.configuration.ConfigurateLoader;

/**
 * 缓存管理器
 */
public interface ICacheManager {

	/**
	 * 注册配置文件加载器，负责将配置于缓存文件中的数据加载至于缓存中
	 * 
	 * @param loader
	 *            加载器
	 */
	void registerCacheConfigLoader(ConfigurateLoader<?> loader);

	/**
	 * 注册索引文件加载器，负责将索引信息加载与缓存中
	 * 
	 * @param loader
	 */
	void registerCacheIndexLoader(ConfigurateLoader<?> loader);

	/**
	 * 
	 * @param extractor
	 */
	void registerCommonDynamicAttributesExtractor(IDynamicAttributesExtractor extractor);

	/**
	 * 
	 * @param extractors
	 */
	void registerDynamicAttributesExtractors(Map<String, IDynamicAttributesExtractor> extractors);

	/**
	 * 创建缓存管理器
	 * 
	 * @param filename
	 *            缓存配置文件的路径
	 * @return ICacheManager 管理器
	 * @throws CachesException
	 */
	ICacheManager create(String filename);
	
	/**
	 * 创建缓存管理器
	 * 
	 * @param filename
	 *            缓存配置文件的路径
	 * @return ICacheManager 管理器
	 * @throws CachesException
	 */
	ICacheManager create(String filename,String configurationFileName);
	
	
	/**
	 * 创建缓存管理器
	 * 
	 * @param inputStream
	 *            缓存配置文件流
	 * @return ICacheManager 管理器
	 * @throws CachesException
	 */
	ICacheManager create( InputStream inputStream,String configurationFileName);

	/**
	 * 创建缓存管理器
	 * 
	 * @param filename
	 *            缓存配置文件的路径
	 * @param loaders
	 *            配置加载器
	 * @return ICacheManager 管理器
	 * @throws CachesException
	 */
	ICacheManager create(String filename, ConfigurateLoader<?>[] loaders);

	/**
	 * 检查缓存是否存在
	 * 
	 * @param cacheName
	 *            缓存名称
	 * @return 存在返回true，否则返回false
	 * @throws CacheException
	 */
	boolean cacheExists(String cacheName);

	/**
	 * 清空缓存中的数据
	 * 
	 * @param cacheName
	 *            缓存名称
	 * @throws CacheException
	 */
	void clear(String cacheName);

	/**
	 * 清空所有缓存中的数据
	 * 
	 * @throws CachesException
	 */
	void clearAll();

	/**
	 * 清空缓存中的数据
	 * 
	 * @param prefix
	 *            缓存名称前缀
	 * @throws CachesException
	 */
	void clearAllStartingWith(String prefix);

	/**
	 * 销毁缓存实例
	 * 
	 * @param cacheName
	 *            缓存名称
	 * @throws CachesException
	 */
	void destory(String cacheName);

	/**
	 * 销毁所有缓存实例
	 * 
	 * @throws CachesException
	 */
	void destoryAll();

	/**
	 * 得到缓存实例
	 * 
	 * @param name
	 *            缓存名称
	 * @return 如果存在返回缓存实例，否则返回Null值
	 * @throws CachesException
	 */
	ICustCache getCache(String name);

	/**
	 * 得到所有缓存的名称
	 * 
	 * @return 如果存在返回缓存实例名称数组，否则返回NULL
	 * @throws CachesException
	 */
	String[] getCacheNames();

	/**
	 * 返回所有缓存实例统计信息
	 * 
	 * @return 如果存在返回所有缓存实例统计信息，否则返回NULL
	 */
	ArrayList<IStatistics> getStatisticses();

	/**
	 * 返回缓存实例统计信息
	 * 
	 * @param cacheName
	 *            缓存名称
	 * @return 如果存在返回缓存实例统计信息，否则返回NULL
	 */
	IStatistics getStatistics(String cacheName);

	/**
	 * 返回所有缓存实例
	 * 
	 * @return 如果存在返回返回缓存实例数组，否则返回NULL
	 */
	ICustCache[] getCaches();

	/**
	 * 返回管理器名称
	 * 
	 * @return 管理器名称
	 */
	String getName();

	/**
	 * 设置管理器名称
	 * 
	 * @param name
	 *            管理器名称
	 */
	//void setName(String name);

	/**
	 * 创建新缓存实例
	 * 
	 * @param name
	 *            缓存实例名称
	 * @throws CachesException
	 */
	void addCache(String name);

	/**
	 * 创建新缓存实例
	 * 
	 * @param name
	 *            缓存实例名称
	 * @param cacheConfig
	 *            缓存配置信息
	 * @return 缓存实例
	 * @throws CachesException
	 */
	ICustCache addCache(String name, ICacheConfig cacheConfig);

	/**
	 * 重置缓存实例中的数据，至系统启动状态
	 * 
	 * @param cacheName
	 *            缓存实例名称
	 */
	void reload(String cacheName);

	/**
	 * 重置所有缓存实例中的数据，至系统启动状态
	 */
	void reloadAll();

	/**
	 * 关闭管理器
	 */
	void shutDown();
	
}
