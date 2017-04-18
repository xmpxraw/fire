package org.yuantai.school.dao;

import java.util.List;

import org.yuantai.school.pojo.School;
import org.yuantai.school.pojo.Student;
import org.yuantai.school.pojo.Term;
import org.yuantai.system.dao.BaseDao;

public interface StudentDao extends BaseDao<Student> {
	
	public Student getStudentByName(String name);
	public Student getStudentByDigest(String name);
	public List<Student> findAll();
	public List<Student> queryByCompany(String companyCode,String sysCode);
	public List<Student> checkMobile(String mobile,String sysCode);
	public List<Student> checkIdcard(String idcard,String sysCode);
	public List<Student> checkRegiste(String sysCode,String registeCode);
	public List<Student> checkClass(String sysCode,String classCode);
	public List<Student> checkByTerm(String termCode,String sysCode);
	public List<Student> checkRegisteStatus(String registeCode);
	public List<Student> checkStudent(String idcard,String classCode);
	public List<Student> checkClassPass(String sysCode,String classCode,String pass);
	public List<Student> checkByOpenIdAndMobile(String mobile,String openId,String schoolCode);
	public List<Student> checkOpenIdByMobile(String mobile,String schoolCode);
	public List<Student> checkOpenId(String openId,String schoolCode);
	public Student checkByOpneId(String openId);
	public List<Student> queryStudent(String idcard,String studentNo);
	public List<Student> exportTemplet(String sysCode,String classCode);
	public List<Student> notify(String sysCode,List<Integer> studentStatus,String termCode);
	public List<Student> queryStudentCountByterm(String termCode,String sysCode);
}
