package org.yuantai.system.dao.hibernate;

import java.util.Date;

import org.springframework.stereotype.Repository;
import org.yuantai.system.dao.LogsDao;
import org.yuantai.system.pojo.Logs;

@Repository
public class LogsDaoImpl extends BaseDaoImpl<Logs> implements LogsDao {

	@Override
	public void deleteBefore(Date date) {
		executeUpdate("delete Logs where createTime<?", date);
	}

}
