package com.spring.common.cache.configuration;

import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.spring.common.cache.CacheManagerFactory;
import com.spring.common.cache.ICustCache;
import com.spring.common.cache.constants.CacheConstants;
import com.spring.common.util.ArrayUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 初始化加载时完善此类
 */
public class CacheConfigLoader extends ConfigurateLoader<CacheConfig> {
	private static final Log logger = LogFactory.getLog(CacheConfigLoader.class);

	private static String PRIMARY_KEY = "auto";

	public CacheConfigLoader() {
	}

	public CacheConfigLoader(String filepath) {
		this.filepath = filepath;
	}
	
	public CacheConfigLoader(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	/**
	 * 读取CacheConfiguration配置文件中的信息
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected Map<Object, CacheConfig> loadCacheItems() {
		Map configs  = null;
		if(filepath != null){
			 configs = XmlConfig.readConfig(this.filepath, CacheConfig.class);
		}else {
			 configs = XmlConfig.readConfig(getClassPath(CacheConstants.CACHE_CONFIG), CacheConfig.class);
		}
		return configs;
	}

	@Override
	protected void putCache(Map<Object, CacheConfig> cacheItems) {
		Set<String> cacheSet = new HashSet<String>();
		//#查询缓存数据
		Set<String> oldKeys = queryKeys(cacheItems,cacheSet);
		Set<Object> newKeys = new HashSet<Object>();
		for (Object key : cacheItems.keySet()) {
			CacheConfig config = cacheItems.get(key);
			ICustCache cache = CacheManagerFactory.getInstance().getCache(config.getCacheName());
			// 加载数据，保存数据到缓存中
			processSql(config, cache,newKeys);
		}
		//清楚缓存数据
		 clearCache(oldKeys,cacheSet,newKeys);
	}
	
	/**
	 * 
		 * @Description: 查询缓存中存在的key
		 * @param @param cacheItems
		 * @param @param cacheSet
		 * @param @return   
		 * @return Set<String>  
		 * @throws
		 * @author
	 */
	protected Set<String> queryKeys(Map<Object, CacheConfig> cacheItems,Set<String> cacheSet){
		Set<String> setKeys = new HashSet<String>();
		for (Object key : cacheItems.keySet()) {
			CacheConfig config = cacheItems.get(key);
			String cacheName = config.getCacheName();
			cacheSet.add(cacheName);
		}
		for(String cacheName : cacheSet){
			ICustCache cache = CacheManagerFactory.getInstance().getCache(cacheName);
			Collection<String>  keys = cache.queryKeys("*");
			setKeys.addAll(keys);
		}
		return setKeys;
	}

	/**
	 * @Description: 每次刷新情况对应缓存
	 * @param @param map
	 * @return void
	 * @throws
	 * @author
	 */
	protected void clearCache(Set<String> oldKeys,Set<String> cacheSet,Set<Object> newKeys) {
		oldKeys.removeAll(newKeys);
		if(oldKeys.isEmpty()){
			return;
		}
		for(String cacheName : cacheSet){
			ICustCache cache = CacheManagerFactory.getInstance().getCache(cacheName);
			cache.removeAll(oldKeys);
		}
	}

	private void processSql(CacheConfig config, ICustCache cache,Set<Object> newKeys) {
		/*Class<?> dao = CacheClassUtil.getEntityClass(config.getDaoInterface());
		String method = config.getMethod();
		DBInDataModel dataModel = new DBInDataModel(dao, method, null);
		Object[] objs = (Object[]) SourceAccessUtil.operate(dataModel);
		addDataToCache(config, cache, objs,newKeys);*/
	}

	private void addDataToCache(CacheConfig config, ICustCache cache, Object[] objs,Set<Object> newKeys) {
		if (!ArrayUtil.isEmpty(objs))
			if (config.getPrimaryKey().equals(PRIMARY_KEY)) {
				//  自增key值未实现
			} else {
				for (int i = 0; i < objs.length; i++) {
					Object key = ClassUtil.getFieldValue(objs[i], config.getPrimaryKey());
					cache.put(key, objs[i], config.getTimeToIdleSeconds(), config.getTimeToLiveSeconds());
					logger.error("存入缓存key：" + key + "存入缓存value：" + objs[i] + "");
					newKeys.add(key);
				}
			}
	}

}
