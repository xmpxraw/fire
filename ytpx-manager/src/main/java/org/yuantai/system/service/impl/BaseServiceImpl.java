package org.yuantai.system.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.yuantai.common.Paginator;
import org.yuantai.system.dao.BaseDao;
import org.yuantai.system.service.BaseService;

/**
 * service基础实现类,实现了基础的增删改查方法
 * @author zhangle
 * @param <T>
 */
@Transactional
public abstract class BaseServiceImpl<T> implements BaseService<T> {

	@Autowired protected BaseDao<T> baseDao;
	
	/**
	 * getBaseDao()的默认实现,子类可覆盖此方法
	 * @return
	 */
	protected BaseDao<T> getBaseDao() {
		return baseDao;
	}
	
	public void add(T obj) {
		getBaseDao().add(obj);
	}
	
	public void delete(String id) {
		getBaseDao().delete(id);
	}
	
	@Transactional(readOnly = true)
	public T get(String id) {
		T obj=getBaseDao().get(id);
		return obj;
	}

	public void update(T obj) {
		getBaseDao().update(obj);
	}
	
	@Transactional(readOnly = true)
	public List<T> find(DetachedCriteria criteria) {
		return getBaseDao().find(criteria);
	}
	
	@Transactional(readOnly = true)
	public void find(Paginator<T> pagin) {
		getBaseDao().find(pagin);
	}
}
