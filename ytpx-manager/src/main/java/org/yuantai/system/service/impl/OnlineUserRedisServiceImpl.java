package org.yuantai.system.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yuantai.common.cache.RedisSessionUtil;
import org.yuantai.system.aop.Logging;
import org.yuantai.system.pojo.OnlineUser;
import org.yuantai.system.service.OnlineUserService;

/**
 * 在线用户服务实现类
 * @author zhangle
 */
@Logging
//@Service
public class OnlineUserRedisServiceImpl implements OnlineUserService {

	private static final String ONLINEUSER_MAP_KEY = "online_users";
	@Autowired RedisSessionUtil redisSessionUtil;
	
	@Override
	public void add(OnlineUser onlineUser,HttpSession session) {
		redisSessionUtil.hash(ONLINEUSER_MAP_KEY, onlineUser.getSessionId(),onlineUser);
	}

	@Override
	public void delete(String sessionId) {
		redisSessionUtil.hashdel(ONLINEUSER_MAP_KEY, sessionId);
	}

	@Override
	public OnlineUser get(String sessionId) {
		OnlineUser onlineUser=(OnlineUser) redisSessionUtil.hash(ONLINEUSER_MAP_KEY, sessionId);
		return onlineUser;
	}

	@Override
	public List<OnlineUser> getOnlineUsers() {
		Set<String> keys=redisSessionUtil.hashkeys(ONLINEUSER_MAP_KEY);
		List<OnlineUser> onlineUserList=new ArrayList<OnlineUser>(keys.size());
		
		for(Object key:keys) {
			String cachekey=(String)key;
			if(redisSessionUtil.hasKey(cachekey)) {
				OnlineUser user=get(cachekey);
				if(user==null) {
					redisSessionUtil.hashdel(ONLINEUSER_MAP_KEY, cachekey);
				} else {
					onlineUserList.add(user);
				}
			} else {
				redisSessionUtil.hashdel(ONLINEUSER_MAP_KEY, cachekey);
			}
		}
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
