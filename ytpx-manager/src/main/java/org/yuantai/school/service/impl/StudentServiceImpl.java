package org.yuantai.school.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yuantai.school.dao.StudentDao;
import org.yuantai.school.pojo.School;
import org.yuantai.school.pojo.Student;
import org.yuantai.school.pojo.Term;
import org.yuantai.school.service.StudentService;
import org.yuantai.system.aop.Logging;
import org.yuantai.system.service.impl.BaseServiceImpl;

@Logging
@Service
public class StudentServiceImpl extends BaseServiceImpl<Student> implements StudentService {
	
@Autowired private StudentDao studentDao;

	@Override
	public Student getStudentByName(String name) {
		return studentDao.getStudentByName(name);
	}
	@Override
	public List<Student> queryByCompany(String companyCode,String sysCode) {
		return studentDao.queryByCompany(companyCode,sysCode);
	}

	@Override
	public List<Student> findAll() {
		return studentDao.findAll();
	}
	@Override
	public List<Student> checkMobile(String mobile,String sysCode) {
		return studentDao.checkMobile(mobile,sysCode);
	}
	@Override
	public List<Student> checkIdcard(String idcard,String sysCode) {
		return studentDao.checkIdcard(idcard,sysCode);
	}
	@Override
	public List<Student> checkRegiste(String sysCode,String registeCode) {
		return studentDao.checkRegiste(sysCode,registeCode);
	}
	@Override
	public List<Student> checkClass(String sysCode,String classCode) {
		return studentDao.checkClass(sysCode,classCode);
	}
	@Override
	public List<Student> checkRegisteStatus(String registeCode) {
		return studentDao.checkRegisteStatus(registeCode);
	}
	@Override
	public List<Student> checkByTerm(String termCode,String sysCode) {
		return studentDao.checkByTerm(termCode,sysCode);
	}
	@Override
	public List<Student> checkStudent(String idcard,String classCode) {
		return studentDao.checkStudent(idcard,classCode);
	}
	@Override
	public List<Student> checkClassPass(String sysCode,String classCode,String pass){
		return studentDao.checkClassPass(sysCode,classCode,pass);
	}
	
	@Override
	public List<Student> checkByOpenIdAndMobile(String mobile,String openId,String schoolCode) {
		return studentDao.checkByOpenIdAndMobile(mobile,openId,schoolCode);
	}
	@Override
	public List<Student> checkOpenIdByMobile(String mobile,String schoolCode) {
		return studentDao.checkOpenIdByMobile(mobile,schoolCode);
	}
	@Override
	public List<Student> checkOpenId(String openId,String schoolCode) {
		return studentDao.checkOpenId(openId,schoolCode);
	}
	
	public Student checkByOPenId(String openId) {
		return studentDao.checkByOpneId(openId);
	}
	
	@Override
	public List<Student> queryStudent(String idcard,String studentNo) {
		return studentDao.queryStudent(idcard,studentNo);
	}
	
	@Override
	public List<Student> exportTemplet(String sysCode,String classCode) {
		return studentDao.exportTemplet(sysCode,classCode);
	}
	
	@Override
	public List<Student> notify(String sysCode,List<Integer> studentStatus,String termCode) {
		return studentDao.notify(sysCode,studentStatus,termCode);
	}
	
	@Override
	public List<Student> queryStudentCountByterm(String termCode,String sysCode) {
		return studentDao.queryStudentCountByterm(termCode,sysCode);
	}
}
