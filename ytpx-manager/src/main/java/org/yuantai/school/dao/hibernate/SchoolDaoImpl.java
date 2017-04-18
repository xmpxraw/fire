package org.yuantai.school.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;
import org.yuantai.school.dao.SchoolDao;
import org.yuantai.school.pojo.School;
import org.yuantai.school.pojo.Term;
import org.yuantai.system.dao.hibernate.BaseDaoImpl;

@Repository
public class SchoolDaoImpl extends BaseDaoImpl<School> implements SchoolDao {
	
	@Override
	public School getSchoolByName(String name) {
		return super.get(School.class, "name", name);
	}
	@Override
	public School getSchoolCode(String schoolCode) {
		return super.get(School.class, "schoolCode", schoolCode);
	}
	@Override
	public School getSchoolBySysCode(String sysCode) {
		return super.get(School.class, "sysCode", sysCode);
	}
	
	@Override
	public List<School> findAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(School.class);
    	
		return super.find(criteria);
	}
	
	@Override
	public List<School> checkBySysCode(String sysCode) {
		
		Query query=null;
		if(sysCode!=null) {
			query=getNamedQuery("school.checkBySysCode");
			query.setString("sysCode", sysCode);
		} 

		return query.list();
	}
	

}
