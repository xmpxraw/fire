package org.yuantai.school.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;
import org.yuantai.school.dao.StudentDao;
import org.yuantai.school.pojo.Student;
import org.yuantai.school.pojo.Term;
import org.yuantai.system.dao.hibernate.BaseDaoImpl;

@Repository
public class StudentDaoImpl extends BaseDaoImpl<Student> implements StudentDao {
	
	@Override
	public Student getStudentByName(String name) {
		return super.get(Student.class, "name", name);
	}
	@Override
	public Student getStudentByDigest(String digest) {
		return super.get(Student.class, "digest", digest);
	}
	@Override
	public List<Student> checkMobile(String mobile,String sysCode) {		
		Query query = null;
		query = getNamedQuery("school.student.checkMobile");
		query.setString("mobile", mobile);
		query.setString("sysCode", sysCode);
		return query.list();
	}
	
	@Override
	public List<Student> queryByCompany(String companyCode,String sysCode) {		
		Query query = null;
		query = getNamedQuery("school.queryByCompany");
		query.setString("companyCode", companyCode);
		query.setString("sysCode", sysCode);
		return query.list();
	}
	
	@Override
	public List<Student> checkIdcard(String idcard,String sysCode) {
		
		Query query=null;

			query=getNamedQuery("school.student.checkIdcard");
			query.setString("idcard", idcard);
			query.setString("sysCode", sysCode);
		return query.list();
	}
	@Override
	public List<Student> checkRegiste(String sysCode,String registeCode) {
		Query query=null;

			query=getNamedQuery("school.student.checkRegiste");
			query.setString("sysCode", sysCode);
			query.setString("registeCode", registeCode);
		return query.list();
	}
	@Override
	public List<Student> checkClass(String sysCode,String classCode) {
		Query query=null;

			query=getNamedQuery("school.student.checkClass");
			query.setString("classCode", classCode);
			query.setString("sysCode", sysCode);
		return query.list();
	}
	@Override
	public List<Student> checkRegisteStatus(String registeCode) {
		Query query=null;

			query=getNamedQuery("school.student.checkRegisteStatus");
			query.setString("registeCode", registeCode);
		return query.list();
	}
	@Override
	public List<Student> findAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Student.class);
    	
		return super.find(criteria);
	}
	@Override
	public List<Student> checkByTerm(String termCode,String sysCode) {
		
		Query query=null;
	
			query=getNamedQuery("school.student.checkTerm");
			query.setString("termCode", termCode);
			query.setString("sysCode", sysCode);
		return query.list();
	}
	
	//查询学生--学生存在有多个学号或班级
	@Override
	public List<Student> checkStudent(String idcard,String classCode) {
		
		Query query=null;
			query=getNamedQuery("school.student.checkStudent");
			query.setString("idcard", idcard);
			query.setString("classCode", classCode);
		return  query.list();
	}
	
	@Override
	public List<Student> queryStudent(String idcard,String studentNo) {
		
		Query query=null;
			query=getNamedQuery("school.student.queryStudent");
			query.setString("idcard", idcard);
			query.setString("studentNo", studentNo);
		return  query.list();
	}
	
	//查询]班级考试通过数
		@Override
		public List<Student> checkClassPass(String sysCode,String classCode,String pass) {			
			Query query=null;
				query=getNamedQuery("school.student.checkClassPass");
				query.setString("sysCode", sysCode);
				query.setString("classCode", classCode);
				query.setString("pass", pass);
			return query.list();
		}
		@Override
		public List<Student> checkByOpenIdAndMobile(String mobile,String weixinOpenid, String schoolCode) {
			Query query=null;
			query=getNamedQuery("school.student.checkByOpenIdAndMobile");
			query.setString("mobile", mobile);
			query.setString("weixinOpenid", weixinOpenid);
			query.setString("sysCode", schoolCode);
			return query.list();
		}
		@Override
		public List<Student> checkOpenIdByMobile(String mobile, String schoolCode) {
			Query query=null;
			query=getNamedQuery("school.student.checkOpenIdByMobile");
			query.setString("mobile", mobile);
			query.setString("sysCode", schoolCode);
			return query.list();
		}
		@Override
		public List<Student> checkOpenId(String weixinOpenid, String schoolCode) {
			Query query=null;
			query=getNamedQuery("school.student.checkOpenId");
			query.setString("weixinOpenid", weixinOpenid);
			query.setString("sysCode", schoolCode);
			return query.list();
		}
		@Override
		public Student checkByOpneId(String openId) {
			return super.get(Student.class, "weixinOpenid", openId);
		}
		
		@Override
		public List<Student> exportTemplet(String sysCode,String classCode) {
			Query query=null;

				query=getNamedQuery("school.student.exportTemplet");
				query.setString("classCode", classCode);
				query.setString("sysCode", sysCode);
			return query.list();
		}
		
	@Override
	public List<Student> notify(String sysCode,List<Integer> studentStatus,String termCode) {
		Query query = null;
		query = getNamedQuery("school.student.notify");
		query.setString("sysCode", sysCode);		
	//	query.setInteger("studentStatus", studentStatus);
		query.setString("termCode", termCode);
		query.setParameterList("studentStatus",studentStatus );
		return query.list();
	}
	
	@Override
	public List<Student> queryStudentCountByterm(String termCode,String sysCode) {
		
		Query query=null;
	
			query=getNamedQuery("school.queryStudentCountByterm");
			query.setString("termCode", termCode);
			query.setString("sysCode", sysCode);
		return query.list();
	}	

}
