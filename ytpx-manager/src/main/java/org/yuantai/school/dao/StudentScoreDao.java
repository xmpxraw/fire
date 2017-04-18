package org.yuantai.school.dao;

import java.util.List;

import org.yuantai.school.pojo.StudentScore;
import org.yuantai.system.dao.BaseDao;

public interface StudentScoreDao extends BaseDao<StudentScore> {
	
	public List<StudentScore> checkIdcard(String idcard,String sysCode);
	public List<StudentScore> findAll();

}
