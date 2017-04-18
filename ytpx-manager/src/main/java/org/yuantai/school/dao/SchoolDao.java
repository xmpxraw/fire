package org.yuantai.school.dao;

import java.util.List;

import org.yuantai.school.pojo.School;
import org.yuantai.school.pojo.Term;
import org.yuantai.system.dao.BaseDao;

public interface SchoolDao extends BaseDao<School> {
	public School getSchoolCode(String schoolCode) ;
	public School getSchoolByName(String name);
	public School getSchoolBySysCode(String sysCode);
	public List<School> findAll();
	public List<School> checkBySysCode(String sysCode);
	
}
