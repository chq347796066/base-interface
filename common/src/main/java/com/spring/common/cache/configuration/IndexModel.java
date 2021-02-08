package com.spring.common.cache.configuration;

import com.github.pagehelper.util.StringUtil;

import java.util.ArrayList;


/**
 *	索引配置模型对应IndexConfiguration.xml
 */
public class IndexModel {

	String entityName;
	
	String cacheName;
	
	ArrayList<String> properites;
	
	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getCacheName() {
		return cacheName;
	}

	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}

	public ArrayList<String> getProperites() {
		return properites;
	}

	public void setProperites(ArrayList<String> properites) {
		this.properites = properites;
	}

	public void addProperty(String property) {
		if (null == properites)
			properites = new ArrayList<String>();
		if (!StringUtil.isEmpty(property))
			properites.add(property);
	}

}
