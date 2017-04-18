package org.yuantai.basic.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yuantai.basic.dao.ChangelogDao;
import org.yuantai.basic.pojo.Changelog;
import org.yuantai.basic.service.ChangelogService;
import org.yuantai.common.util.DateUtil;
import org.yuantai.system.aop.Logging;
import org.yuantai.system.dao.hibernate.DaoUtil;
import org.yuantai.system.service.impl.BaseServiceImpl;

@Logging
@Service
public class ChangelogServiceImpl extends BaseServiceImpl<Changelog> implements ChangelogService {
	
	@Autowired private ChangelogDao helptextDao;
	@Autowired private DaoUtil daoutil;
	
	@Override
	public void delete(String id) {
		daoutil.update(Changelog.class, id, "status", Changelog.STATUS_DELETED);
	}
	
	@Override
	public Map<String, Changelog> findForAboutus() {
		
		Query query=daoutil.createQuery("from Changelog c where c.status=? order by c.createtime desc",Changelog.STATUS_DEFAULT);
		List<Changelog> logs=query.list();
		
		LinkedHashMap<String, Changelog> treemap=new LinkedHashMap<String, Changelog>();
		for(Changelog log:logs) {
			treemap.put(DateUtil.format(log.getCreatetime(), "yyyy-MM-dd HH:mm"), log);
		}
		return treemap;
	}
}
