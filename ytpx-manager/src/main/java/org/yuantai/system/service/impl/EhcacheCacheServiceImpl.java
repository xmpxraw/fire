package org.yuantai.system.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yuantai.common.cache.EhcacheUtil;
import org.yuantai.system.service.CacheService;

/**
 * ehcache缓存服务实现类
 * @ClassName: EhcacheCacheServiceImpl
 * @Description:
 * @author zhangle
 * @email  zhanngle@163.com
 * @date 2015年7月28日 下午1:49:30
 */
@Service
public class EhcacheCacheServiceImpl implements CacheService {

	@Autowired private EhcacheUtil ehcacheUtil;
	
	@Override
	public Object get(String key) {
		return ehcacheUtil.get(key);
	}

	@Override
	public List<Object> get(Collection<String> keys) {
		return ehcacheUtil.get(keys);
	}

	@Override
	public void set(String key, Object value) {
		ehcacheUtil.set(key, value);
	}

	@Override
	public void set(Map<String, Object> map) {
		ehcacheUtil.set(map);
	}

	@Override
	public void set(String key, Object value, long timeout) {
		ehcacheUtil.set(key, value, timeout);
	}

}
