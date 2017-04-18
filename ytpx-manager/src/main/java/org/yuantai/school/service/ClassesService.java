package org.yuantai.school.service;

import java.util.List;

import org.yuantai.school.pojo.Classes;
import org.yuantai.school.pojo.School;
import org.yuantai.system.service.BaseService;

public interface ClassesService extends BaseService<Classes> {
	public  List<Classes> findAll() ;
	public Classes getClassesByName(String name) ;
	public Classes getClassesByCode(String classCode) ;
	public List<Classes> checkBySysCode(String sysCode);
	public List<Classes> checkStatus(String sysCode,String termCode);
	public List<Classes> findClasses(String classCode,String sysCode);
	public List<Classes> findSpringClass(String sysCode, String termCode);//查询突击班
	public List<Classes> queryCount(String sysCode,String termCode);//每期的班级数
}

