package org.yuantai.school.service;

import java.util.List;

import org.yuantai.school.pojo.StudentScore;
import org.yuantai.system.service.BaseService;

public interface StudentScoreService extends BaseService<StudentScore> {
	public  List<StudentScore> findAll() ;
	public List<StudentScore> checkIdcard(String idcard,String sysCode);
}
