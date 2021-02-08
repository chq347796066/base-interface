package com.spring.common.cache.configuration;

/**
 *	缓存配置模型对应cacheConfiguration.xml
 */
public class CacheConfig {

	private String cacheName;
	
	private String primaryKey;
	
	private String daoInterface;
	
	private String method;
	
	private Integer timeToIdleSeconds = 0;
	
	private Integer timeToLiveSeconds = 0;
	
	public Integer getTimeToIdleSeconds() {
		return timeToIdleSeconds;
	}

	public void setTimeToIdleSeconds(Integer timeToIdleSeconds) {
		this.timeToIdleSeconds = timeToIdleSeconds;
	}

	public Integer getTimeToLiveSeconds() {
		return timeToLiveSeconds;
	}

	public void setTimeToLiveSeconds(Integer timeToLiveSeconds) {
		this.timeToLiveSeconds = timeToLiveSeconds;
	}
	
	public String getCacheName() {
		return cacheName;
	}

	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
	public String getDaoInterface() {
		return daoInterface;
	}

	public void setDaoInterface(String daoInterface) {
		this.daoInterface = daoInterface;
	}
	
}
