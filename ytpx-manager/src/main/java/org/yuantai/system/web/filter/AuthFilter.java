package org.yuantai.system.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.yuantai.common.Constants;
import org.yuantai.common.spring.SpringUtil;
import org.yuantai.common.util.SystemUtil;
import org.yuantai.school.pojo.School;
import org.yuantai.school.service.SchoolService;
import org.yuantai.system.pojo.OnlineUser;
import org.yuantai.system.service.AuthService;
import org.yuantai.system.service.OnlineUserService;

/**
 * 权限验证过滤器
 * @author zhangle
 */
public class AuthFilter implements Filter {
	
	private static final Logger logger = LogManager.getLogger(AuthFilter.class);
	
	private AuthService authService;
	private OnlineUserService onlineUserService;
	private Pattern[] excludeUrlPattern;
	private Map<String,String> excludeUrlCache;
	private SchoolService schoolService;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		excludeUrlPattern=getExcludeUrlPattern(filterConfig);
		excludeUrlCache=new HashMap<String,String>(200);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req=(HttpServletRequest)request;
		HttpServletResponse res=(HttpServletResponse)response;
		res.setHeader("Access-Control-Allow-Origin", "*");
		res.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Range");
	/*	res.setHeader("Access-Control-Allow-Methods", "*");*/
		String hostName =  req.getHeader("Host");
		Properties props = SystemUtil.getSpringAppProperties();
		String host= props.getProperty("system.host.domain");	//获取配置的域名
		String ip= props.getProperty("system.host.ip");	//获取配置的ip
		String str=null;
		String domian= host.substring(4, host.length());
	//	if(!hostName.contains("host")){
		if(!hostName.contains(host)&&!hostName.contains(ip)&&!hostName.contains("localhost")&&!hostName.contains("127.0.0.1")){
			if(!hostName.startsWith("www")){
			//	String preDomian= hostName.substring(4, hostName.indexOf(domian)-1);//前缀
				String preDomian= hostName.substring(0, hostName.indexOf(domian)-1);//前缀
				logger.debug("===域名前缀===="+preDomian);
				School school= getSchoolService().getSchoolCode(preDomian);
					if(school!=null){
						req.getSession().setAttribute("domain", preDomian);
						if(school.getLogo()!=null){
							String logoUrl=req.getHeader("Host")+"/fileUpload/"+school.getSchoolCode()+school.getLogo().substring(school.getLogo().lastIndexOf("."));
							req.getSession().setAttribute("logoUrl", logoUrl);
					//		logger.debug("===logoUrl===="+logoUrl);
						}		
					}	
			}
			
		}		
		//获取请求的URL
		String requestUrl=getRequestUrl(req);
		
		//如果是排除验证的URL,那么直接通过,无需权限验证
		if(isExcludedUrl(requestUrl)) {
			chain.doFilter(request, response);
			return;
		}
		
		logger.debug("auth:"+requestUrl);
		OnlineUser loginUser=getOnlineUserService().getLoginUser(req.getSession());
		if(loginUser==null) {
			//如果未登录,那么检查是否未登录也可访问.不可访问则跳到登录页,否则通过
			if(!getAuthService().checkUrl(null, requestUrl)) {
				gotoLogin(req,res,"用户超时或未登录,请先登录!");
			} else {
				chain.doFilter(request, response);
			}
			return;
		}
		
		//此处代表用户已登录,如果有权访问,那么通过;否则跳到拒绝访问页面
		if(getAuthService().checkUrl(loginUser, requestUrl)) {
			chain.doFilter(request, response);
		} else {
			accessDenied(req,res,loginUser);
		}
	}

	@Override
	public void destroy() {}
	
	public AuthService getAuthService() {
		if(authService==null) {
			authService=SpringUtil.getBean(AuthService.class);
		}
		return authService;
	}
	
	public SchoolService getSchoolService() {
		if(schoolService==null) {
			schoolService=SpringUtil.getBean(SchoolService.class);
		}
		return schoolService;
	}

	public OnlineUserService getOnlineUserService() {
		if(onlineUserService==null) {
			onlineUserService=SpringUtil.getBean(OnlineUserService.class);
		}
		return onlineUserService;
	}

	/**
	 * 获取排除验证的url
	 */
	private Pattern[] getExcludeUrlPattern(FilterConfig filterConfig) {
		Pattern[] excludeUrlPattern=null;
		String excludeUrl=filterConfig.getInitParameter("exclude-url");
		if(StringUtils.isBlank(excludeUrl)) {
			excludeUrlPattern=new Pattern[0];
		} else {
			
			String[] url=excludeUrl.split(",|\\s+");
			List<Pattern> pl=new ArrayList<Pattern>();
			for(int i=0;i<url.length;i++) {
				if(StringUtils.isBlank(url[i])) continue;
				pl.add(Pattern.compile(url[i]));
			}
			excludeUrlPattern=pl.toArray(new Pattern[pl.size()]);
		}
		return excludeUrlPattern;
	}
	
	/**
	 * 获取请求的URL路径
	 */
	private String getRequestUrl(HttpServletRequest request) {
		int contextPathLength=request.getContextPath().length();
		String requestUrl=request.getRequestURI().substring(contextPathLength);
		/*
		//验证权限的url是否需要加入querystring参数,如果需要请去掉此注释.本系统基于rest风格,推荐不加入,提升性能
		String queryString=request.getQueryString();
		if(queryString==null) return requestUrl;
		queryString=queryString.replaceFirst("_=\\d+", "");	//去掉easyui自动加入的防缓存参数
		if(queryString.length()>0) {		//加入querystring
			requestUrl+="?"+request.getQueryString();
		}*/
		return requestUrl;
	}
	
	/**
	 * 检查请求路径是否不需要验证权限
	 */
	private boolean isExcludedUrl(String path) {
		if(excludeUrlCache.containsKey(path)) return true;
		
		for(Pattern p:excludeUrlPattern) {
			if(p.matcher(path).matches()) {
				excludeUrlCache.put(path, "OK");
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 跳到登录页面
	 */
	private void gotoLogin(HttpServletRequest request,HttpServletResponse response,String systemMessage) {
		try {
			logger.debug("用户未登录！");
			response.sendRedirect(request.getContextPath()+Constants.LOGIN_PAGE);
		} catch (IOException e) {}
	}
	
	/**
	 * 拒绝访问处理
	 */
	private void accessDenied(HttpServletRequest request,HttpServletResponse response,OnlineUser loginUser) throws IOException, ServletException {
		logger.warn("权限不够,拒绝访问!	 user:"+loginUser.getUsername()+" url:"+getRequestUrl(request));
	//	response.sendRedirect(request.getContextPath()+Constants.ACCESS_DENIED_PAGE);
		request.getRequestDispatcher(Constants.ACCESS_DENIED_PAGE).forward(request, response);
	}
}
