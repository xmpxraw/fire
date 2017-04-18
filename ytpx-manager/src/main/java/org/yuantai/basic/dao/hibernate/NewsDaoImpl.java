package org.yuantai.basic.dao.hibernate;

import org.springframework.stereotype.Repository;
import org.yuantai.basic.dao.NewsDao;
import org.yuantai.basic.pojo.News;
import org.yuantai.system.dao.hibernate.BaseDaoImpl;

@Repository
public class NewsDaoImpl extends BaseDaoImpl<News> implements NewsDao {

	@Override
	public void delete(String id) {
		update(id, "status", News.STATUS_DELETED);
	}
}
