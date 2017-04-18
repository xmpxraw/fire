package org.yuantai.school.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yuantai.school.dao.CompanyDao;
import org.yuantai.school.pojo.Company;
import org.yuantai.school.pojo.Term;
import org.yuantai.school.service.CompanyService;
import org.yuantai.system.aop.Logging;
import org.yuantai.system.service.impl.BaseServiceImpl;

@Logging
@Service
public class CompanyServiceImpl extends BaseServiceImpl<Company> implements CompanyService {
	
@Autowired private CompanyDao companyDao;

	@Override
	public Company getCompanyByName(String name) {
		return companyDao.getCompanyByName(name);
	}
	@Override
	public List<Company> checkBySysCode(String sysCode) {
		return companyDao.checkBySysCode(sysCode);
	}

	@Override
	public List<Company> findAll() {
		return companyDao.findAll();
	}
	
	@Override
	public List<String> likeCompanyName(String companyName,String sysCode) {
		return companyDao.likeCompanyName(companyName,sysCode);
	}
}
