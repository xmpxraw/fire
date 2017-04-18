package org.yuantai.system.dao;

import java.util.List;

import org.yuantai.system.pojo.Menu;
import org.yuantai.system.pojo.OnlineUser;

/**
 * 权限控制相关功能的dao接口
 * @author zhangle
 */
public interface AuthDao {

	/**
	 * 检查用户是否有权访问url
	 * @param loginUser		用户
	 * @param url			访问的url
	 * @return
	 */
	public boolean checkUrl(OnlineUser loginUser,String url);
	
	/**
	 * 验证用户是否有权访问功能code,此方法供自定义标签使用
	 * @param loginUser
	 * @param functionCode
	 * @return
	 */
	public boolean checkCode(OnlineUser loginUser,String functionCode);
	
	/**
	 * 检查并获取用户有权访问的子菜单
	 * @param loginUser
	 * @param parentId		父菜单ID,当parentId==null时,获取顶级子菜单
	 * @return
	 */
	public List<Menu> checkMenu(OnlineUser loginUser,String parentId);
	
	/**
	 * 刷新权限缓存
	 */
	public void refreshCache();
	
}
