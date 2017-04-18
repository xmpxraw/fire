package org.yuantai.common.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.springframework.stereotype.Component;

/**
 * 用于对ehcache缓存进行操作
 * @ClassName: EhcacheUtil
 * @Description:
 * @author zhangle
 * @email  zhanngle@163.com
 * @date 2015年7月28日 下午1:45:54
 */
@Component
public class EhcacheUtil {

	@Resource(name="systemCache")
	private Cache systemCache;
	
	/**
	 * 从缓存获取数据
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		Element ele=systemCache.get(key);
		return ele==null?null:ele.getObjectValue();
	}
	
	/**
	 * 从缓存获取数据
	 * @param key
	 * @return
	 */
	public List<Object> get(Collection<String> keys) {
		List<Object> values=new ArrayList<Object>(keys.size());
		for(String key:keys) {
			values.add(get(key));
		}
		return values;
	}
	
	
	/**
	 * 设置数据到缓存
	 * @param key
	 * @param value
	 */
	public void set(String key,Object value) {
		Element ele=new Element(key,value);
		systemCache.put(ele);
	}
	
	/**
	 * 设置数据到缓存
	 * @param key
	 * @param value
	 */
	public void set(Map<String, Object> map) {
		
		Iterator<Map.Entry<String, Object>> it=map.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<String, Object> entry=it.next();
			set(entry.getKey(),entry.getValue());
		}
	}
	
	/**
	 * 设置数据到缓存
	 * @param key
	 * @param value
	 * @param timeout	缓存时间,单位:秒
	 */
	public void set(String key,Object value,long timeout) {
		Element ele=new Element(key,value,false,(int)timeout,(int)timeout);
		systemCache.put(ele);
	}
	
	/**
	 * 删除数据缓存
	 * @param key
	 */
	public void remove(String key) {
		systemCache.remove(key);
	}
}
