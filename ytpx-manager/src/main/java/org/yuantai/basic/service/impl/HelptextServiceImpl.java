package org.yuantai.basic.service.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yuantai.basic.dao.HelptextDao;
import org.yuantai.basic.pojo.Helptext;
import org.yuantai.basic.service.HelptextService;
import org.yuantai.system.aop.Logging;
import org.yuantai.system.dao.hibernate.DaoUtil;
import org.yuantai.system.service.impl.BaseServiceImpl;

@Logging
@Service
public class HelptextServiceImpl extends BaseServiceImpl<Helptext> implements HelptextService {
	
	@Autowired private HelptextDao helptextDao;
	@Autowired private DaoUtil daoutil;
	
	@Override
	public void delete(String id) {
		daoutil.update(Helptext.class, id, "status", Helptext.STATUS_DELETED);
	}
	
	@Override
	public void add(Helptext obj) {
		obj.setSeqnum(daoutil.nextSeqnum(Helptext.class, "seqnum"));
		super.add(obj);
	}
	
	@Override
	public void sort(Helptext[] helptexts) {
		
		Query sortQuery=daoutil.createQuery("update Helptext set seqnum=:seqnum where id=:id");
		for(Helptext text:helptexts) {
			sortQuery.setLong("seqnum", text.getSeqnum());
			sortQuery.setString("id", text.getId());
			sortQuery.executeUpdate();
		}
	}
	
	@Override
	public List<Helptext> findForHome(int startrow, int pagesize) {
		Query query=daoutil.createQuery("from Helptext h where h.status=? order by h.seqnum",Helptext.STATUS_PUBLIC_HOME);
		query.setFirstResult(startrow);
		query.setMaxResults(pagesize);
		return query.list();
	}
}
