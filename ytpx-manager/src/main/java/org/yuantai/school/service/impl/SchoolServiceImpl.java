package org.yuantai.school.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yuantai.school.dao.SchoolDao;
import org.yuantai.school.pojo.School;
import org.yuantai.school.pojo.Term;
import org.yuantai.school.service.SchoolService;
import org.yuantai.system.aop.Logging;
import org.yuantai.system.service.impl.BaseServiceImpl;

@Logging
@Service
public class SchoolServiceImpl extends BaseServiceImpl<School> implements SchoolService {
	
@Autowired private SchoolDao schoolDao;

	@Override
	public School getSchoolByName(String name) {
		return schoolDao.getSchoolByName(name);
	}
	@Override
	public School getSchoolCode(String schoolCode) {
		return schoolDao.getSchoolCode(schoolCode);
	}
	
	@Override
	public School getSchoolBySysCode(String sysCode) {
		return schoolDao.getSchoolBySysCode(sysCode);
	}


	@Override
	public List<School> findAll() {
		return schoolDao.findAll();
	}
	@Override
	public List<School> checkBySysCode(String sysCode) {
		return schoolDao.checkBySysCode(sysCode);
	}
	
	
	
}
