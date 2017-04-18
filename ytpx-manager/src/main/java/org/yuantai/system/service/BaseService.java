package org.yuantai.system.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.yuantai.common.Paginator;

/**
 * service基础接口,定义了基本的增删改查方法
 * @author zhangle
 * @param <T>
 */
public interface BaseService<T> {

	public T get(String id);
	
	public void add(T obj);
	
	public void update(T obj);
	
	public void delete(String id);
	
	public List<T> find(DetachedCriteria criteria);

	public void find(Paginator<T> pagin);
}
