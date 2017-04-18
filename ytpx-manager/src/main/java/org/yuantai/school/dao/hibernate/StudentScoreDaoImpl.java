package org.yuantai.school.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;
import org.yuantai.school.dao.StudentScoreDao;
import org.yuantai.school.pojo.Student;
import org.yuantai.school.pojo.StudentScore;
import org.yuantai.system.dao.hibernate.BaseDaoImpl;

@Repository
public class StudentScoreDaoImpl extends BaseDaoImpl<StudentScore> implements StudentScoreDao {
	
	@Override
	public List<StudentScore> checkIdcard(String idcard,String sysCode) {
		
		Query query=null;

			query=getNamedQuery("school.studentScore.checkIdcard");
			query.setString("idcard", idcard);
			query.setString("sysCode", sysCode);
		return query.list();
	}
	
	@Override
	public List<StudentScore> findAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(StudentScore.class);
    	
		return super.find(criteria);
	}
	
}
