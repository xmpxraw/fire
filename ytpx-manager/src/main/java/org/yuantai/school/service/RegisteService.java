package org.yuantai.school.service;

import java.util.List;

import org.yuantai.school.pojo.Registe;
import org.yuantai.school.pojo.School;
import org.yuantai.system.service.BaseService;

public interface RegisteService extends BaseService<Registe> {
	public  List<Registe> findAll() ;
	public Registe getRegisteByName(String name) ;
	public List<Registe> checkBySysCode(String sysCode);
	public List<Registe> queryByDate(String sysCode,String registeCode);//自增序号
}
