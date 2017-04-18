package org.yuantai.school.dao;

import java.util.List;

import org.yuantai.school.pojo.Registe;
import org.yuantai.school.pojo.School;
import org.yuantai.system.dao.BaseDao;

public interface RegisteDao extends BaseDao<Registe> {
	
	public Registe getRegisteByName(String name);
	public Registe getRegisteByDigest(String name);
	public List<Registe> findAll();
	public List<Registe> checkBySysCode(String sysCode);
	public List<Registe> queryByDate(String sysCode,String registeCode);//自增序号
}
