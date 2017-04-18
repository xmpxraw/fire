package org.yuantai.school.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yuantai.school.dao.TermDao;
import org.yuantai.school.pojo.Term;
import org.yuantai.school.service.TermService;
import org.yuantai.system.aop.Logging;
import org.yuantai.system.service.impl.BaseServiceImpl;

@Logging
@Service
public class TermServiceImpl extends BaseServiceImpl<Term> implements TermService {
	
@Autowired private TermDao termDao;

	@Override
	public Term getTermByName(String name) {
		return termDao.getTermByName(name);
	}

	@Override
	public List<Term> queryBySysYear(String sysCode,String year) {
		return termDao.queryBySysYear(sysCode,year);
	}

	@Override
	public List<Term> findAll() {
		return termDao.findAll();
	}
	
	@Override
	public List<Term> checkBySysCode(String sysCode) {
		return termDao.checkBySysCode(sysCode);
	}
	
	@Override
	public List<Term> check(String term,String sysCode) {
		return termDao.check(term,sysCode);
	}
	@Override
	public List<Term> checkTermCode(String termCode,String sysCode) {
		return termDao.checkTermCode(termCode,sysCode);
	}
	
}
