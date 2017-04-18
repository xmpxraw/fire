package org.yuantai.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yuantai.common.SystemConfig;
import org.yuantai.system.aop.Logging;
import org.yuantai.system.auth.AuthHandler;
import org.yuantai.system.pojo.Menu;
import org.yuantai.system.pojo.OnlineUser;
import org.yuantai.system.service.AuthService;

/**
 * 权限验证服务实现类
 * @author zhangle
 */
@Service
@Transactional(readOnly=true)
public class AuthServiceImpl implements AuthService,ApplicationListener<ContextRefreshedEvent> {

	private static final Logger logger = LogManager.getLogger(AuthServiceImpl.class);
	
	@Resource(name="authHandlerChain")
	private AuthHandler authHandler;
	
	@Override
	public boolean checkUrl(OnlineUser loginUser, String url) {
		return authHandler.handleUrl(loginUser, url);
	}

	@Override
	public boolean checkCode(OnlineUser loginUser, String functionCode) {
		return authHandler.handleCode(loginUser, functionCode);
	}

	@Override
	public List<Menu> checkMenu(OnlineUser loginUser, String parentId) {
		return authHandler.handleMenu(loginUser, parentId);
	}

	@Override
	@Logging
	@Transactional(readOnly=false)
	public void refreshCache() {
		logger.info("正在刷新权限缓存,请稍等...");
		authHandler.refreshCache();
	}

	/**
	 * 当Spring的ApplicationContext初始化或刷新时发送的事件
	 * 当spring加载完毕后,刷新菜单缓存
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		//系统启动时,是否刷新与权限有关的缓存
		String refreshCache=SystemConfig.getProperty("system.cache.refresh_on_startup");
		if("true".equalsIgnoreCase(refreshCache)) {
			refreshCache();
		}
	}
}
