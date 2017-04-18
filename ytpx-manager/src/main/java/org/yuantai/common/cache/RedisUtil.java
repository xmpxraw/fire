package org.yuantai.common.cache;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

/**
 * 用于对redis缓存进行操作
 * @ClassName: RedisUtil
 * @Description:
 * @author zhangle
 * @email  zhanngle@163.com
 * @date 2014年11月28日 下午2:46:56
 */
//@Component
public class RedisUtil {

	private static final Logger logger = LogManager.getLogger(RedisUtil.class);
	@Autowired private RedisTemplate<String, Object> redisTemplate;
	
	/**
	 * 从缓存获取数据
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		ValueOperations<String, Object> valueOper=redisTemplate.opsForValue();
		Object value=valueOper.get(key);
		if(value!=null) {
			logger.debug("Got an object,key: '{}'",key);
		} else {
			logger.debug("Got null,key: '{}'",key);
		}
		return value;
	}
	
	/**
	 * 从缓存获取数据
	 * @param key
	 * @return
	 */
	public List<Object> get(Collection<String> keys) {
		ValueOperations<String, Object> valueOper=redisTemplate.opsForValue();
		return valueOper.multiGet(keys);
	}
	
	
	/**
	 * 设置数据到缓存
	 * @param key
	 * @param value
	 */
	public void set(String key,Object value) {
		ValueOperations<String, Object> valueOper=redisTemplate.opsForValue();
		valueOper.set(key, value);
	}
	
	/**
	 * 设置数据到缓存
	 * @param key
	 * @param value
	 */
	public void set(Map<String, Object> map) {
		ValueOperations<String, Object> valueOper=redisTemplate.opsForValue();
		valueOper.multiSet(map);
	}
	
	/**
	 * 设置数据到缓存
	 * @param key
	 * @param value
	 * @param timeout	缓存时间,单位:秒
	 */
	public void set(String key,Object value,long timeout) {
		ValueOperations<String, Object> valueOper=redisTemplate.opsForValue();
		valueOper.set(key, value, timeout, TimeUnit.SECONDS);
	}
	
	public Object hash(String key,String hashkey) {
		HashOperations<String, String, Object> hashOper=redisTemplate.opsForHash();
		return hashOper.get(key,hashkey);
	}
	
	public void hash(String key,String hashkey,Object value) {
		HashOperations<String, String, Object> hashOper=redisTemplate.opsForHash();
		hashOper.put(key, hashkey, value);
	}
	
	public void hashdel(String key,String hashkey) {
		HashOperations<String, String, Object> hashOper=redisTemplate.opsForHash();
		hashOper.delete(key, hashkey);
	}
	
	public Set<String> hashkeys(String key) {
		HashOperations<String, String, Object> hashOper=redisTemplate.opsForHash();
		return hashOper.keys(key);
	}
	
	public List<Object> hashvals(String key) {
		HashOperations<String, String, Object> hashOper=redisTemplate.opsForHash();
		return hashOper.values(key);
	}
}
