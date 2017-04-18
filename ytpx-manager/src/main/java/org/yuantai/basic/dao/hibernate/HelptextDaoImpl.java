package org.yuantai.basic.dao.hibernate;

import org.springframework.stereotype.Repository;
import org.yuantai.basic.dao.HelptextDao;
import org.yuantai.basic.pojo.Helptext;
import org.yuantai.basic.pojo.News;
import org.yuantai.system.dao.hibernate.BaseDaoImpl;

@Repository
public class HelptextDaoImpl extends BaseDaoImpl<Helptext> implements HelptextDao {

	@Override
	public void delete(String id) {
		update(id, "status", News.STATUS_DELETED);
	}
}
