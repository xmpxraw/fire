package org.yuantai.system.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.yuantai.common.JsonResult;
import org.yuantai.common.TreeNode;
import org.yuantai.system.pojo.Function;
import org.yuantai.system.pojo.OnlineUser;
import org.yuantai.system.pojo.Role;
import org.yuantai.system.service.FunctionService;
import org.yuantai.system.service.OnlineUserService;
import org.yuantai.system.service.RoleService;

/**
 * 角色管理
 * @author zhangle
 */
@Controller
@RequestMapping(value="/system/role")
public class RoleAction extends ModelAction<Role> {

	@Autowired private RoleService roleService;
	@Autowired private FunctionService  functionService;
	@Autowired private OnlineUserService onlineUserService;
    @RequestMapping(method=RequestMethod.GET)
	public String list() {
		return "system/role/role-list";
	}
    
    @RequestMapping(value="/query",method=RequestMethod.GET)
    public String query() {
    	return "system/role/role-query";
    }
    
    @RequestMapping(value="/query",method=RequestMethod.POST)
	public String query(ServletRequest request, ServletResponse response,Role param) {
   // 	HttpServletRequest req=(HttpServletRequest)request;
		DetachedCriteria criteria=getPaginator().getCriteria();
		if(!StringUtils.isEmpty(param.getName())) {
			criteria.add(Restrictions.like("name", param.getName(),MatchMode.ANYWHERE));
		}
		if(param.getStatus()!=null) {
			criteria.add(Restrictions.eq("status", param.getStatus()));
		}
	/*	OnlineUser currentUser =  onlineUserService.getLoginUser(req.getSession());
		if(currentUser.getSysCode()!=null&&currentUser.getSysCode()!=""){
			criteria.add(Restrictions.eq("sysCode", currentUser.getSysCode()));
		}*/
		
		criteria.addOrder(Order.asc("seqNum"));
		roleService.find(getPaginator());
		return pagin(getPaginator());
	}
	
    @RequestMapping(value="/add",method=RequestMethod.GET)
	public String add() {
		return "system/role/role-update";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doAdd(@RequestBody Role role) {
		role.setCreateUserId(loginuser().getId());
		roleService.add(role);
		return result(200, "OK");
	}
	
	/**
	 * 检查角色是否存在
	 * @param name
	 * @return code:200 表示不存在,code:-1 表示已存在
	 */
	@RequestMapping(value="/check_role",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult checkRole(String name) {
		boolean exist=roleService.getRoleByName(name)!=null;
		if(exist) {
			return result(-1, "already exists");
		} else {
			return result(200, "not exist");
		}
	}
	
	@RequestMapping(value="/update/{id}",method=RequestMethod.GET)
	public String update(@PathVariable String id) {
		Role role=roleService.get(id);
		attr("role", role);
		return "system/role/role-update";
	}
	
	@RequestMapping(value="/update/{id}",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doUpdate(@PathVariable String id,@RequestBody Role param) {
		
		Role role=roleService.get(id);
		role.setName(param.getName());
		role.setStatus(param.getStatus());
		role.setRemark(param.getRemark());
		role.setType(param.getType());
		role.setCheckedFunctionId(param.getCheckedFunctionId());
		role.setUncheckdFunctionId(param.getUncheckdFunctionId());
		roleService.update(role);
		return result(200, "OK");
	}
	
	@RequestMapping(value="/delete/{id}",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doDelete(@PathVariable String id) {
		roleService.delete(id);
		return result(200, "OK");
	}
	
	/**
	 * 为角色分配功能时,返回功能的树形组件的json对象
	 */
    @RequestMapping(value="/func_tree",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
	public List<TreeNode> funcTree() {
		
		String id=getId();
		List<Function> functions=functionService.getChildren(id, Function.STATUS_DEFAULT);
		List<TreeNode> tree=new ArrayList<TreeNode>(functions.size());
		
		String roleId=param("roleId");
    	List<Function> selectedFunctions=Collections.EMPTY_LIST;
    	if(!StringUtils.isBlank(roleId)) {
    		selectedFunctions=functionService.getChildrenForRole(roleId, id,null);
    	}
		
		for(Function f:functions) {
			TreeNode node=new TreeNode();
			node.setId(f.getId());
			node.setText(f.getName());
			if(f.getType()==Function.TYPE_DIRECTORY) {
				node.setState(TreeNode.STATE_CLOSED);
				node.setIconCls("icon-folder");
			} else {
				node.setState(TreeNode.STATE_OPEN);
				node.setIconCls("icon-star");
			}
			node.setChecked(selectedFunctions.contains(f));
			node.getAttributes().put("parentId", f.getParentId());
			node.getAttributes().put("type", f.getType());
			tree.add(node);
		}
		return tree;
	}
    
    @RequestMapping(value="/sort",method=RequestMethod.GET)
   	public String sort() {
   		return "system/role/role-sort";
   	}
   	
   	@RequestMapping(value="/sort",method=RequestMethod.POST)
   	@ResponseBody
   	public JsonResult doSort(@RequestBody Role[] roles) {
   		roleService.sort(roles);
   		return result(200, "OK");
   	}
}
