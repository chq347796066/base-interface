package com.spring.common.cache.constants;

public class CacheConstants {

	public static final String INDEX_CACHE = "indexCache";
	
	/** 用户缓存 */
	public static final String USER_CACHE = "userCache";
	
	/** 服务缓存 */
	public static final String SERVICE_CACHE = "serviceCache";
	
	/** 临时缓存 */
	public static final String TEMP_CACHE = "tempCache";
	
	/** 系统缓存 */
	public static final String SYSTEM_CACHE = "systemCache";
	
	/** 大数据缓存 */
	public static final String BIG_DATA_CACHE = "bigdataCache";
	
	/** 缓存配置文件 */
	public static final String EHCACHE_CONFIG = "cache/ehcache.xml";
	
	/** REDIS配置文件 */
	public static final String REDIS_CONFIG = "cache/connect-redis.properties";
	
	/** 服务模式配置文件 */
	public static final String MODEL_CONFIG = "cache/SERVICE_MODEL.xml";
	
	/** 注册服务配置文件 (此配置已废弃暂时保留)*/
	public static final String REGISTRY_SERVICE_CONFIG = "cache/REGISTRY_SERVICE.xml";
	
	/** 注册服务*/
	public static final String REGISTRY_SERVICE_FOLDER= "/services";
	
	/** 缓存数据配置文件 */
	public static final String CACHE_CONFIG = "cache/CacheConfiguration.xml";
	
	/** 实体索引配置信息 */
	public static final String INDEX_CONFIG = "cache/IndexConfiguration.xml";
	
	// 平台缓存对象Key
	public static final String PROPERTY_INDEX = "$$PROPERTY_INDEX$$";
	
	
}
