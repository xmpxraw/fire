package org.yuantai.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yuantai.system.aop.Logging;
import org.yuantai.system.dao.MenuDao;
import org.yuantai.system.pojo.Menu;
import org.yuantai.system.service.MenuService;

/**
 * 菜单服务实现类
 * @author zhangle
 */
@Logging
@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu> implements MenuService {

	@Autowired private MenuDao menuDao;

	@Override
	public List<Menu> getChildren(String parentId,Integer status) {
		return menuDao.getChildren(parentId,status);
	}

	@Override
	public void move(String targetId, String sourceId, String point) {
		menuDao.move(targetId, sourceId, point);
	}
	
}
