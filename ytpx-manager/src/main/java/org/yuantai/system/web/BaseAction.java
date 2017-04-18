package org.yuantai.system.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.yuantai.common.JsonResult;
import org.yuantai.common.Paginator;
import org.yuantai.common.spring.view.PaginView;
import org.yuantai.common.spring.view.XmlView;
import org.yuantai.common.util.JsonUtil;
import org.yuantai.common.util.StringUtil;
import org.yuantai.system.pojo.OnlineUser;
import org.yuantai.system.service.OnlineUserService;

/**
 * Action基础类,系统里所有Action都必须继承该Action
 * @author zhangle
 */
public abstract class BaseAction {
	private static final Logger logger = LogManager.getLogger(BaseAction.class);
	
	@Autowired private OnlineUserService onlineUserService;
	
	protected String forward(String path) {
		return "forward:"+path;
	}
	
	protected String redirect(String path) {
		return "redirect:"+path;
	}
	
	public HttpServletRequest request() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}
	
	public HttpServletResponse response() {
		HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
		/*response.setHeader("Access-Control-Allow-Origin", "*");*/
		return response;
	}
	
	protected HttpSession session() {
		return request().getSession();
	}
	
	protected ServletContext servletContext() {
		return session().getServletContext();
	}
	
	/**
	 * 获取真实路径
	 */
	protected String realpath(String path) {
		return servletContext().getRealPath(path);
	}
	
	/**
	 * 获取请求参数
	 */
	protected String param(String name) {
		return request().getParameter(name);
	}
	
	/**
	 * 获取请求参数数组
	 */
	protected String[] params(String name) {
		return request().getParameterValues(name);
	}
	
	/**
	 * 获取int类型请求参数
	 */
	protected int intparam(String name) {
		return Integer.parseInt(param(name));
	}

	/**
	 * 获取int类型请求参数,当出错时,返回defaultValue
	 */
	protected int intparam(String name,int defaultValue) {
		try {
			return Integer.parseInt(param(name));
		} catch(Exception e) {
			return defaultValue;
		}
	}
	
	/**
	 * 设置attribute到request中
	 */
	protected void attr(String name,Object value) {
		request().setAttribute(name, value);
	}
	
	/**
	 * 从request中获取attribute
	 */
	protected Object attr(String name) {
		return request().getAttribute(name);
	}
	
	/**
	 * 设置attribute到session中
	 */
	protected void sattr(String name,Object value) {
		session().setAttribute(name, value);
	}
	
	/**
	 * 从session中获取attribute
	 */
	public Object sattr(String name) {
		return session().getAttribute(name);
	}
	
	/**
	 * 设置attribute到application中
	 */
	protected void appattr(String name,Object value) {
		servletContext().setAttribute(name, value);
	}
	
	/**
	 * 从application中获取attribute
	 */
	protected Object appattr(String name) {
		return servletContext().getAttribute(name);
	}
	
	/**
	 * 获取登录用户
	 */
	protected OnlineUser loginuser() {
		return onlineUserService.getLoginUser(request().getSession());
	}
	
	public String uid() {
		OnlineUser user=loginuser();
		return user==null?null:user.getId();
	}
	
	/**
	 * 获取ip
	 * @param request
	 * @return
	 */
	public static String clientip(HttpServletRequest request) {
		
		if (request == null) return "";
		String ip =  request.getRemoteAddr();
		if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
		}
		if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		
		return ip;
	}
	
	/**
	 * 打印json字符到response
	 */
	protected void printjson(HttpServletResponse response,Object obj) {
		try {
			response.setContentType("text/json; charset=utf-8");
			response.setCharacterEncoding("utf-8");
			response.setHeader("Pragma", "No-cache");
			/*response.setHeader("Access-Control-Allow-Origin", "*");*/
			response.setHeader("Cache-Control", "no-cache,no-store,max-age=0");
			response.setDateHeader("Expires",-1);
			
			if(obj instanceof String) {
				response.getWriter().print((String)obj);
			} else if(obj instanceof Paginator) {
				Paginator pagin=(Paginator<?>)obj;
				Map<String, Object> jsonMap=new HashMap<String, Object>();
				jsonMap.put("total", pagin.getTotalCount());
				jsonMap.put("rows", pagin.getDataList());
				response.getWriter().print(JsonUtil.toJson(jsonMap));
			} else {
				response.getWriter().print(JsonUtil.toJson(obj));
			}
		} catch (Exception e) {
			throw new RuntimeException("输出json数据出错!",e);
		}
	}
	
	/**
	 * 返回分页数据
	 * @param state
	 * @param message
	 * @param data
	 * @return
	 */
	public String pagin(Paginator<?> pagin) {
		request().setAttribute(PaginView.PARAMETERS_KEY, pagin);
		return PaginView.VIEW_NAME;
	}
	
	/**
	 * 获取JsonResult对象
	 */
	public JsonResult result(int code,String msg) {
		JsonResult result=new JsonResult(code,msg);
		return result;
	}
	
	/**
	 * 获取JsonResult对象
	 */
	protected JsonResult result(int code,String msg,Object data) {
		JsonResult result=new JsonResult(code,msg,data);
		return result;
	}
	
	/**
	 * 返回xml
	 * @param xml
	 * @return
	 */
	public String xml(String xml) {
		request().setAttribute(XmlView.PARAMETERS_KEY, xml);
		return XmlView.VIEW_NAME;
	}
	
	/**
	 * 错误处理
	 */
	@ExceptionHandler(Exception.class)
	protected void handleException(HttpServletRequest request,HttpServletResponse response,Exception e) {
		try {
			logger.error(e.getMessage(),e);
			JsonResult result=result(-1,e.getMessage());
			printErrorMessage(request,response,result);
		} catch (Exception e1) {}
	}
	
	/**
	 * 输出错误消息
	 * @param request
	 * @param response
	 * @param result
	 */
	private void printErrorMessage(HttpServletRequest request,HttpServletResponse response,JsonResult result) {
		
		response.setContentType("text/json; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache,no-store,max-age=0");
		response.setDateHeader("Expires",-1);
		
		try {
			String callback=request.getParameter("callback");
			if(StringUtil.isEmpty(callback)) {
				response.getWriter().print(result.toJson());
				response.getWriter().flush();
			} else {		//否则以jsonp格式输出消息
				response.getWriter().print(callback+"("+result.toJson()+")");
				response.getWriter().flush();
			}
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}
	}
}
