package org.yuantai.school.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;
import org.yuantai.school.dao.TermDao;
import org.yuantai.school.pojo.Term;
import org.yuantai.system.dao.hibernate.BaseDaoImpl;

@Repository
public class TermDaoImpl extends BaseDaoImpl<Term> implements TermDao {
	
	@Override
	public Term getTermByName(String name) {
		return super.get(Term.class, "term", name);
	}
	@Override
	public List<Term> check(String term,String sysCode) {
		
		Query query=null;
		if(sysCode!=null) {
			query=getNamedQuery("school.checkTerm");
			query.setString("term", term);
			query.setString("sysCode", sysCode);
			
		} 

		return query.list();
	}
	
	@Override
	public List<Term> checkTermCode(String termCode,String sysCode) {
		
		Query query=null;
			query=getNamedQuery("school.checkTermCode");
			query.setString("termCode", termCode);
			query.setString("sysCode", sysCode);

		return query.list();
	}
	
	@Override
	public List<Term> findAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Term.class);
    	
		return super.find(criteria);
	}
	@Override
	public List<Term> checkBySysCode(String sysCode) {		
		Query query=null;
		if(sysCode!=null) {
			query=getNamedQuery("school.checkTermBySysCode");
			query.setString("sysCode", sysCode);
		} 
		return query.list();
	}
	
	@Override
	public List<Term> queryBySysYear(String sysCode,String year) {		
		Query query=null;
		if(sysCode!=null) {
			query=getNamedQuery("school.queryByYear");
			query.setString("sysCode", sysCode);
			query.setString("years", year);
		} 
		return query.list();
	}
	
}
