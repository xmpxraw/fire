package org.yuantai.basic.service.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yuantai.basic.dao.NewsDao;
import org.yuantai.basic.pojo.News;
import org.yuantai.basic.service.NewsService;
import org.yuantai.system.aop.Logging;
import org.yuantai.system.dao.hibernate.DaoUtil;
import org.yuantai.system.service.impl.BaseServiceImpl;

@Logging
@Service
public class NewsServiceImpl extends BaseServiceImpl<News> implements NewsService {
	
	@Autowired private NewsDao newsDao;
	@Autowired private DaoUtil daoutil;
	
	@Override
	public void delete(String id) {
		daoutil.update(News.class, id, "status", News.STATUS_DELETED);
	}
	
	@Override
	public void add(News obj) {
		obj.setSeqnum(daoutil.nextSeqnum(News.class, "seqnum"));
		super.add(obj);
	}
	
	@Override
	public void sort(News[] news) {
		
		Query sortQuery=daoutil.createQuery("update News set seqnum=:seqnum where id=:id");
		for(News text:news) {
			sortQuery.setLong("seqnum", text.getSeqnum());
			sortQuery.setString("id", text.getId());
			sortQuery.executeUpdate();
		}
	}

	@Override
	public List<News> findForHome(int startrow, int pagesize) {
		Query query=daoutil.createQuery("from News n where n.status=? order by n.seqnum desc",News.STATUS_PUBLIC);
		query.setFirstResult(startrow);
		query.setMaxResults(pagesize);
		return query.list();
	}
	
}
