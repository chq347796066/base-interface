package com.spring.baseinfo.config;

import java.io.InputStream;

import com.spring.common.StringUtil;
import com.spring.common.cache.CacheManagerFactory;
import com.spring.common.cache.configuration.CacheConfigLoader;
import com.spring.common.cache.configuration.IndexConfigLoader;
import com.spring.common.cache.constants.CacheConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 描述: 初始化管理，需要放值在
 * 依赖org.springframework.context.ApplicationContext
 * 对象取值
 */
public class ICIPLoader {
	
	private static final Log logger = LogFactory.getLog(ICIPLoader.class);
	
	private String filePath;
	
	public ICIPLoader(String filePath){
		this.filePath = filePath;
	}
	
	public void Initialized() {
		initCache();
		//initAsyncComponent();
		//ConsumerThreadServer.init();
	}

	public void destroyed() {
		CacheManagerFactory.destroy();
		//AsyncProcessorHolder.dispose();
		//ConsumerThreadServer.dispose();
	}
	
	private void initCache() {
		logger.info("初始化缓存组件: CacheInit- #初始化缓存信息  开始------------------");
		String path = StringUtil.isEmpty(filePath)?getClassPath():filePath;
		try {
			CacheManagerFactory.create(path + CacheConstants.EHCACHE_CONFIG);
			CacheManagerFactory.getInstance().registerCacheConfigLoader(new CacheConfigLoader(getClassPath() + CacheConstants.CACHE_CONFIG));
			CacheManagerFactory.getInstance().registerCacheIndexLoader(new IndexConfigLoader(getClassPath() + CacheConstants.INDEX_CONFIG));
		} catch (Exception e) {
			CacheManagerFactory.create(getClassPath(CacheConstants.EHCACHE_CONFIG));
			CacheManagerFactory.getInstance().registerCacheConfigLoader(new CacheConfigLoader(getClassPath(CacheConstants.CACHE_CONFIG)));
			CacheManagerFactory.getInstance().registerCacheIndexLoader(new IndexConfigLoader(getClassPath(CacheConstants.INDEX_CONFIG)));
		}
		CacheManagerFactory.getInstance().reloadAll();
		logger.info("初始化缓存组件: CacheInit- #初始化缓存信息  结束------------------");
	}
	
	private void initAsyncComponent() {
		logger.info("初始化异步队列: initAsyncComponent- #启动异步监听  开始------------------");
		//AsyncProcessorHolder.start();
		logger.info("初始化异步队列: initAsyncComponent- #启动异步监听  结束------------------");
	}
	
	private String getClassPath(){  
		return this.getClass().getResource("/").getPath();
	}
	
	private InputStream getClassPath(String path){
		return this.getClass().getResourceAsStream("/"+path);
	}
	
}
