package org.yuantai.system.service;

import java.util.List;

import org.yuantai.system.pojo.Menu;
import org.yuantai.system.pojo.OnlineUser;

/**
 * 权限验证服务
 * @author zhangle
 */
public interface AuthService {

	/**
	 * 验证权限
	 * 验证用户是否有权访问url
	 * @param loginUser		登录的用户,为null时,表示未登录
	 * @param url			访问的url
	 * @return
	 */
	public boolean checkUrl(OnlineUser loginUser,String url);
	
	/**
	 * 验证权限
	 * 验证用户是否有权访问功能code,此方法供自定义标签使用
	 * @param loginUser		登录的用户,为null时,表示未登录
	 * @param functionCode	功能code
	 * @return
	 */
	public boolean checkCode(OnlineUser loginUser,String functionCode);
	
	/**
	 * 检查并获取角色有权访问的子菜单
	 * @param loginUser		登录的用户,为null时,表示未登录
	 * @param parentId		父菜单ID,当parentId==null时,获取顶级子菜单
	 * @return
	 */
	public List<Menu> checkMenu(OnlineUser loginUser,String parentId);
	
	/**
	 * 刷新权限缓存
	 */
	public void refreshCache();
}
