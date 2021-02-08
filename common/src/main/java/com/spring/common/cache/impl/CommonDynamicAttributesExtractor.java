package com.spring.common.cache.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.spring.common.cache.CacheManagerFactory;
import com.spring.common.cache.IDynamicAttributesExtractor;
import com.spring.common.cache.constants.CacheConstants;
import com.spring.common.cache.util.CacheClassUtil;
import net.sf.ehcache.Element;
import net.sf.ehcache.search.attribute.DynamicAttributesExtractor;

/**
 * 可查询属性实现
 * 
 */
public class CommonDynamicAttributesExtractor implements IDynamicAttributesExtractor,
		DynamicAttributesExtractor {

	@Override
	public Map<String, Object> attributesFor(Element element) {
		Object value = element.getObjectValue();
		//取同一个cacheItem内所有定义的索引
		@SuppressWarnings("unchecked")
		ArrayList<String> temp = (ArrayList<String>) CacheManagerFactory.getInstance().getCache(CacheConstants.INDEX_CACHE).get(value.getClass().getName());
		
		@SuppressWarnings("unchecked")
		Map<String,Object> indexes = (Map<String, Object>) ((HashMap<String, Object>) CacheManagerFactory.getInstance().getCache(CacheConstants.INDEX_CACHE)
				.get(CacheConstants.PROPERTY_INDEX)).clone();
		
		if(null != temp && 0 < temp.size()){
			@SuppressWarnings("unchecked")
			ArrayList<String> properties = (ArrayList<String>) temp.clone();
			if(null != properties && 0 < properties.size()){
				for(String propertyName : properties){
					Object property = CacheClassUtil.getValueFromObject(propertyName, value);
					if(null != property)
						indexes.put(propertyName, property);
				}
			}
		}
		return indexes;
	}

}
