package org.yuantai.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yuantai.system.aop.Logging;
import org.yuantai.system.dao.DataDao;
import org.yuantai.system.pojo.Data;
import org.yuantai.system.service.DataService;

@Logging
@Service
public class DataServiceImpl extends BaseServiceImpl<Data> implements DataService {

	@Autowired private DataDao dataDao;
	
	@Override
	public List<Data> getChildren(String parentId, Integer status) {
		return dataDao.getChildren(parentId, status);
	}

	@Override
	public List<Data> getChildren(String code) {
		return dataDao.getChildren(code);
	}

	@Override
	public Data getByCode(String code) {
		return dataDao.getByCode(code);
	}

	@Override
	public void move(String targetId, String sourceId, String point) {
		dataDao.move(targetId, sourceId, point);
	}

	@Override
	public Data getByCode(String code, String value) {
		return dataDao.getByCode(code, value);
	}
}
