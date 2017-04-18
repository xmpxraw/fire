package org.yuantai.school.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;
import org.yuantai.school.dao.RegisteDao;
import org.yuantai.school.pojo.Registe;
import org.yuantai.school.pojo.School;
import org.yuantai.system.dao.hibernate.BaseDaoImpl;

@Repository
public class RegisteDaoImpl extends BaseDaoImpl<Registe> implements RegisteDao {
	
	@Override
	public Registe getRegisteByName(String name) {
		return super.get(Registe.class, "registeCode", name);
	}
	@Override
	public Registe getRegisteByDigest(String digest) {
		return super.get(Registe.class, "digest", digest);
	}
	
	@Override
	public List<Registe> findAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Registe.class);
    	
		return super.find(criteria);
	}
	
	@Override
	public List<Registe> checkBySysCode(String sysCode) {
		
		Query query=null;
			query=getNamedQuery("school.checkRegistBySysCode");
			query.setString("sysCode", sysCode);
		return query.list();
	}
	
	@Override
	public List<Registe> queryByDate(String sysCode,String registeCode) {
		Query query=null;
			query=getNamedQuery("school.queryByDate");
			query.setString("sysCode", sysCode);
			query.setString("registeCode", registeCode);
		return query.list();
	}
	
}
