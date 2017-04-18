package org.yuantai.system.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.yuantai.common.Constants;
import org.yuantai.system.aop.Logging;
import org.yuantai.system.pojo.OnlineUser;
import org.yuantai.system.service.OnlineUserService;

/**
 * 在线用户服务实现类
 * @author zhangle
 */
@Logging
@Service
public class OnlineUserServiceImpl implements OnlineUserService {

	private HashMap<String, OnlineUser> onlineUserMap=new HashMap<String, OnlineUser>(200);
	
	@Override
	public void add(OnlineUser onlineUser,HttpSession session) {
		session.removeAttribute(Constants.LOGIN_USER_SESSION_KEY);
		onlineUserMap.put(session.getId(), onlineUser);
		session.setAttribute(Constants.LOGIN_USER_SESSION_KEY, onlineUser);
	}

	@Override
	public void delete(String sessionId) {
		if(onlineUserMap.containsKey(sessionId)) {
			onlineUserMap.remove(sessionId);
		}
	}

	@Override
	public OnlineUser get(String sessionId) {
		return onlineUserMap.get(sessionId);
	}

	@Override
	public List<OnlineUser> getOnlineUsers() {
		Collection<OnlineUser> users=onlineUserMap.values();
		List<OnlineUser> onlineUserList=new ArrayList<OnlineUser>(users);
		Collections.sort(onlineUserList);
		return onlineUserList;
	}

	@Override
	public OnlineUser getLoginUser(HttpSession session) {
		try {
			return get(session.getId());
		} catch (Exception e) {
			return null;
		}
	}
}
