package com.spring.gateway.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author 作者：赵进华
 * @version 创建时间：2019年3月21日 上午9:36:54
 * @Desc类说明:Redis帮助类
 */
@Component
public class RedisUtils {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	/**
	 * 根据key 获取过期时间
	 * @param key 键 不能为null
	 * @return 时间(秒) 返回0代表为永久有效
	 */
	public long getExpire(String key) {
		return redisTemplate.getExpire(key, TimeUnit.SECONDS);
	}

	/**
	 * 设置缓存
	 * 
	 * @param key
	 * @param object
	 * @param expireTime
	 *            秒
	 */
	@SuppressWarnings("unchecked")
	public  void set(String key, Object object, long expireTime) {
		if (!StringUtils.isEmpty(key)) {
			redisTemplate.opsForValue().set(key, object);
			if (expireTime > 0) {
				redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
			}
		}
	}
	
	/**
	 * 设置缓存,永远不过期 
	 * @param key
	 * @param object
	 * @author 作者：赵进华 
	 * @version 创建时间：2019年10月26日 上午11:10:33
	 */
	@SuppressWarnings("unchecked")
	public  void set(String key, Object object) {
		if (!StringUtils.isEmpty(key)) {
			redisTemplate.opsForValue().set(key, object);
		}
	}

	/**
	 * 删除缓存
	 * 
	 * @param key
	 */
	@SuppressWarnings("unchecked")
	public  void del(String key) {
		if (!StringUtils.isEmpty(key)) {
			redisTemplate.delete(key);
		}
	}

	/**
	 * 判断key是否存在
	 * 
	 * @param key
	 */
	@SuppressWarnings("unchecked")
	public  boolean exists(String key) {
		return redisTemplate.hasKey(key);
	}
	
	/**
	 * key续约
	 * 
	 * @param key
	 */
	@SuppressWarnings("unchecked")
	public  void expire(String key, long expireTime) {
		redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
	}

	/**
	 * 获取缓存
	 * 
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  <T> T get(String key, Class<T> clazz) {
		if (null != redisTemplate.opsForValue().get(key)) {
			Object obj = redisTemplate.opsForValue().get(key);
			if (null != obj) {
				return clazz.cast(obj);
			}
		}
		return null;
	}

	/**
	 * 设置Hash类型缓存
	 * 
	 * @param key
	 * @param hashKey
	 * @param object
	 * @param expireTime
	 */
	public  void setH(String key, Object hashKey, Object object, long expireTime) {
		if (!StringUtils.isEmpty(key)) {
			redisTemplate.opsForHash().put(key, hashKey, object);
			if (expireTime > 0) {
				redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
			}
		}
	}

	/**
	 * 获取Hash类型缓存
	 * 
	 * @param key
	 * @param hashKey
	 * @param
	 * @param
	 */
	@SuppressWarnings("unchecked")
	public  <T> T getH(String key, Object hashKey, Class<T> clazz) {
		if (null != redisTemplate.opsForHash().get(key, hashKey)) {
			Object obj = redisTemplate.opsForHash().get(key, hashKey);
			if (null != obj) {
				return clazz.cast(obj);
			}
		}
		return null;
	}

	/**
	 * 获取Hash类型缓存
	 * 
	 * @param key
	 * @param hashKey
	 * @param
	 * @param
	 */
	@SuppressWarnings("unchecked")
	public  void delH(String key, Object hashKey) {
		if (!StringUtils.isEmpty(key) && null != hashKey) {
			redisTemplate.opsForHash().delete(key, hashKey);
		}
	}
}
