package org.yuantai.school.dao;

import java.util.List;

import org.yuantai.school.pojo.Term;
import org.yuantai.system.dao.BaseDao;

public interface TermDao extends BaseDao<Term> {
	
	public Term getTermByName(String name);
	public List<Term> check(String name,String sysCode);
	public List<Term> checkTermCode(String termCode,String sysCode);
	public List<Term> findAll();
	public List<Term> checkBySysCode(String sysCode);
	public List<Term> queryBySysYear(String sysCode,String year);
	
}
