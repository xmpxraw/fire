package org.yuantai.system.auth;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.yuantai.system.dao.AuthDao;
import org.yuantai.system.pojo.Menu;
import org.yuantai.system.pojo.OnlineUser;

/**
 * 基于组织机构的权限验证处理类
 * @author zhangle
 */
@Component
public class OrganAuthHandler extends AuthHandler {

	@Resource(name="organAuthDaoImpl") private AuthDao authDao;
	
	@Override
	public boolean handleUrl(OnlineUser loginUser, String url) {
		if(authDao.checkUrl(loginUser, url)) return true;
		return getNextHandler().handleUrl(loginUser, url);
	}

	@Override
	public boolean handleCode(OnlineUser loginUser, String functionCode) {
		if(authDao.checkCode(loginUser, functionCode)) return true;
		return getNextHandler().handleCode(loginUser, functionCode);
	}

	@Override
	public List<Menu> handleMenu(OnlineUser loginUser, String parentId) {
		List<Menu> menus=authDao.checkMenu(loginUser, parentId);
		List<Menu> otherMenus=getNextHandler().handleMenu(loginUser, parentId);
		menus.addAll(otherMenus);
		return menus;
	}

	@Override
	public void refreshCache() {
		authDao.refreshCache();
		getNextHandler().refreshCache();
	}

}
