package com.spring.common.cache.impl;


import com.spring.common.cache.ICacheConfig;

/**
 *	对应ehcache.xml 
 */
public class EhcacheConfig implements ICacheConfig {

	String name;
	
	int maxElementsInMemory;
	
	boolean eternal;
	
	long timeToIdleSeconds;
	
	long timeToLiveSeconds;
	
	boolean overflowToDisk;
	
	boolean allowDynamicIndexing;
	
	public boolean isAllowDynamicIndexing() {
		return allowDynamicIndexing;
	}

	public void setAllowDynamicIndexing(boolean allowDynamicIndexing) {
		this.allowDynamicIndexing = allowDynamicIndexing;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMaxElementsInMemory() {
		return maxElementsInMemory;
	}

	public void setMaxElementsInMemory(int maxElementsInMemory) {
		this.maxElementsInMemory = maxElementsInMemory;
	}

	public boolean isEternal() {
		return eternal;
	}

	public void setEternal(boolean eternal) {
		this.eternal = eternal;
	}

	public long getTimeToIdleSeconds() {
		return timeToIdleSeconds;
	}

	public void setTimeToIdleSeconds(long timeToIdleSeconds) {
		this.timeToIdleSeconds = timeToIdleSeconds;
	}

	public long getTimeToLiveSeconds() {
		return timeToLiveSeconds;
	}

	public void setTimeToLiveSeconds(long timeToLiveSeconds) {
		this.timeToLiveSeconds = timeToLiveSeconds;
	}

	public boolean isOverflowToDisk() {
		return overflowToDisk;
	}

	public void setOverflowToDisk(boolean overflowToDisk) {
		this.overflowToDisk = overflowToDisk;
	}
	
}
