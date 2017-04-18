package org.yuantai.school.dao;

import java.util.List;

import org.yuantai.school.pojo.FeesScheme;
import org.yuantai.school.pojo.Term;
import org.yuantai.system.dao.BaseDao;

public interface FeesSchemeDao extends BaseDao<FeesScheme> {
	
	public FeesScheme getFeesSchemeByName(String name);
	public List<FeesScheme> findAll();
	public List<FeesScheme> check(Integer schemeStatus,String sysCode);
	public List<FeesScheme> check(Integer schemeStatus);

}
