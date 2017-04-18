package org.yuantai.system.dao;

import java.util.List;

import org.yuantai.system.pojo.User;

/**
 * 用户数据访问接口
 * @author zhangle
 */
public interface UserDao extends BaseDao<User>{
	
	/**
	 * 根据用户名查找用户对象
	 * @param name
	 * @return
	 */
	public User getUserByName(String username);
	
	public List<User> getUserBySysCode(String sysCode);
	
	/**
	 * 更新用户密码
	 * @param userId
	 * @param password
	 */
	public void updatePassword(String userId,String password);
	
	/**
	 * 更新所有用户密码
	 * @param password
	 */
	public void updateAllPassword(String password);
	
	/**
	 * 修改登录参数(登录时间与登录次数)
	 * @param user
	 */
	public void updateLoginInfo(User user);
}
