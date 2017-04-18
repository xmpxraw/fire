package org.yuantai.school.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yuantai.school.dao.StudentScoreDao;
import org.yuantai.school.pojo.StudentScore;
import org.yuantai.school.service.StudentScoreService;
import org.yuantai.system.aop.Logging;
import org.yuantai.system.service.impl.BaseServiceImpl;

@Logging
@Service
public class StudentScoreServiceImpl extends BaseServiceImpl<StudentScore> implements StudentScoreService {
	
@Autowired private StudentScoreDao studentScoreDao;

@Override
public List<StudentScore> checkIdcard(String idcard,String sysCode) {
	return studentScoreDao.checkIdcard(idcard,sysCode);
}


	@Override
	public List<StudentScore> findAll() {
		return studentScoreDao.findAll();
	}
	
}
