package org.yuantai.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yuantai.system.aop.Logging;
import org.yuantai.system.dao.FunctionDao;
import org.yuantai.system.pojo.Function;
import org.yuantai.system.service.FunctionService;

/**
 * 功能服务实现类
 * @author zhangle
 */
@Logging
@Service
public class FunctionServiceImpl extends BaseServiceImpl<Function> implements FunctionService{
	
	@Autowired FunctionDao functionDao;
	
	@Override
	public List<Function> getChildren(String parentId,Integer status) {
		return functionDao.getChildren(parentId,status);
	}
	
	@Override
	public List<Function> getChildrenForRole(String roleId, String parentId, Integer status) {
		return functionDao.getChildrenForRole(roleId, parentId, status);
	}
	
	@Override
	public List<Function> getChildrenForOrgan(String organId, String parentId,Integer status) {
		return functionDao.getChildrenForOrgan(organId, parentId, status);
	}

	@Override
	public void move(String targetId, String sourceId, String point) {
		functionDao.move(targetId, sourceId, point);
	}


}
