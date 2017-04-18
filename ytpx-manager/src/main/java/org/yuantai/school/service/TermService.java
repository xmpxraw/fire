package org.yuantai.school.service;

import java.util.List;

import org.yuantai.school.pojo.Term;
import org.yuantai.system.service.BaseService;

public interface TermService extends BaseService<Term> {
	public  List<Term> findAll() ;
	public Term getTermByName(String name) ;
	public List<Term> checkBySysCode(String sysCode);
	public List<Term> checkTermCode(String termCode,String sysCode);
	public List<Term> check(String term,String sysCode);
	public List<Term> queryBySysYear(String sysCode,String year);
}
