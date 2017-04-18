package org.yuantai.system.web.tag;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;
import org.yuantai.common.spring.SpringUtil;
import org.yuantai.system.pojo.OnlineUser;
import org.yuantai.system.pojo.Organ;
import org.yuantai.system.service.AuthService;
import org.yuantai.system.service.OnlineUserService;

/**
 * 权限验证自定义标签
 * @author zhangle
 */
public class AuthTag extends TagSupport {

	private String code;
	private String url;
	private String username;
	private String role;
	private String organ;
	
	private AuthService authService;
	private OnlineUserService onlineUserService;
	
	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);
		authService=SpringUtil.getBean(AuthService.class);
		onlineUserService=SpringUtil.getBean(OnlineUserService.class);
	}
	
	@Override
	public int doStartTag() throws JspException {
		super.doStartTag();

		HttpServletRequest request=(HttpServletRequest) pageContext.getRequest();
		
		//取出登录用户
		OnlineUser loginUser=onlineUserService.getLoginUser(request.getSession());
		
		//如果用户能访问指定功能code,那么权限通过
		if(!StringUtils.isBlank(code)) {
			if(authService.checkCode(loginUser, code)) {
				return EVAL_BODY_INCLUDE;
			}
			return SKIP_BODY;
		}
		
		//如果用户能访问指定url,那么权限通过
		if(!StringUtils.isBlank(url)) {
			if(authService.checkUrl(loginUser, url)) {
				return EVAL_BODY_INCLUDE;
			}
			return SKIP_BODY;
		}
		
		//如果当前用户是指定用户,那么权限通过
		if(!StringUtils.isBlank(username)) {
			if(loginUser!=null && loginUser.getUsername().equals(username)) {
				return EVAL_BODY_INCLUDE;
			}
			return SKIP_BODY;
		}
		
		//如果用户拥有指定角色,那么权限通过
		if(!StringUtils.isBlank(role)) {
			if(loginUser!=null && loginUser.hasRole(role)) {
				return EVAL_BODY_INCLUDE;
			}
			return SKIP_BODY;
		}
		
		//如果用户是指定组织机构中的人员,那么权限通过
		if(!StringUtils.isBlank(organ)) {
			if(loginUser==null) {
				return SKIP_BODY;
			}
			List<Organ> parents=loginUser.getParents();
			for(Organ o:parents) {
				if(o.getName().equals(organ)) {
					return EVAL_BODY_INCLUDE;
				}
			}
			return SKIP_BODY;
		}
		return SKIP_BODY;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getOrgan() {
		return organ;
	}

	public void setOrgan(String organ) {
		this.organ = organ;
	}
	
}
