package org.yuantai.system.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 缓存服务
 * @ClassName: CacheService
 * @Description:
 * @author zhangle
 * @email  zhanngle@163.com
 * @date 2015年7月27日 下午5:51:08
 */
public interface CacheService {

	public Object get(String key);
	
	public List<Object> get(Collection<String> keys);
	
	public void set(String key,Object value);
	
	public void set(Map<String, Object> map);
	
	public void set(String key,Object value,long timeout);
}
