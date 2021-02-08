package com.spring.common.cache.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import com.spring.common.cache.CachesException;
import com.spring.common.cache.ICustCache;
import com.spring.common.cache.IDynamicAttributesExtractor;
import com.spring.common.cache.IStatistics;
import com.spring.common.cache.util.CacheArrayUtil;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.search.Attribute;
import net.sf.ehcache.search.ExecutionHints;
import net.sf.ehcache.search.Query;
import net.sf.ehcache.search.Results;
import net.sf.ehcache.search.SearchException;
import net.sf.ehcache.search.attribute.DynamicAttributesExtractor;
import net.sf.ehcache.statistics.StatisticsGateway;
import net.sf.ehcache.terracotta.TerracottaNotRunningException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class EhcacheCache implements ICustCache {
	
	private static final Log logger = LogFactory.getLog(EhcacheCache.class);

	/** 缓存实例 */
	private Ehcache cache;
	
	/**
	 * 统计信息对象
	 */
	class Statistics implements IStatistics {
		
		private StatisticsGateway statistics;
		
		public Statistics(StatisticsGateway statistics){
			this.statistics = statistics;
		}

		@Override
		public long cacheEvictedCount() {
			return this.statistics.cacheEvictedCount();
		}

		@Override
		public long cacheExpiredCount() {
			return this.statistics.cacheExpiredCount();
		}

		@Override
		public long cacheHitCount() {
			return this.statistics.cacheHitCount();
		}

		@Override
		public double cacheHitRatio() {
			return this.statistics.cacheHitRatio();
		}

		@Override
		public long cacheMissCount() {
			return this.statistics.cacheMissCount();
		}

		@Override
		public long cacheMissExpiredCount() {
			return this.statistics.cacheMissExpiredCount();
		}

		@Override
		public long cacheMissNotFoundCount() {
			return this.statistics.cacheMissNotFoundCount();
		}

		@Override
		public long cachePutAddedCount() {
			return this.statistics.cachePutAddedCount();
		}

		@Override
		public long cachePutCount() {
			return this.statistics.cachePutCount();
		}

		@Override
		public long cachePutUpdatedCount() {
			return this.statistics.cachePutUpdatedCount();
		}

		@Override
		public long cacheRemoveCount() {
			return this.statistics.cacheRemoveCount();
		}

		@Override
		public String getAssociatedCacheName() {
			return this.statistics.getAssociatedCacheName();
		}

		@Override
		public long getLocalDiskSize() {
			return this.statistics.getLocalDiskSize();
		}

		@Override
		public long getLocalDiskSizeInBytes() {
			return this.statistics.getLocalDiskSizeInBytes();
		}

		@Override
		public long getLocalHeapSize() {
			return this.statistics.getLocalHeapSize();
		}

		@Override
		public long getLocalHeapSizeInBytes() {
			return this.statistics.getLocalHeapSizeInBytes();
		}

		@Override
		public long getLocalOffHeapSize() {
			return this.statistics.getLocalOffHeapSize();
		}

		@Override
		public long getLocalOffHeapSizeInBytes() {
			return this.statistics.getLocalHeapSizeInBytes();
		}

		@Override
		public long getRemoteSize() {
			return this.statistics.getRemoteSize();
		}

		@Override
		public long getSize() {
			return this.statistics.getSize();
		}

		@Override
		public long getWriterQueueLength() {
			return this.statistics.getWriterQueueLength();
		}

		@Override
		public long localDiskHitCount() {
			return this.statistics.localDiskHitCount();
		}

		@Override
		public long localDiskMissCount() {
			return this.statistics.localDiskMissCount();
		}

		@Override
		public long localDiskPutAddedCount() {
			return this.statistics.localDiskPutAddedCount();
		}

		@Override
		public long localDiskPutCount() {
			return this.statistics.localDiskPutCount();
		}

		@Override
		public long localDiskPutUpdatedCount() {
			return 0;
		}

		@Override
		public long localDiskRemoveCount() {
			return this.statistics.localDiskRemoveCount();
		}

		@Override
		public long localHeapHitCount() {
			return this.statistics.localHeapHitCount();
		}

		@Override
		public long localHeapMissCount() {
			return this.statistics.localHeapMissCount();
		}

		@Override
		public long localHeapPutAddedCount() {
			return this.statistics.localHeapPutAddedCount();
		}

		@Override
		public long localHeapPutCount() {
			return this.statistics.localHeapPutCount();
		}

		@Override
		public long localHeapPutUpdatedCount() {
			return this.statistics.localHeapPutUpdatedCount();
		}

		@Override
		public long localHeapRemoveCount() {
			return this.statistics.localHeapRemoveCount();
		}

		@Override
		public long localOffHeapHitCount() {
			return this.statistics.localOffHeapHitCount();
		}

		@Override
		public long localOffHeapMissCount() {
			return this.statistics.localOffHeapMissCount();
		}

		@Override
		public long localOffHeapPutAddedCount() {
			return this.statistics.localOffHeapPutAddedCount();
		}

		@Override
		public long localOffHeapPutCount() {
			return this.statistics.localOffHeapPutCount();
		}

		@Override
		public long localOffHeapPutUpdatedCount() {
			return this.statistics.localOffHeapPutUpdatedCount();
		}

		@Override
		public long localOffHeapRemoveCount() {
			return this.statistics.localOffHeapRemoveCount();
		}

		@Override
		public void setStatisticsTimeToDisable(long time, TimeUnit unit) {
			this.statistics.setStatisticsTimeToDisable(time, unit);
		}

		@Override
		public long xaCommitCommittedCount() {
			return this.statistics.xaCommitCommittedCount();
		}

		@Override
		public long xaCommitCount() {
			return this.statistics.xaCommitCount();
		}

		@Override
		public long xaCommitExceptionCount() {
			return this.statistics.xaCommitExceptionCount();
		}

		@Override
		public long xaCommitReadOnlyCount() {
			return this.statistics.xaCommitReadOnlyCount();
		}

		@Override
		public long xaRecoveryCount() {
			return this.statistics.xaRecoveryCount();
		}

		@Override
		public long xaRecoveryNothingCount() {
			return this.statistics.xaRecoveryNothingCount();
		}

		@Override
		public long xaRecoveryRecoveredCount() {
			return this.statistics.xaRecoveryRecoveredCount();
		}

		@Override
		public long xaRollbackCount() {
			return this.statistics.xaRollbackCount();
		}

		@Override
		public long xaRollbackExceptionCount() {
			return this.statistics.xaRollbackExceptionCount();
		}

		@Override
		public long xaRollbackSuccessCount() {
			return this.statistics.xaRollbackSuccessCount();
		}
	}
	
	public EhcacheCache(){}
	
	public EhcacheCache(Ehcache cache){
		this.cache = cache;
	}
	
	@Override
	public void clear() throws CachesException {
		try{
			this.cache.removeAll();
		}catch(IllegalStateException ex){
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		}catch(CacheException ex){
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		}
	}

	@Override
	public void destory() {
		try{
			this.cache.dispose();
		}catch(IllegalStateException ex){
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		}
	}

	@Override
	public void dispose() throws CachesException {
		try{
			this.cache.dispose();
		}catch(IllegalStateException ex){
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		}
	}

	@Override
	public void evictExpiredElements() {
		try{
			this.cache.evictExpiredElements();
		}catch (IllegalStateException ex) {
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		} catch (CacheException ex) {
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		}
	}

	@Override
	public void flush() {
		try {
			this.cache.flush();
		} catch (IllegalStateException ex) {
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		} catch (CacheException ex) {
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		}
	}

	@Override
	public Object get(Object key) throws CachesException {
		if(null == key){
			return null;
		}
		try{
			Element elm = cache.get(key);
			if(null != elm){
				return this.cache.get(key).getObjectValue();
			}else{
				return null;
			}
		} catch (IllegalStateException ex) {
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		} catch (CacheException ex) {
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		}
	}

	@Override
	public IStatistics getStatistics() {
		if (this.isStatisticsEnabled())
			try {
				return new Statistics(this.cache.getStatistics());
			} catch (IllegalStateException ex) {
				logger.error(ex.getMessage(),ex);
				throw new CachesException(ex.getMessage());
			}
		return null;
	}

	@Override
	public String getStats() {
		return this.cache.getStatus().toString();
	}

	@Override
	public boolean isClustered() {
		try {
			return this.cache.isClusterBulkLoadEnabled();
		} catch (UnsupportedOperationException ex) {
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		} catch (TerracottaNotRunningException ex) {
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		}
	}

	@Override
	public boolean isExpired(Object key) {
		try {
			return this.cache.get(key).isExpired();
		} catch (IllegalStateException ex) {
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		} catch (CacheException ex) {
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		}
	}

	@Override
	public boolean isSearchable() {
		return this.cache.isSearchable();
	}

	@Override
	public boolean isStatisticsEnabled() {
		return true;
	}

	@Override
	public void put(Object key, Object value) throws CachesException {
		try {
			this.cache.put(new Element(key, value));
		} catch (IllegalStateException ex) {
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		} catch (IllegalArgumentException ex) {
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		} catch (CacheException ex) {
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		}
	}

	@Override
	public void put(Object key, Object value, Integer timeToIdleSeconds,
			Integer timeToLiveSeconds) throws CachesException {
		try {
			this.cache.put(new Element(key, value, timeToIdleSeconds, timeToLiveSeconds));
		} catch (IllegalStateException ex) {
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		} catch (IllegalArgumentException ex) {
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		} catch (CacheException ex) {
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		}
	}

	@Override
	public void put(Object key, Object value, long version)
			throws CachesException {
		try {
			this.cache.put(new Element(key, value, version));
		} catch (IllegalStateException ex) {
			throw new CachesException(ex.getMessage());
		} catch (IllegalArgumentException ex) {
			throw new CachesException(ex.getMessage());
		} catch (CacheException ex) {
			throw new CachesException(ex.getMessage());
		}
	}

	@Override
	public void put(Serializable key, Serializable value)
			throws CachesException {
		try {
			this.cache.put(new Element(key, value));
		} catch (IllegalStateException ex) {
			throw new CachesException(ex.getMessage());
		} catch (IllegalArgumentException ex) {
			throw new CachesException(ex.getMessage());
		} catch (CacheException ex) {
			throw new CachesException(ex.getMessage());
		}
	}

	@Override
	public void putAll(HashMap<Object, Object> elements) {
		try{
			for(Object key : elements.keySet()){
				this.cache.put(new Element(key, elements.get(key)));
			}
		}catch (IllegalStateException ex) {
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		} catch (IllegalArgumentException ex) {
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		} catch (CacheException ex) {
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		}
	}

	@Override
	public Object queryByKey(Object key) {
		if(this.isSearchable()){
			try{
				@SuppressWarnings("unchecked")
				Results result = this.cache.createQuery().includeKeys().includeValues().addCriteria(Query.KEY.eq(key.toString())).execute();
				return result.all().get(0).getValue();
			} catch (SearchException ex) {
				logger.error(ex.getMessage(),ex);
				throw new CachesException(ex.getMessage());
			} catch (NullPointerException ex) {
				logger.error(ex.getMessage(),ex);
				throw new CachesException(ex.getCause());
			}
		}else{
			// 不使用<searchable/> 模式，通过比较key
			if(this.cache.isKeyInCache(key))
				return this.get(key);
			return null;
		}
	}

	@Override
	public Object[] queryLikeKey(String regex) {
		if(this.isSearchable()){
			try{
				Results result = this.cache.createQuery().includeKeys().includeValues().addCriteria(Query.KEY.ilike(regex)).execute();
				return CacheArrayUtil.convert(result);
			}catch(SearchException ex){
				logger.error(ex.getMessage(),ex);
				throw new CachesException(ex.getMessage());
			}
		}else{
			// 不使用<searchable/> 模式，通过比较key
			String likeKey = regex.replace("*", "").replace("?", "");
			ArrayList<Object> results = new ArrayList<Object>();
			for (Object ok : this.cache.getKeys()) {
				if (ok.toString().indexOf(likeKey) >= 0) {
					results.add(this.get(ok));
				}
			}
			if(results.size() > 0)
				return results.toArray();
			return null;
		}
	}

	@Override
	public Object queryObjectEqPropValue(String property, Object value) {
		if(this.isSearchable()){
			try{
				this.cache.getSearchAttributes();
				Attribute<Object> attr = this.cache.getSearchAttribute(property);
				Results result = this.cache.createQuery().includeKeys().includeValues().includeAttribute(attr).addCriteria(attr.eq(value)).execute();
				Object[] results = CacheArrayUtil.convert(result);
				return results != null ? results[0] : null;
			} catch (SearchException e) {
				logger.error(e.getMessage(),e);
				throw new CachesException(e.getCause());
			}
		}
		return null;
	}

	@Override
	public Object[] queryObjsLikePropValue(String property, String regex) {
		if(this.isSearchable()){
			try{
				Results result = this.cache.createQuery().includeValues().addCriteria(this.cache.getSearchAttribute(property).ilike(regex)).end().execute();
				return CacheArrayUtil.convert(result);
			} catch (SearchException e) {
				logger.error(e.getMessage(),e);
				throw new CachesException(e.getCause());
			}
		}
		return null;
	}

	@Override
	public Object[] queryObjsLikePropValue(String property, String regex,
			int size) {
		if (this.isSearchable()) {
			try {
				Results result = this.cache.createQuery().includeValues().addCriteria(this.cache.getSearchAttribute(property).ilike(regex)).end()
						.execute(new ExecutionHints().setResultBatchSize(size));
				return CacheArrayUtil.convert(result);
			} catch (SearchException e) {
				logger.error(e.getMessage(),e);
				throw new CachesException(e.getCause());
			}
		}
		return null;
	}

	@Override
	public Object[] queryObjsLikePropValueAndOrderBy(String property,
			String regex, Direction direct) {
		if (this.isSearchable()) {
			try {
				Attribute<Object> att = this.cache.getSearchAttribute(property);
				Results result = this.cache
						.createQuery()
						.includeValues()
						.addCriteria(att.ilike(regex))
						.addOrderBy(att, direct == Direction.ASCENDING ? net.sf.ehcache.search.Direction.ASCENDING : net.sf.ehcache.search.Direction.DESCENDING)
						.end().execute();
				return CacheArrayUtil.convert(result);
			} catch (SearchException e) {
				logger.error(e.getMessage(),e);
				throw new CachesException(e.getCause());
			}
		}
		return null;
	}

	@Override
	public Object[] queryObjsInPropValue(String property,
			Collection<Object> values) {
		if (this.isSearchable()) {
			try {
				Attribute<Object> att = this.cache.getSearchAttribute(property);
				Results result = this.cache.createQuery().includeValues().addCriteria(att.in(values)).end().execute();
				return CacheArrayUtil.convert(result);
			} catch (SearchException e) {
				logger.error(e.getMessage(),e);
				throw new CachesException(e.getCause());
			}
		}
		return null;
	}

	@Override
	public Object[] queryObjsGePropValue(String property, Object value) {
		if (this.isSearchable()) {
			try {
				Attribute<Object> att = this.cache.getSearchAttribute(property);
				Results result = this.cache.createQuery().includeValues().addCriteria(att.ge(value)).end().execute();
				return CacheArrayUtil.convert(result);
			} catch (SearchException e) {
				throw new CachesException(e.getCause());
			}
		}
		return null;
	}

	@Override
	public Object[] queryObjsLePropValue(String property, Object value) {
		if (this.isSearchable()) {
			try {
				Attribute<Object> att = this.cache.getSearchAttribute(property);
				Results result = this.cache.createQuery().includeValues().addCriteria(att.le(value)).end().execute();
				return CacheArrayUtil.convert(result);
			} catch (SearchException e) {
				logger.error(e.getMessage(),e);
				throw new CachesException(e.getCause());
			}
		}
		return null;
	}

	@Override
	public Object[] queryObjsBetweenPropValue(String property, Object max,
			Object min, boolean maxInclusive, boolean minInclusive) {
		if (this.isSearchable()) {
			try {
				Attribute<Object> att = this.cache.getSearchAttribute(property);
				Results result = this.cache.createQuery().includeValues().addCriteria(att.between(min, max, minInclusive, maxInclusive)).end().execute();
				return CacheArrayUtil.convert(result);
			} catch (SearchException e) {
				logger.error(e.getMessage(),e);
				throw new CachesException(e.getCause());
			}
		}
		return null;
	}

	@Override
	public Object replace(Object key, Object value) {
		if (this.cache.isKeyInCache(key))
			try {
				return this.cache.replace(new Element(key, value));
			} catch (NullPointerException e) {
				logger.error(e.getMessage(),e);
				throw new CachesException(e.getCause());
			}
		return null;
	}

	@Override
	public boolean replace(Object oldkey, Object oldvalue, Object key,
			Object value) {
		try {
			return this.cache.replace(new Element(oldkey, oldvalue), new Element(key, value));
		} catch (IllegalArgumentException ex) {
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		} catch (NullPointerException ex) {
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		}
	}

	@Override
	public Object remove(Object key) throws CachesException {
		Object object = null;
		try{
			object = this.cache.get(key).getObjectValue();
			this.cache.remove(key);
		} catch (IllegalStateException ex) {
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		} catch (CacheException ex) {
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		}
		return object;
	}

	@Override
	public Object remove(Serializable key) throws CachesException {
		try {
			return this.cache.remove(key);
		} catch (IllegalStateException ex) {
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		}
	}

	@Override
	public void removeAll(Collection<?> keys) throws CachesException {
		try {
			this.cache.removeAll(keys);
		} catch (IllegalStateException ex) {
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		} catch (NullPointerException ex) {
			logger.error(ex.getMessage(),ex);
			throw new CachesException(ex.getMessage());
		}
	}
	
	/**
	 * 注册可查询属性 
	 */
	@Override
	public void registerDynamicAttributesExtractor(
			IDynamicAttributesExtractor extractor) {
		if (this.cache.isSearchable())
			this.cache.registerDynamicAttributesExtractor((DynamicAttributesExtractor) extractor);
	}

	/* (non-Javadoc)
	 * @see com.icip.framework.common.cache.ICustCache#queryKeys(java.lang.String)
	 */
	@Override
	public Collection<String> queryKeys(String regex) {
		return cache.getKeys();
	}

	@Override
	public void clearAllByKey(String key) {
		cache.remove(key);
	}

}
