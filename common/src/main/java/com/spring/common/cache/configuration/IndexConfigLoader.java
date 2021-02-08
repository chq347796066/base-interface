package com.spring.common.cache.configuration;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.spring.common.cache.CacheManagerFactory;
import com.spring.common.cache.ICustCache;
import com.spring.common.cache.constants.CacheConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class IndexConfigLoader extends ConfigurateLoader<IndexModel>{
	
	private static final Log logger = LogFactory.getLog(IndexConfigLoader.class);

	public IndexConfigLoader(String filepath) {
		this.filepath = filepath;
		this.cacheName = CacheConstants.INDEX_CACHE;
	}
	
	public IndexConfigLoader(InputStream inputStream){
		this.inputStream = inputStream;
		this.cacheName = CacheConstants.INDEX_CACHE;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })	
	@Override
	protected Map<Object, IndexModel> loadCacheItems() {
		Map configs  = null;
		if(filepath != null){
			 configs = XmlConfig.readIndex(this.filepath, IndexModel.class);
		}else {
			 configs = XmlConfig.readIndex(getClassPath(CacheConstants.CACHE_CONFIG), IndexModel.class);
		}
		return configs;
	}

	@Override
	protected void putCache(Map<Object, IndexModel> cacheItems) {
		ICustCache cache = (ICustCache) CacheManagerFactory.getInstance().getCache(cacheName);
		cache.clear();
		Map<String, Object> indexes = new HashMap<>();
		if(cacheItems !=null){
			for (Object key : cacheItems.keySet()) {
				IndexModel model = cacheItems.get(key);
				if (model == null) {
					continue;
				}
				if (model.getProperites()==null) {
					continue;
				}
				for (String name : model.getProperites()) {
					indexes.put(name, null);
				}
				// indexconfiguration.xml中定义的实体和所有索引
				cache.put(model.entityName, model.getProperites());
				for(int i = 0; i < model.getProperites().size(); i++){
					logger.info("加载缓存索引：" + model.entityName + model.getProperites().get(i));
				}
			}
			// 所有索引名
			cache.put(CacheConstants.PROPERTY_INDEX, indexes);
		}
	}

}
