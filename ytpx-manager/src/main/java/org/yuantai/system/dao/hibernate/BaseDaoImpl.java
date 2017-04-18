package org.yuantai.system.dao.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.yuantai.common.Paginator;
import org.yuantai.system.dao.BaseDao;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;

/**
 * hibernate的dao基础实现类
 * @author zhangle
 * @param <T>
 */
public abstract class BaseDaoImpl<T> implements BaseDao<T> {

	@Autowired 
	private SessionFactory sessionFactory;
	private Class<T> entityClass;
	
	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		try {
			entityClass =(Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		} catch(Exception e) {}
	}
	
	/**
	 * 获取hibernate的session对象
	 * @return
	 */
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public void add(T po) {
		getSession().save(po);
	}
	
	public void update(T po) {
		getSession().update(po);
	}
	
	/**
	 * 更新单个字段
	 * @param field
	 * @param id
	 * @param value
	 */
	public void update(Serializable id,String field,Object value) {
		String hql="update "+entityClass.getName()+" set "+field+"=? where id=?";
		executeUpdate(hql, value,id);
	}
	
	@SuppressWarnings("unchecked")
	public T get(String id) {
		return (T) getSession().get(entityClass, id);
	}
	
	public void delete(String id) {
		executeUpdate("delete "+entityClass.getName()+" where id=?",id);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> find(DetachedCriteria detachedCriteria) {
		Criteria criteria=detachedCriteria.getExecutableCriteria(getSession());
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public void find(Paginator<T> pagin) {
		
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
	
	/**
	 * 执行命名sqlquery的count查询
	 * sqlQueryName一定是<sql-query>的名称
	 * @param sqlQueryName
	 * @return
	 */
	protected long countNamedSQLQuery(String sqlQueryName) {
		
		Query query=getNamedQuery(sqlQueryName);
		String sql=String.format("select count(*) count from (%s)",query.getQueryString());
		sql=sql.replaceAll("\\{|\\}", "");
		SQLQuery sqlquery=getSession().createSQLQuery(sql);
		sqlquery.addScalar("count",StandardBasicTypes.LONG);
		return (Long)sqlquery.uniqueResult();
	}
	
	/**
	 * 当field字段的值等于value时返回该对象
	 */
	@SuppressWarnings("unchecked")
	protected <O> O get(Class<O> clazz,String field,Object value) {
		Criteria criteria=getSession().createCriteria(clazz);
		criteria.add(Restrictions.eq(field, value));
		List<O> result= criteria.list();
		
		return result.isEmpty()?null:result.get(0);
	}
	
	/**
	 * 获取下一个排序号
	 * @param field		排序号实体属性名
	 * @return
	 */
	public long getNextSeqNum(String property) {
		Criteria criteria=getCriteria().setProjection(Projections.max(property));
		try {
			return Long.parseLong(criteria.uniqueResult()+"")+1;	
		} catch(Exception e) {
			return 1;
		}
	}
	
	/**
	 * 获取下一排序号,默认排序属性为:seqNum
	 * @return
	 */
	public long getNextSeqNum() {
		return getNextSeqNum("seqNum");
	}
	
	protected Criteria getCriteria() {
		return getSession().createCriteria(entityClass);
	}
	
	protected Criteria getCriteria(Class<?> clazz) {
		return getSession().createCriteria(clazz);
	}
	
	/**
	 * 从配制文件获取命名sql
	 * @param name
	 * @return
	 */
	protected Query getNamedQuery(String name) {
		return getSession().getNamedQuery(name);
	}
	
	/**
	 * 快速执行命名查询
	 * @param hql
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected List<T> executeNamedQuery(String name,Object... params) {
		Query query=getSession().getNamedQuery(name);
		if(params!=null && params.length>0) {
			for(int i=0;i<params.length;i++) {
				query.setParameter(i, params[i]);
			}
		}
		return query.list();
	}
	
	/**
	 * 快速创建Query对象
	 * @param hql
	 * @return
	 */
	protected Query createQuery(String hql) {
		return getSession().createQuery(hql);
	}
	
	/**
	 * 快速执行hql查询
	 * @param hql
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected List<T> executeQuery(String hql,Object... params) {
		Query query=getSession().createQuery(hql);
		if(params!=null && params.length>0) {
			for(int i=0;i<params.length;i++) {
				query.setParameter(i, params[i]);
			}
		}
		return query.list();
	}
	
	/**
	 * 快速执行hql更新
	 * @param hql
	 * @return
	 */
	protected int executeUpdate(String hql,Object... params) {
		Query query=getSession().createQuery(hql);
		if(params!=null && params.length>0) {
			for(int i=0;i<params.length;i++) {
				query.setParameter(i, params[i]);
			}
		}
		return query.executeUpdate();
	}
	
}
