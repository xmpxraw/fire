package org.yuantai.school.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;
import org.yuantai.school.dao.CompanyDao;
import org.yuantai.school.pojo.Company;
import org.yuantai.school.pojo.Term;
import org.yuantai.system.dao.hibernate.BaseDaoImpl;

@Repository
public class CompanyDaoImpl extends BaseDaoImpl<Company> implements CompanyDao {
	
	@Override
	public Company getCompanyByName(String name) {
		return super.get(Company.class, "companyName", name);
	}

	@Override
	public List<Company> findAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Company.class);
    	
		return super.find(criteria);
	}
	@Override
	public List<Company> checkBySysCode(String sysCode) {
		
		Query query=null;
		if(sysCode!=null) {
			query=getNamedQuery("school.checkCompanyBySysCode");
			query.setString("sysCode", sysCode);
		} 
		return query.list();
	}
	
	@Override
	public List<String> likeCompanyName(String companyName,String sysCode) {
		
		Query query=null;
		if(sysCode!=null) {
			query=getNamedQuery("school.likeCompanyName");
			query.setString("companyName", companyName);
			query.setString("sysCode", sysCode);
		} 
		return query.list();
	}
	
}
