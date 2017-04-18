package org.yuantai.study.dao.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.yuantai.study.dao.DirectoryDao;
import org.yuantai.study.pojo.Directory;
import org.yuantai.system.dao.hibernate.BaseDaoImpl;

/**
 * DirectoryDaoImpl
 * 
 * @author cola
 * @version 2016年12月23日
 */
@Repository
public class DirectoryDaoImpl extends BaseDaoImpl<Directory> implements DirectoryDao {

	@Override
	public List<Directory> getChildren(String parentId, Integer status) {
		Criteria criteria = getCriteria();
		if (StringUtils.isEmpty(parentId)) {
			criteria.add(Restrictions.eqOrIsNull("parentId", "0"));
		} else {
			criteria.add(Restrictions.eq("parentId", parentId));
		}
		if (status != null) {
			criteria.add(Restrictions.eq("status", status));
		}
		criteria.addOrder(Order.asc("sortNo"));

		return criteria.list();
	}

	@Override
	public String getMaxDirCode() {
		Query query = createQuery("select max(code) from Directory");
		return (String) query.uniqueResult();
	}

	@Override
	public Long countByName(String name) {
		Query query = createQuery("select count(*) from Directory where name = ?");
		query.setParameter(0, name);
		return (Long) query.uniqueResult();
	}

}
