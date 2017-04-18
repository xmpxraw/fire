package org.yuantai.school.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yuantai.school.dao.ClassesDao;
import org.yuantai.school.pojo.Classes;
import org.yuantai.school.pojo.Company;
import org.yuantai.school.service.ClassesService;
import org.yuantai.system.aop.Logging;
import org.yuantai.system.service.impl.BaseServiceImpl;

@Logging
@Service
public class ClassesServiceImpl extends BaseServiceImpl<Classes> implements ClassesService {
	
@Autowired private ClassesDao classesDao;

	@Override
	public Classes getClassesByName(String name) {
		return classesDao.getClassesByName(name);
	}
	
	@Override
	public Classes getClassesByCode(String classCode){
		return classesDao.getClassesByCode(classCode);
	}
	@Override
	public List<Classes> checkBySysCode(String sysCode) {
		return classesDao.checkBySysCode(sysCode);
	}

	@Override
	public List<Classes> findAll() {
		return classesDao.findAll();
	}
	@Override
	public List<Classes> checkStatus(String sysCode,String termCode) {
		return classesDao.checkStatus(sysCode,termCode);
	}
	
	@Override
	public List<Classes> findSpringClass(String sysCode,String termCode) {
		return classesDao.findSpringClass(sysCode,termCode);
	}
	
	@Override
	public List<Classes> findClasses(String classCode,String sysCode) {
		return classesDao.findClasses(classCode,sysCode);
	}
	
	@Override
	public List<Classes> queryCount(String sysCode,String termCode) {
		return classesDao.queryCount(sysCode,termCode);
	}
}
