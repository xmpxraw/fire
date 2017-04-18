package org.yuantai.school.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yuantai.school.dao.RegisteDao;
import org.yuantai.school.pojo.Registe;
import org.yuantai.school.pojo.School;
import org.yuantai.school.service.RegisteService;
import org.yuantai.system.aop.Logging;
import org.yuantai.system.service.impl.BaseServiceImpl;

@Logging
@Service
public class RegisteServiceImpl extends BaseServiceImpl<Registe> implements RegisteService {
	
@Autowired private RegisteDao registeDao;

	@Override
	public Registe getRegisteByName(String name) {
		return registeDao.getRegisteByName(name);
	}


	@Override
	public List<Registe> findAll() {
		return registeDao.findAll();
	}
	@Override
	public List<Registe> checkBySysCode(String sysCode) {
		return registeDao.checkBySysCode(sysCode);
	}
	
	@Override
	public List<Registe> queryByDate(String sysCode,String registeCode) {
		return registeDao.queryByDate(sysCode, registeCode);
	}
}
