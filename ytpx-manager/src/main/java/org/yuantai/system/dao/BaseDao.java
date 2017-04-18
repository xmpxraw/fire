package org.yuantai.system.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.yuantai.common.Paginator;

/**
 * dao基础接口
 * @author zhangle
 * @param <T>
 */
public interface BaseDao<T> {
	
	public T get(String id);
	
	public void add(T po);
	
	public void update(T po);
	
	public void delete(String id);
	
	public List<T> find(DetachedCriteria criteria);
	
	public void find(Paginator<T> pagin);
}
