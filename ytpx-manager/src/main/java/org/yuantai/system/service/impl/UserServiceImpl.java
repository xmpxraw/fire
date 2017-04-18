package org.yuantai.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yuantai.system.aop.Logging;
import org.yuantai.system.dao.UserDao;
import org.yuantai.system.pojo.User;
import org.yuantai.system.service.UserService;

/**
 * 用户服务实现类
 * @author zhangle
 */
@Logging
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

	@Autowired private UserDao userDao;
	
	@Override
	public User getUserByName(String username) {
		return userDao.getUserByName(username);
	}
	
	@Override
	public List<User> getUserBySysCode(String sysCode) {
		return userDao.getUserBySysCode(sysCode);
	}

	@Override
	public void updatePassword(String userId, String password) {
		userDao.updatePassword(userId, password);
	}

	@Override
	public void updateAllPassword(String password) {
		userDao.updateAllPassword(password);
	}

	@Override
	public void updateLoginInfo(User user) {
		userDao.updateLoginInfo(user);
	}
}
