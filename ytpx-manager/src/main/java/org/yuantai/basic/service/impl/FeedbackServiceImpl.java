package org.yuantai.basic.service.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yuantai.basic.dao.FeedbackDao;
import org.yuantai.basic.pojo.Feedback;
import org.yuantai.basic.service.FeedbackService;
import org.yuantai.system.aop.Logging;
import org.yuantai.system.dao.hibernate.DaoUtil;
import org.yuantai.system.service.impl.BaseServiceImpl;

@Logging
@Service
public class FeedbackServiceImpl extends BaseServiceImpl<Feedback> implements FeedbackService {
	
	@Autowired private FeedbackDao newsDao;
	@Autowired private DaoUtil daoutil;
	
	@Override
	public void delete(String id) {
		daoutil.update(Feedback.class, id, "status", Feedback.STATUS_DELETED);
	}
	
	@Override
	public void add(Feedback obj) {
		obj.setSeqnum(daoutil.nextSeqnum(Feedback.class, "seqnum"));
		super.add(obj);
	}
	
	@Override
	public void sort(Feedback[] feedbacks) {
		
		Query sortQuery=daoutil.createQuery("update Feedback set seqnum=:seqnum where id=:id");
		for(Feedback feedback:feedbacks) {
			sortQuery.setLong("seqnum", feedback.getSeqnum());
			sortQuery.setString("id", feedback.getId());
			sortQuery.executeUpdate();
		}
	}
	
	public List<Feedback> findForHome(String pid,int startrow, int pagesize) {
		Query query=daoutil.createQuery("from Feedback f where f.pid=? and f.status=? order by f.seqnum desc",pid,Feedback.STATUS_PUBLIC);
		query.setFirstResult(startrow);
		query.setMaxResults(pagesize);
		return query.list();
	}
}
