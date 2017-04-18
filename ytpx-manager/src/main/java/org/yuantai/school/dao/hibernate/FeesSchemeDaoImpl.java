package org.yuantai.school.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;
import org.yuantai.school.dao.FeesSchemeDao;
import org.yuantai.school.pojo.FeesScheme;
import org.yuantai.school.pojo.Term;
import org.yuantai.system.dao.hibernate.BaseDaoImpl;

@Repository
public class FeesSchemeDaoImpl extends BaseDaoImpl<FeesScheme> implements FeesSchemeDao {
	
	@Override
	public FeesScheme getFeesSchemeByName(String name) {
		return super.get(FeesScheme.class, "name", name);
	}
	@Override
	public List<FeesScheme> check(Integer schemeStatus,String sysCode) {
		
		Query query=null;
		if(sysCode!=null) {
			query=getNamedQuery("school.checkFees");
			query.setInteger("schemeStatus", schemeStatus);
			query.setString("sysCode", sysCode);
			
		} 

		return query.list();
	}
	@Override
	public List<FeesScheme> check(Integer schemeStatus) {
		
		Query query=null;
			query=getNamedQuery("school.checkFee");
			query.setInteger("schemeStatus", schemeStatus);
		return query.list();
	}
	@Override
	public List<FeesScheme> findAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(FeesScheme.class);
    	
		return super.find(criteria);
	}
	
}
