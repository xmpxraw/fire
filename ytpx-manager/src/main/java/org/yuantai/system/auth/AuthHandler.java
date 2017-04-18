package org.yuantai.system.auth;

import java.util.List;

import org.yuantai.system.pojo.Menu;
import org.yuantai.system.pojo.OnlineUser;

/**
 * 权限处理器
 * 使用职责链模式
 * @author zhangle
 */
public abstract class AuthHandler {

	private AuthHandler nextHandler;		//下一个处理者
	
	public AuthHandler getNextHandler() {
		return nextHandler;
	}

	public void setNextHandler(AuthHandler nextHandler) {
		this.nextHandler = nextHandler;
	}
	
	/**
	 * 处理基于URL的权限验证
	 * @param loginUser			登录用户
	 * @param url				访问地址
	 * @return					true:通过,false:不通过
	 */
	public abstract boolean handleUrl(OnlineUser loginUser,String url);
	
	/**
	 * 处理基于code的权限验证
	 * @param loginUser			登录用户
	 * @param functionCode		功能code
	 * @return					true:通过,false:不通过
	 */
	public abstract boolean handleCode(OnlineUser loginUser,String functionCode);
	
	/**
	 * 检查并获取角色有权访问的子菜单
	 * @param loginUser		登录的用户
	 * @param parentId		父菜单ID,当parentId==null时,获取顶级子菜单
	 * @return
	 */
	public abstract List<Menu> handleMenu(OnlineUser loginUser,String parentId);
	
	/**
	 * 刷新权限相关的缓存
	 */
	public abstract void refreshCache();
}
