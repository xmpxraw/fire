package org.yuantai.basic.dao.hibernate;

import org.springframework.stereotype.Repository;
import org.yuantai.basic.dao.FeedbackDao;
import org.yuantai.basic.pojo.Feedback;
import org.yuantai.system.dao.hibernate.BaseDaoImpl;

@Repository
public class FeedbackDaoImpl extends BaseDaoImpl<Feedback> implements FeedbackDao {

	@Override
	public void delete(String id) {
		update(id, "status", Feedback.STATUS_DELETED);
	}
}
