/**
 * Copyright (c) 2016, YYD All Rights Reserved.
 */
package org.yuantai.weixin.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.yuantai.weixin.service.WechatAuthService;

/**
 * 
 * description: 这个拦截器所做检查如下：<br/>
 * <ul>
 * <li>检查当前的SESSION中是否包含openId信息，如果包含直接返回true</li>
 * <li>如果不包含，则判断当前是否是微信浏览器，如果不是，跳转到一个微信提示页面</li>
 * <li>如果是微信浏览器，则组装微信跳转链接，并进行redirect</li>
 * </ul>
 * date: 2016年1月21日 上午10:19:21 <br/>
 *
 * @author jack
 */
public class WechatAuthInterceptor extends HandlerInterceptorAdapter {
	private static Logger log = LoggerFactory.getLogger(WechatAuthInterceptor.class);

	@Autowired private WechatAuthService wechatService;

	/** 非微信跳转地址 */
	private String wydNonWechatUrl = "/school/weixin/auth";
	private String servletContext = "";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (log.isDebugEnabled()) {
			HttpSession session = request.getSession(false);
			if (session != null) {
				log.debug("[WechatAuthInteceptor] [SISSIONID:" + session.getId() + "]");
			} else {
				log.debug("[WechatAuthInteceptor] [SISSIONID:null]");
			}
		}
		String contextPath=request.getContextPath();
		String uri = request.getRequestURI();
		if (uri.endsWith("/wechat/auth")) {
			// 对于返回的验证处理不执行此拦截器
			return true;
		}

		// 检查是否已经通过了微信的授权
	//	String openId = (String) RequestUtils.getSessionAttr(request, WechatWebConstants.WECHAT_OPENID);
		String openId=(String) request.getSession().getAttribute("WECHAT_OPENID");
		if (openId != null && !"".equals(openId)) {
			return true;
		}

		// 检查是否是微信浏览器
		String userAgent = request.getHeader("User-Agent");
		if (!wechatService.checkWechatBrowser(userAgent)) {
			// 不是微信浏览器，跳转到一个说明页面
			response.sendRedirect(contextPath+wydNonWechatUrl);
			return false;
		}

		// 跳转到微信网页授权页面
//		String currUrl = RequestUtils.getRequestURIWithParam(request);
		String currUrl=request.getRequestURI();
		if (!"".equals(servletContext) && currUrl.startsWith(servletContext)) {
			currUrl = currUrl.substring(servletContext.length());
			log.debug("当前需要保存的地址是:[{}]", currUrl);
		}
	//	response.sendRedirect(wechatService.makeAuthRedirectUrl(request, currUrl));
		return false;
	}

	public void setWydNonWechatUrl(String wydNonWechatUrl) {
		this.wydNonWechatUrl = wydNonWechatUrl;
	}

	public void setServletContext(String servletContext) {
		this.servletContext = servletContext;
	}
}
