package org.yuantai.system.dao.hibernate;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.yuantai.common.Paginator;

@Repository
public class DaoUtil {
	
	@Autowired 
	private SessionFactory sessionFactory;
	
	/**
	 * 获取hibernate的session对象
	 * @return
	 */
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public void add(Object po) {
		getSession().save(po);
	}
	
	public void update(Object po) {
		getSession().update(po);
	}
	
	/**
	 * 更新单个字段
	 * @param clazz
	 * @param field
	 * @param id
	 * @param value
	 */
	public void update(Class<?> clazz,Serializable id,String field,Object value) {
		String hql="update "+clazz.getName()+" set "+field+"=? where id=?";
		executeUpdate(hql, value,id);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> clazz,String id) {
		return (T) getSession().get(clazz, id);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> clazz,Integer id) {
		return (T) getSession().get(clazz, id);
	}
	
	/**
	 * 当field字段的值等于value时返回该对象
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> clazz,String field,Object value) {
		Criteria criteria=getSession().createCriteria(clazz);
		criteria.add(Restrictions.eq(field, value));
		T result= (T) criteria.uniqueResult();
		return result;
	}
	
	public void delete(Class<?> clazz,String id) {
		executeUpdate("delete "+clazz.getName()+" where id=?",id);
	}
	
	public void delete(Class<?> clazz,Integer id) {
		executeUpdate("delete "+clazz.getName()+" where id=?",id);
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> find(DetachedCriteria detachedCriteria) {
		Criteria criteria=detachedCriteria.getExecutableCriteria(getSession());
		return (List<T>)criteria.list();
	}
	
	@SuppressWarnings("all")
	public void find(Paginator pagin) {
		
		Criteria criteria=pagin.getCriteria().getExecutableCriteria(getSession());
		long count=countRow(criteria);
		pagin.setTotalCount(count);
		
		//如果只有0条记录,那么无需再查数据库,直接返回一个空list
		if(count==0) {
			pagin.setDataList(Collections.EMPTY_LIST);
		} else {
			criteria.setProjection(null);
			criteria.setFirstResult(pagin.getStartResult());
			criteria.setMaxResults(pagin.getPageSize());
			pagin.setDataList(criteria.list());
		}
	}
	
	/**
	 * 快速统计Criteria的总行数
	 * @param criteria
	 * @return
	 */
	protected long countRow(Criteria criteria) {
		criteria.setProjection(Projections.rowCount());
		try {
			return Long.parseLong(criteria.uniqueResult()+"");
		} catch(Exception e) {
			return 0;
		}
	}
	
	public int countRow(String hql,Object... params) {
		try {
			List<Object> result=executeQuery(hql,params);
			return Integer.parseInt(result.get(0)+"");
		} catch(Exception e) {
			return 0;
		}
	}
	
	public Criteria getCriteria(Class<?> clazz) {
		return getSession().createCriteria(clazz);
	}
	
	/**
	 * 从配制文件获取命名sql
	 * @param name
	 * @return
	 */
	public Query getNamedQuery(String name,Object... params) {
		Query query=getSession().getNamedQuery(name);
		if(params!=null && params.length>0) {
			for(int i=0;i<params.length;i++) {
				query.setParameter(i, params[i]);
			}
		}
		return query;
	}
	
	/**
	 * 快速执行命名查询
	 * @param hql
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> executeNamedQuery(String name,Object... params) {
		Query query=getNamedQuery(name,params);
		return (List<T>)query.list();
	}
	
	/**
	 * 快速创建Query对象
	 * @param hql
	 * @return
	 */
	public Query createQuery(String hql,Object... params) {
		Query query=getSession().createQuery(hql);
		if(params!=null && params.length>0) {
			for(int i=0;i<params.length;i++) {
				query.setParameter(i, params[i]);
			}
		}
		return query;
	}
	
	/**
	 * 快速执行hql查询
	 * @param hql
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> executeQuery(String hql,Object... params) {
		Query query=createQuery(hql,params);
		return (List<T>)query.list();
	}
	
	/**
	 * 快速执行hql更新
	 * @param hql
	 * @return
	 */
	public int executeUpdate(String hql,Object... params) {
		Query query=createQuery(hql, params);
		return query.executeUpdate();
	}
	
	/**
	 * 快速获取一个对象
	 * @param hql
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T uniqueResult(String hql,Object... params) {
		Query query=createQuery(hql,params);
		return (T)query.uniqueResult();
	}
	
	/**
	 * 获取下一个排序号
	 * @param field		排序号实体属性名
	 * @return
	 */
	public int nextSeqnum(Class<?> clazz,String property) {
		String hql="select max("+property+") from "+clazz.getName();
		try {
			return Integer.parseInt(createQuery(hql).uniqueResult()+"")+3;	
		} catch(Exception e) {
			return 1;
		}
	}
	
}
