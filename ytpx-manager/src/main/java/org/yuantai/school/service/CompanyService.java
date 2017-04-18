package org.yuantai.school.service;

import java.util.List;

import org.yuantai.school.pojo.Company;
import org.yuantai.school.pojo.Term;
import org.yuantai.system.service.BaseService;

public interface CompanyService extends BaseService<Company> {
	public  List<Company> findAll() ;
	public Company getCompanyByName(String name) ;
	public List<Company> checkBySysCode(String sysCode);
	public List<String> likeCompanyName(String companyName,String sysCode);
}
