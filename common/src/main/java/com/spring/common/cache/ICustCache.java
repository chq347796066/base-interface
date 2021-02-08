package com.spring.common.cache;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

/**
 *	缓存操作入口 
 */
public interface ICustCache {

	static enum Direction{
		ASCENDING, DESCENDING
	}
	
	/**
	 * 清理缓存数据
	 * 
	 * @throws CachesException
	 */
	void clear() throws CachesException;

	/**
	 * 销毁缓存实例
	 * 
	 * @throws CachesException
	 */
	void destory();

	/**
	 * 销毁缓存实例
	 * 
	 * @throws CachesException
	 */
	void dispose() throws CachesException;

	/**
	 * 清理失效的缓存对象
	 * 
	 * @throws CachesException
	 */
	void evictExpiredElements();

	/**
	 * 写入磁盘
	 * 
	 * @throws CachesException
	 */
	void flush();

	/**
	 * 得到缓存实例
	 * 
	 * @param key
	 *            对象Key
	 * @return Object 返回缓存对象
	 * @throws CachesException
	 */
	Object get(Object key) throws CachesException;

	/**
	 * 得到统计信息
	 * 
	 * @return 缓存统计对象
	 */
	IStatistics getStatistics();

	/**
	 * 得到缓存状态信息
	 * 
	 * @return
	 */
	String getStats();

	/**
	 * 是否支持集群
	 * 
	 * @return 是集群则返回True，否则返回false
	 * @throws CachesException
	 */
	boolean isClustered();

	/**
	 * 是否缓存对象失效
	 * 
	 * @param key
	 *            对象Key
	 * @return 失效则返回True,否则返回false
	 * @throws CacheException
	 */
	boolean isExpired(Object key);

	/**
	 * 是否支持查询
	 * 
	 * @return 是返回True，否则返回false
	 */
	boolean isSearchable();

	/**
	 * 是否支持统计功能
	 * 
	 * @return 是返回True，否则返回false
	 */
	boolean isStatisticsEnabled();

	/**
	 * 添加缓存对象
	 * 
	 * @param key
	 *            对象Key
	 * @param value
	 *            对象
	 * @throws CachesException
	 */
	void put(Object key, Object value) throws CachesException;

	/**
	 * 添加缓存对象
	 * 
	 * @param key
	 *            对象Key
	 * @param value
	 *            对象
	 * @param timeToIdleSeconds
	 *            两次访问该对象时间最大的间隔时间(单位：秒)，默认值0-不限
	 * @param timeToLiveSeconds
	 *            该对象最大的存活时间（单位：秒），默认值0-不限
	 * 
	 * @throws CachesException
	 */
	void put(Object key, Object value, Integer timeToIdleSeconds, Integer timeToLiveSeconds) throws CachesException;

	/**
	 * 添加缓存对象
	 * 
	 * @param key
	 *            对象Key
	 * @param value
	 *            对象
	 * @param version
	 *            版本号
	 * @throws CachesException
	 */
	void put(Object key, Object value, long version) throws CachesException;

	/**
	 * 添加缓存对象
	 * 
	 * @param key
	 *            对象Key
	 * @param value
	 *            对象
	 * @throws CachesException
	 */
	void put(Serializable key, Serializable value) throws CachesException;

	/**
	 * 添加缓存对象
	 * 
	 * @param elements
	 *            对象集合
	 * @throws CachesException
	 */
	void putAll(HashMap<Object, Object> elements);

	/**
	 * 查询通过缓存Key
	 * 
	 * @param key
	 *            对象缓存
	 * @return 返回缓存对象
	 */
	Object queryByKey(Object key);

	/**
	 * 模糊查询通过缓存Key
	 * 
	 * @param regex
	 * 
	 * @return 返回缓存对象数组
	 */
	Object[] queryLikeKey(String regex);

	/**
	 * 模糊查询通过缓存Key
	 * 
	 * @param regex
	 * 
	 * @return 返回缓存key数组
	 */
	Collection<String> queryKeys(String regex);
	
	/**
	 * 根据属性值查找缓存对象
	 * 
	 * @param property
	 *            属性名 支持数组索引、List数组索引、Map的Key、对象属性的组合{ 0/name/age}
	 * @param value
	 *            属性值
	 * @return 缓存对象
	 */
	Object queryObjectEqPropValue(String property, Object value);

	/**
	 * 根据属性值模糊查找缓存对象，类似sql中的like效果
	 * 
	 * @param property
	 *            属性名 支持数组索引、List数组索引、Map的Key、对象属性的组合{ 0/name/age}
	 * @param regex
	 *            表达式，支持*、？通配符 {"age*","age?","*age*"}
	 * @return 数组对象
	 */
	Object[] queryObjsLikePropValue(String property, String regex);

	/**
	 * 根据属性值模糊查找缓存对象，并指定结果集大小，类似sql中的like效果
	 * 
	 * @param property
	 *            属性名 支持数组索引、List数组索引、Map的Key、对象属性的组合{ 0/name/age}
	 * @param regex
	 *            表达式，支持*、？通配符 {"age*","age?","*age*"}
	 * @param size
	 *            结果集大小
	 * @return 数组对象
	 */
	Object[] queryObjsLikePropValue(String property, String regex, int size);

	/**
	 * 根据属性值模糊查找缓存对象，并排序
	 * 
	 * @param property
	 *            属性名 支持数组索引、List数组索引、Map的Key、对象属性的组合{ 0/name/age}
	 * @param regex
	 *            表达式，支持*、？通配符 {"age*","age?","*age*"}
	 * @param direct
	 *            ASCENDING --升序 DESCENDING --降序
	 * @return 数组对象
	 */

	Object[] queryObjsLikePropValueAndOrderBy(String property, String regex, Direction direct);

	/**
	 * 查找属性在集合值中的缓存对象 ，类似sql中的in效果
	 * 
	 * @param property
	 *            属性名 支持数组索引、List数组索引、Map的Key、对象属性的组合{ 0/name/age}
	 * @param value
	 *            值集合
	 * 
	 * @return 数组对象
	 */
	Object[] queryObjsInPropValue(String property, Collection<Object> values);

	/**
	 * 查找属性值大于给定值得缓存对象，类似sql中的"大于"效果
	 * 
	 * @param property
	 * @param value
	 * @return 对象数组
	 */
	Object[] queryObjsGePropValue(String property, Object value);

	/**
	 * 查找属性值小于给定值得缓存对象，类似sql中的"小于"效果
	 * 
	 * @param property
	 * @param value
	 * @return 对象数组
	 */
	Object[] queryObjsLePropValue(String property, Object value);

	/**
	 * 查找属性给定的值范围内得缓存对象，类似sql中的"between"效果
	 * 
	 * @param property
	 *            属性名
	 * @param max
	 *            最大值
	 * @param min
	 *            最小值
	 * @param maxInclusive
	 *            是否包含最大边界值，true--是,false--否
	 * @param minInclusive
	 *            是否包含最小边界值，true--是,false--否
	 * @return 对象数组
	 */
	Object[] queryObjsBetweenPropValue(String property, Object max, Object min, boolean maxInclusive, boolean minInclusive);

	/**
	 * 重置缓存对象
	 * 
	 * @param key
	 *            对象Key
	 * @param value
	 *            缓存对象
	 * @return 返回缓存对象
	 */
	Object replace(Object key, Object value);

	/**
	 * 重置缓存对象
	 * 
	 * @param oldkey
	 *            原缓存对象Key
	 * @param oldvalue
	 *            原缓存对象
	 * @param key
	 *            对象Key
	 * @param value
	 *            缓存对象
	 * @return 成功返回True，否则返回false
	 * @throws CachesException
	 */
	boolean replace(Object oldkey, Object oldvalue, Object key, Object value);

	/**
	 * 移除缓存对象
	 * 
	 * @param key
	 *            缓存对象Key
	 * @return 返回缓存对象
	 * @throws CachesException
	 */
	Object remove(Object key) throws CachesException;

	/**
	 * 移除缓存对象
	 * 
	 * @param key
	 *            缓存对象Key
	 * @return 返回缓存对象
	 * @throws CachesException
	 */
	Object remove(Serializable key) throws CachesException;

	/**
	 * 移除缓存对象
	 * 
	 * @param keys
	 *            缓存对象Key对象集合
	 * @throws CachesException
	 */
	void removeAll(Collection<?> keys) throws CachesException;

	/**
	 * 注册属性索引
	 * 
	 * @param extractor
	 */
	void registerDynamicAttributesExtractor(IDynamicAttributesExtractor extractor);

	/**
	 * 根据key删除系统中所有key
	 * @param key
	 */
	void clearAllByKey(String key);
}
