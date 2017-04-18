package org.yuantai.school.dao;

import java.util.List;

import org.yuantai.school.pojo.Company;
import org.yuantai.school.pojo.Term;
import org.yuantai.system.dao.BaseDao;

public interface CompanyDao extends BaseDao<Company> {
	
	public Company getCompanyByName(String name);
	public List<Company> findAll();
	public List<Company> checkBySysCode(String sysCode);
	public List<String> likeCompanyName(String companyName,String sysCode);
}
