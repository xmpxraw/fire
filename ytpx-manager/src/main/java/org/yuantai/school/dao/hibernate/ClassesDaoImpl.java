package org.yuantai.school.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;
import org.yuantai.school.dao.ClassesDao;
import org.yuantai.school.pojo.Classes;
import org.yuantai.school.pojo.Company;
import org.yuantai.system.dao.hibernate.BaseDaoImpl;

@Repository
public class ClassesDaoImpl extends BaseDaoImpl<Classes> implements ClassesDao {
	
	@Override
	public Classes getClassesByName(String name) {
		return super.get(Classes.class, "className", name);
	}
	
	@Override
	public Classes getClassesByCode(String classCode) {
		return super.get(Classes.class, "classCode", classCode);
	}

	@Override
	public List<Classes> checkBySysCode(String sysCode) {
		
		Query query=null;
		if(sysCode!=null) {
			query=getNamedQuery("school.checkClassesBySysCode");
			query.setString("sysCode", sysCode);
		} 

		return query.list();
	}
	
	@Override
	public List<Classes> findAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Classes.class);
    	
		return super.find(criteria);
	}
	
	@Override
	public List<Classes> checkStatus(String sysCode,String termCode) {
		
		Query query=null;
		if(sysCode!=null) {
			query=getNamedQuery("school.checkClassStatusBySysCode");
			query.setString("sysCode", sysCode);
			query.setString("termCode", termCode);
		} 
		return query.list();
	}
	//查询某个学校的某个班级
	@Override
	public List<Classes> findClasses(String classCode,String sysCode) {
		Query query=null;
		if(sysCode!=null) {
			query=getNamedQuery("school.findClass");
			query.setString("classCode", classCode);
			query.setString("sysCode", sysCode);
		} 
		return query.list();
	}
	//查询突击班
	@Override
	public List<Classes> findSpringClass(String sysCode,String termCode) {
		
		Query query=null;
		if(sysCode!=null) {
			query=getNamedQuery("school.findSpringClass");
			query.setString("sysCode", sysCode);
			query.setString("termCode", termCode);
		} 
		return query.list();
	}
	
	//每期的班级
	@Override
	public List<Classes> queryCount(String sysCode,String termCode) {
		Query query=null;
		if(sysCode!=null) {
			query=getNamedQuery("school.queryCountByTerm");
			query.setString("sysCode", sysCode);
			query.setString("termCode", termCode);
		} 
		return query.list();
	}
}
