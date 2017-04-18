package org.yuantai.basic.dao.hibernate;

import org.springframework.stereotype.Repository;
import org.yuantai.basic.dao.ChangelogDao;
import org.yuantai.basic.pojo.Changelog;
import org.yuantai.system.dao.hibernate.BaseDaoImpl;

@Repository
public class ChangelogDaoImpl extends BaseDaoImpl<Changelog> implements ChangelogDao {

	@Override
	public void delete(String id) {
		update(id, "status", Changelog.STATUS_DELETED);
	}
	
	
	
}
