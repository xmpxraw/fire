package org.yuantai.system.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yuantai.common.JsonResult;
import org.yuantai.common.TreeNode;
import org.yuantai.system.pojo.Data;
import org.yuantai.system.pojo.Menu;
import org.yuantai.system.service.AuthService;
import org.yuantai.system.service.MenuService;

/**
 * 菜单管理
 * @author zhangle
 */
@Controller
@RequestMapping(value="/system/menu")
public class MenuAction extends ModelAction<Menu> {

	@Autowired private MenuService menuService;
	@Autowired private AuthService authService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String list() {
		return "system/menu/menu-list";
	}
    
    @RequestMapping(value="/query",method=RequestMethod.GET)
    public String query() {
    	return "system/menu/menu-query";
    }
    
    @RequestMapping(value="/query",method=RequestMethod.POST)
    @ResponseBody
	public List<TreeNode> query(Menu param) {
		
		String id=getId();
		
		List<Menu> menus=menuService.getChildren(id, null);
		List<TreeNode> tree=new ArrayList<TreeNode>(menus.size());
		
		for(Menu m:menus) {
			TreeNode node=new TreeNode();
			node.setId(m.getId());
			node.setText(m.getName());
			if(m.getType()==Menu.TYPE_DIRECTORY) {
				node.setState(TreeNode.STATE_CLOSED);
				node.setIconCls("icon-menu");
			} else {
				node.setState(TreeNode.STATE_OPEN);
				if(m.getStatus()==Menu.STATUS_DEFAULT) {
					node.setIconCls("icon-star");
				} else {
					node.setIconCls("icon-star-dis");
				}
			}
			node.getAttributes().put("parentId", m.getParentId());
			node.getAttributes().put("type", m.getType());
			tree.add(node);
		}
		return tree;
	}
    
    @RequestMapping(value="/add",method=RequestMethod.GET)
   	public String add(@RequestParam(required=false) String parentId) {
    	if(!StringUtils.isBlank(parentId)) {
    		Menu parent=menuService.get(parentId);
    		attr("parent", parent);
    	}
   		return "system/menu/menu-update";
   	}
   	
   	@RequestMapping(value="/add",method=RequestMethod.POST)
   	@ResponseBody
   	public JsonResult doAdd(Menu menu) {
   		menu.setCreateUserId(loginuser().getId());
   		try {
   			menuService.add(menu);
   		} catch(Exception e) {
   			return result(-1, e.getMessage());
   		}
   		return result(200, "OK");
   	}
   	
   	@RequestMapping(value="/update/{id}",method=RequestMethod.GET)
   	public String update(@PathVariable String id) {
   		Menu menu=menuService.get(id);
   		attr("menu", menu);
   		if(!StringUtils.isBlank(menu.getParentId())) {
    		Menu parent=menuService.get(menu.getParentId());
    		attr("parent", parent);
    	}
   		return "system/menu/menu-update";
   	}
   	
   	@RequestMapping(value="/update/{id}",method=RequestMethod.POST)
   	@ResponseBody
   	public JsonResult doUpdate(@PathVariable String id,Menu param) {
   		Menu menu=menuService.get(id);
   		if(menu==null) {
   			return result(-1, "菜单不存在!");
   		}
   		menu.setName(param.getName());
   		menu.setType(param.getType());
   		menu.setStatus(param.getStatus());
   		menu.setExpand(param.getExpand());
   		menu.setUrl(param.getUrl());
   		menu.setIcon(param.getIcon());
   		menu.setIconOpen(param.getIconOpen());
   		menu.setRemark(param.getRemark());
   		try {
   			menuService.update(menu);
   			return result(200, "OK");
   		} catch(Exception e) {
   			return result(-1, e.getMessage());
   		}
   	}
   	
   	@RequestMapping(value="/delete/{id}",method=RequestMethod.POST)
   	@ResponseBody
   	public JsonResult doDelete(@PathVariable String id) {
   		menuService.delete(id);
   		return result(200, "OK");
   	}
	
   	/**
	 * 移动节点
	 * @param targetId		目标节点id
	 * @param sourceId		源节点id
	 * @param point			操作类型	取值范围: append,top,bottom
	 */
	@RequestMapping(value="/move",method=RequestMethod.POST)
   	@ResponseBody
	public JsonResult move(@RequestParam String targetId,@RequestParam String sourceId,@RequestParam String point) {
		menuService.move(targetId, sourceId, point);
		return result(200, "OK");
	}
	
	@RequestMapping(value="/refresh_cache",method=RequestMethod.POST)
   	@ResponseBody
	public JsonResult refreshCache() {
		authService.refreshCache();
		return result(200, "OK");
	}
}
