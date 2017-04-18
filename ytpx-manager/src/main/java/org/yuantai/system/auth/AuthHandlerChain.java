package org.yuantai.system.auth;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.yuantai.system.pojo.Menu;
import org.yuantai.system.pojo.OnlineUser;

/**
 * 权限验证职责链的默认头节点,负责配置整个职责链
 * @author zhangle
 */
@Component
public class AuthHandlerChain extends AuthHandler {

	@Autowired private RoleAuthHandler roleAuthHandler;
	@Autowired private OrganAuthHandler organAuthHandler;
	
	@PostConstruct
	public void init() {
		this.setNextHandler(roleAuthHandler);
		roleAuthHandler.setNextHandler(organAuthHandler);
		organAuthHandler.setNextHandler(new DefaultAuthHandler());
	}
	
	@Override
	public boolean handleUrl(OnlineUser loginUser, String url) {
		return getNextHandler().handleUrl(loginUser, url);
	}

	@Override
	public boolean handleCode(OnlineUser loginUser, String functionCode) {
		return getNextHandler().handleCode(loginUser, functionCode);
	}

	@Override
	public List<Menu> handleMenu(OnlineUser loginUser, String parentId) {
		List<Menu> menus=getNextHandler().handleMenu(loginUser, parentId);
		Collections.sort(menus);
		return removeDuplicates(menus);
	}
	
	/**
	 * 删除重复的菜单
	 * @param menus
	 * @return
	 */
	private List<Menu> removeDuplicates(List<Menu> menus) {
		for(int i=menus.size()-2;i>=0;i--) {
			if(menus.get(i).equals(menus.get(i+1))) {
				menus.remove(i);
			}
		}
		return menus;
	}

	@Override
	public void refreshCache() {
		getNextHandler().refreshCache();
	}
	
	/**
	 * 默认的权限处理器
	 * @author zhangle
	 */
	public static class DefaultAuthHandler extends AuthHandler {

		@Override
		public boolean handleUrl(OnlineUser loginUser, String url) {
			return false;
		}

		@Override
		public boolean handleCode(OnlineUser loginUser, String functionCode) {
			return false;
		}

		@Override
		public List<Menu> handleMenu(OnlineUser loginUser, String parentId) {
			return Collections.emptyList();
		}
		
		@Override
		public void refreshCache() {}
	}
}
