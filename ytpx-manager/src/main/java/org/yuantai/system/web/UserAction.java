package org.yuantai.system.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yuantai.common.JsonResult;
import org.yuantai.common.SystemConfig;
import org.yuantai.common.TreeNode;
import org.yuantai.school.pojo.School;
import org.yuantai.school.service.SchoolService;
import org.yuantai.system.pojo.OnlineUser;
import org.yuantai.system.pojo.Organ;
import org.yuantai.system.pojo.Role;
import org.yuantai.system.pojo.User;
import org.yuantai.system.service.OnlineUserService;
import org.yuantai.system.service.OrganService;
import org.yuantai.system.service.RoleService;
import org.yuantai.system.service.UserService;

/**
 * 用户管理
 * @author zhangle
 */
@Controller
@RequestMapping(value="/system/user")
public class UserAction extends ModelAction<User>{
	
	private static final Logger logger = LogManager.getLogger(UserAction.class);
	
    @Autowired private UserService userService;
    @Autowired private RoleService roleService;
    @Autowired private OrganService organService;
	@Autowired private OnlineUserService onlineUserService;
	@Autowired private SchoolService schoolService;
    /**
     * 跳到用户列表页面
     */
    @RequestMapping(method=RequestMethod.GET)
	public String list() {
		return "system/user/user-list";
	}
    
    @RequestMapping(value="/query",method=RequestMethod.GET)
    public String query() {
    	return "system/user/user-query";
    }
    
    /**
	 * 查询用户
	 */
    @RequestMapping(value="/query",method=RequestMethod.POST)
	public String query(ServletRequest request, ServletResponse response,User param) {
		DetachedCriteria criteria=getPaginator().getCriteria();
		HttpServletRequest req=(HttpServletRequest)request;
		if(!StringUtils.isEmpty(param.getUsername())) {
			criteria.add(Restrictions.like("username",param.getUsername(),MatchMode.ANYWHERE));
		}
		if(!StringUtils.isEmpty(param.getNickname())) {
			criteria.add(Restrictions.like("nickname", param.getNickname(),MatchMode.ANYWHERE));
		}
		if(param.getStatus()!=null) {
			criteria.add(Restrictions.eq("status", param.getStatus()));
		}
		OnlineUser currentUser =  onlineUserService.getLoginUser(req.getSession());
		if(currentUser.getSysCode()!=null&&currentUser.getSysCode()!=""){
			criteria.add(Restrictions.eq("sysCode", currentUser.getSysCode()));
		}
		criteria.addOrder(Order.desc("createTime"));
		userService.find(getPaginator());
		List<User> users=  getPaginator().getDataList();
		for(User user:users){
			if(user.getSysCode()!=null){
				School school= schoolService.getSchoolBySysCode(user.getSysCode());
			if(school!=null){
				String schoolName=	school.getSchoolName();
				user.setSysCode(schoolName);
			}
			}				
		}
		return pagin(getPaginator());
	}
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(ServletRequest request, ServletResponse response) {
		HttpServletRequest req=(HttpServletRequest)request;
		OnlineUser currentUser =  onlineUserService.getLoginUser(req.getSession());
		List<School> school = null;
		if(currentUser.getSysCode()!=null){
			 school= schoolService.checkBySysCode(currentUser.getSysCode());
		}else {
			 school= schoolService.findAll();
		}
	
		attr("school", school);
		return "system/user/user-add";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doAdd(@RequestBody User user) {
		user.setCreateUserId(loginuser().getId());
		if(loginuser().getSysCode()!=null&&loginuser().getSysCode()!=""){
			user.setSysCode(loginuser().getSysCode());
		}	
		userService.add(user);
		return result(200, "OK");
	}
	
	/**
	 * 检查用户是否存在
	 * @param username
	 * @return code:200 表示不存在,code:-1 表示已存在
	 */
	@RequestMapping(value="/check_user",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult checkUser(String username) {
		boolean exist=userService.getUserByName(username)!=null;
		if(exist) {
			return result(-1, "already exists");
		} else {
			return result(200, "not exist");
		}
	}
	
	@RequestMapping(value="/update/{id}",method=RequestMethod.GET)
	public String update(@PathVariable String id,ServletRequest request, ServletResponse response) {
		HttpServletRequest req=(HttpServletRequest)request;
		OnlineUser currentUser =  onlineUserService.getLoginUser(req.getSession());
		User user=userService.get(id);
		attr("user", user);
		List<School> schools=null;
		if(currentUser.getSysCode()!=null){
			schools= schoolService.checkBySysCode(currentUser.getSysCode());
		}else {
			schools= schoolService.findAll();
		}
		attr("schools", schools);
		if(user.getSysCode()!=null){
			School school= schoolService.getSchoolBySysCode(user.getSysCode());
			attr("school", school);
		}		
		return "system/user/user-update";
	}
	
	@RequestMapping(value="/update/{id}",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doUpdate(@PathVariable String id,@RequestBody User param) {
		
		User user=userService.get(id);
		user.setNickname(param.getNickname());
		user.setStatus(param.getStatus());
		user.setMobile(param.getMobile());
		user.setRemark(param.getRemark());
		String publicRoleId=roleService.getRoleByName(SystemConfig.getProperty("system.role.public")).getId();
		ArrayList<String> list = new ArrayList<String>();
		list.add(publicRoleId);
		if(param.getCheckedRoleId()!=null){
		String [] roleIds=param.getCheckedRoleId();
		if(roleIds.length>0){
		for(String roleid:roleIds){
			if(!roleid.equals(publicRoleId)){//去重
				list.add(roleid);
			}
		}}
		user.setCheckedRoleId((String[])list.toArray(new String[list.size()]));
		}
		user.setSysCode(param.getSysCode());
		userService.update(user);
		return result(200, "OK");
	}
	
	@RequestMapping(value="/delete/{id}",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doDelete(@PathVariable String id) {
		userService.delete(id);
		return result(200, "OK");
	}
    
    /**
	 * 为用户分配角色时,返回角色的树形组件的json对象
	 */
	@RequestMapping(value="/role_tree",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
    public List<TreeNode> roleTree(@RequestParam(value="userId",required=false) String userId) {
    	
		List<Role> roles=(List<Role>) roleService.findAll();
    	List<TreeNode> tree=new ArrayList<TreeNode>(roles.size());
		
    	List<Role> selectedRoles=Collections.EMPTY_LIST;
    	if(!StringUtils.isBlank(userId)) {
    		selectedRoles=roleService.findRoleByUserId(userId,null);
    	}
    	
    	String guestRoleName=SystemConfig.getProperty("system.role.guest");
    	String publicRoleName=SystemConfig.getProperty("system.role.public");
    	String devRoleName=SystemConfig.getProperty("system.role.developer");
    	String schoolManagerRoleName=SystemConfig.getProperty("system.role.school.manager");
		for(Role r:roles) {
			if(r.getName().equals(guestRoleName)) continue;	//guest角色不能被分配
			if(r.getName().equals(devRoleName)) {				//只有拥有developer角色的用户才能分配developer角色给别人
				if(!loginuser().hasRole(devRoleName)) continue;
			}
			if(loginuser().hasRoles(schoolManagerRoleName)) {				//只有拥有学校管理员角色的用户可以分配跟学校相关的角色给学校用户	
				if(r.getType()!=null&&r.getType()!=""){
				if(r.getType().startsWith("school_")){
					TreeNode node=new TreeNode();
					node.setId(r.getId());
					node.setText(r.getName());
					node.setChecked(selectedRoles.contains(r));
					node.setIconCls("icon-star");
					tree.add(node);
				}}			
			}else {
				if(!r.getName().equals(publicRoleName)){
					TreeNode node=new TreeNode();
					node.setId(r.getId());
					node.setText(r.getName());
					node.setChecked(selectedRoles.contains(r));
					node.setIconCls("icon-star");
					tree.add(node);
				}
			
			}		
		}
		return tree;
    }
	
	@RequestMapping(value="/password/{id}",method=RequestMethod.GET)
	public String password(@PathVariable String id) {
		User user=userService.get(id);
		attr("user", user);
		return "system/user/user-password";
	}
	
	@RequestMapping(value="/password/{id}",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doPassword(@PathVariable String id,@RequestParam String password) {
		try {
			userService.updatePassword(id, password);
		} catch(Exception e) {
			return result(-1, e.getMessage());
		}
		return result(200, "OK");
	}
    
	@RequestMapping(value="/login/{id}",method=RequestMethod.GET)
	public String login(@PathVariable String id) {
		User user=userService.get(id);
		loginSuccess(user);
		return redirect("/admin");
	}
	
	/**
	 * 登录成功
	 * @param user
	 */
	private void loginSuccess(User user) {
		
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
				Organ dept=organService.getMainDepartment(user.getId());
				List<Organ> parents=organService.getParents(emp.getId());
				onlineUser.setEmployee(emp);
				onlineUser.setEmployees(emps);
				onlineUser.setDepartment(dept);
				onlineUser.setParents(parents);
			}
			onlineUserService.add(onlineUser,session());
		} catch (Exception e) {
			logger.error("登录出现错误!",e);
			throw new RuntimeException(e);
		}
	}
}
