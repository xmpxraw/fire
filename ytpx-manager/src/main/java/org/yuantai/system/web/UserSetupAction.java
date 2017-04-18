package org.yuantai.system.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yuantai.common.JsonResult;
import org.yuantai.common.Paginator;
import org.yuantai.common.util.Md5Util;
import org.yuantai.system.pojo.OnlineUser;
import org.yuantai.system.pojo.Organ;
import org.yuantai.system.pojo.User;
import org.yuantai.system.service.OrganService;
import org.yuantai.system.service.UserService;

/**
 * 用户管理
 * @author zhangle
 */
@Controller
@RequestMapping(value="/system/user_setup")
public class UserSetupAction extends BaseAction {
	
	@Autowired private UserService userService;
	@Autowired private OrganService organService;
    
    /**
     * 在线用户列表页
     * @return
     */
	@RequestMapping(method=RequestMethod.GET)
    public String setup() {
		attr("loginUser", loginuser());
    	return "system/user/user-setup";
    }
    
	@RequestMapping(value="/basic",method=RequestMethod.POST)
	@ResponseBody
    public JsonResult setupUser(User param) {
		
		User loginUser=loginuser();
		User user=userService.get(loginUser.getId());	//从数据库查询出最新的用户对象
		user.setNickname(param.getNickname());
		user.setMobile(param.getMobile());
		user.setRemark(param.getRemark());
		userService.update(user);						//修改用户信息
		
		loginUser.setNickname(param.getNickname());		//刷新"登录用户对象"
		loginUser.setRemark(param.getRemark());
		loginUser.setMobile(param.getMobile());
		return result(200, "OK");
	}
	
	@RequestMapping(value="/password",method=RequestMethod.POST)
	@ResponseBody
    public JsonResult setupPassword(String oldpwd,String newpwd) {
		
		User loginUser=loginuser();
		User user=userService.get(loginUser.getId());						//从数据库查询出最新的用户对象
		
		String _oldpwd=Md5Util.makePassword(oldpwd, user.getSalt());		//检查原密码是否正确
		if(!_oldpwd.equals(user.getPassword())) {
			return result(-1, "原密码不正确!");
		}
		try {
			userService.updatePassword(user.getId(), newpwd);
		} catch(Exception e) {
			return result(-1, e.getMessage());
		}
		return result(200, "OK");
	}
	
	@RequestMapping(value="/emp",method=RequestMethod.POST)
	@ResponseBody
    public JsonResult setupEmp(String empId) {
		
		OnlineUser loginUser=loginuser();
		organService.setMainEmployee(loginUser.getId(), empId);
		
		//刷新用户组织信息
		Organ emp=organService.getMainEmployee(loginUser.getId());
		if(emp!=null) {
			List<Organ> emps=organService.getOrgansByUserId(loginUser.getId());
			Organ dept=organService.getMainDepartment(loginUser.getId());
			List<Organ> parents=organService.getParents(emp.getId());
			loginUser.setEmployee(emp);
			loginUser.setEmployees(emps);
			loginUser.setDepartment(dept);
			loginUser.setParents(parents);
		}
		return result(200, "OK");
	}
	
	/**
     * 当前用户对应的所有员工列表
     */
	@RequestMapping(value="/emps",method=RequestMethod.POST)
    public String emps() {
    	User loginUser = loginuser();
    	
    	List<Organ> emps = organService.getOrgansByUserId(loginUser.getId());
		for(int i=0; i<emps.size();i++) {
			Organ emp = emps.get(i);
			List<Organ> parents = organService.getParents(emp.getId());
			
			StringBuilder sb = new StringBuilder("/");
			for(int j=parents.size()-1; j>=0; j--) {
				Organ p = parents.get(j);
				sb.append(p.getName());
				if(j>0) sb.append("/");
			}
			emp.setName(sb.toString());
		}
		
		Paginator<Organ> pagin=new Paginator<Organ>(0,10000);
    	pagin.setPageSize(emps.size());
    	pagin.setTotalCount(emps.size());
    	pagin.setDataList(emps);
    	return pagin(pagin);
    }
}
