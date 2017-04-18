package org.yuantai.system.service;

import java.util.List;

import org.yuantai.system.pojo.User;

/**
 * 用户服务接口
 * @author zhangle
 */
public interface UserService extends BaseService<User>{
	
	/**
	 * 根据用户名查找用户对象
	 * @param name
	 * @return
	 */
	public User getUserByName(String username);
	public List<User> getUserBySysCode(String sysCode);
	/**
	 * 更新用户密码
	 * @param userId		用户ID
	 * @param password		密码原文
	 */
	public void updatePassword(String userId,String password);
	
	/**
	 * 更新所有用户密码
	 * @param password		密码原文
	 */
	public void updateAllPassword(String password);

	/**
	 * 修改登录参数(登录时间与登录次数)
	 * @param user
	 */
	public void updateLoginInfo(User user);
}
