package org.yuantai.system.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yuantai.common.cache.RedisUtil;
import org.yuantai.system.service.CacheService;

/**
 * redis缓存服务实现类
 * @ClassName: RedisCacheServiceImpl
 * @Description:
 * @author zhangle
 * @email  zhanngle@163.com
 * @date 2015年7月27日 下午5:53:29
 */
//@Service
public class RedisCacheServiceImpl implements CacheService {

	@Autowired private RedisUtil redisUtil;
	
	@Override
	public Object get(String key) {
		return redisUtil.get(key);
	}

	@Override
	public List<Object> get(Collection<String> keys) {
		return redisUtil.get(keys);
	}

	@Override
	public void set(String key, Object value) {
		redisUtil.set(key, value);
	}

	@Override
	public void set(Map<String, Object> map) {
		redisUtil.set(map);
	}

	@Override
	public void set(String key, Object value, long timeout) {
		redisUtil.set(key, value, timeout);
	}

}
