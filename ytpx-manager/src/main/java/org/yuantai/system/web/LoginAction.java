package org.yuantai.system.web;

import java.sql.Timestamp;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yuantai.common.JsonResult;
import org.yuantai.common.util.Md5Util;
import org.yuantai.common.util.StringUtil;
import org.yuantai.system.pojo.OnlineUser;
import org.yuantai.system.pojo.Organ;
import org.yuantai.system.pojo.Role;
import org.yuantai.system.pojo.User;
import org.yuantai.system.service.OnlineUserService;
import org.yuantai.system.service.OrganService;
import org.yuantai.system.service.RoleService;
import org.yuantai.system.service.UserService;

/**
 * 登录管理
 * @author zhangle
 */
@Controller
public class LoginAction extends BaseAction {

	private static final Logger logger = LogManager.getLogger(LoginAction.class);
	
	@Autowired private UserService userService;
	@Autowired private RoleService roleService;
	@Autowired private OrganService organService;
	@Autowired private OnlineUserService onlineUserService;
	
	private static String DEFAULT_LOGIN_PAGE="system/login/login-5";
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String execute() {
		return execute(null);
	}
	
	@RequestMapping(value="/login/{style}",method=RequestMethod.GET)
	public String execute(@PathVariable String style) {
		
		if(StringUtil.isEmpty(style)) {
			return DEFAULT_LOGIN_PAGE;
		} else {
			return "system/login/login-"+style;
		}
	}

	/**
	 * 登录操作
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult login(@RequestParam(value="username",required=true) String username,
						@RequestParam(value="password",required=true) String password) {
		
		//根据用户名获取用户对象,如果不能获取用户对象,那么返回登录页面
		User user=userService.getUserByName(username);
		if(user==null) {
			return result(-1, "用户名或密码错误,登录失败!");
		}
		
		//检查输入的密码是否正确,如果不正确,那么返回登录页面
		String _pwd=Md5Util.makePassword(password, user.getSalt());
		if (!user.getPassword().equals(_pwd)) {
			return result(-1, "用户名或密码错误,登录失败!");
		}
		
		if(user.getStatus()==User.STATUS_LOCKED) {
			return result(-1, "用户被锁定,不能登录,请联系系统管理员!");
		}
		
		//登录成功操作
		loginSuccess(user);
		return result(200,request().getContextPath()+"/admin");
	}
	
	/**
	 * 登录成功
	 * @param user
	 */
	private void loginSuccess(User user) {
		//更新用户登录时间与次数
		user.setLoginNum(user.getLoginNum()+1);
		user.setLoginTime(new Timestamp(System.currentTimeMillis()));
		userService.updateLoginInfo(user);
		
		//清空密码,不保存在内存中
		user.setPassword(null);
		user.setSalt(null);
		
		try {
			//创建在线用户对象
			OnlineUser onlineUser=new OnlineUser(request(),user);
			
			//缓存用户关联的角色
			List<Role> roles=roleService.findRoleByUserId(user.getId(),Role.STATUS_DEFAULT);
			onlineUser.setRoles(roles);
			
			//缓存用户在组织机构中的关系
			Organ emp=organService.getMainEmployee(user.getId());
			if(emp!=null) {
				List<Organ> emps=organService.getOrgansByUserId(user.getId());
				Organ team=organService.getTeam(emp.getId());
				Organ dept=organService.getMainDepartment(user.getId());
				List<Organ> parents=organService.getParents(emp.getId());
				onlineUser.setEmployee(emp);
				onlineUser.setEmployees(emps);
				onlineUser.setTeam(team);
				onlineUser.setDepartment(dept);
				onlineUser.setParents(parents);
			}
			onlineUserService.add(onlineUser,session());
		} catch (Exception e) {
			logger.error("登录出现错误!",e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 注销用户
	 * @return
	 */
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String logout() {
		session().invalidate();
		onlineUserService.delete(session().getId());
		return redirect("/login");
	}
}
