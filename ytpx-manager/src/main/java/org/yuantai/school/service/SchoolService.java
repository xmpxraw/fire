package org.yuantai.school.service;

import java.util.List;

import org.yuantai.school.pojo.School;
import org.yuantai.school.pojo.Term;
import org.yuantai.system.service.BaseService;

public interface SchoolService extends BaseService<School> {
	public  List<School> findAll() ;
	public School getSchoolByName(String name) ;
	public School getSchoolCode(String schoolCode) ;
	public School getSchoolBySysCode(String sysCode);
	public List<School> checkBySysCode(String sysCode);

}
