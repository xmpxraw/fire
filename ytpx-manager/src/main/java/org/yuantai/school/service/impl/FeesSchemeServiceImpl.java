package org.yuantai.school.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yuantai.school.dao.FeesSchemeDao;
import org.yuantai.school.pojo.FeesScheme;
import org.yuantai.school.pojo.Term;
import org.yuantai.school.service.FeesSchemeService;
import org.yuantai.system.aop.Logging;
import org.yuantai.system.service.impl.BaseServiceImpl;

@Logging
@Service
public class FeesSchemeServiceImpl extends BaseServiceImpl<FeesScheme> implements FeesSchemeService {
	
@Autowired private FeesSchemeDao feesSchemeDao;

	@Override
	public FeesScheme getFeesSchemeByName(String name) {
		return feesSchemeDao.getFeesSchemeByName(name);
	}


	@Override
	public List<FeesScheme> findAll() {
		return feesSchemeDao.findAll();
	}
	@Override
	public List<FeesScheme> check(Integer schemeStatus,String sysCode) {
		return feesSchemeDao.check(schemeStatus,sysCode);
	}
	@Override
	public List<FeesScheme> check(Integer schemeStatus) {
		return feesSchemeDao.check(schemeStatus);
	}
}
