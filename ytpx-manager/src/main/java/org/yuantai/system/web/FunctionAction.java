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
import org.yuantai.system.pojo.Function;
import org.yuantai.system.pojo.Menu;
import org.yuantai.system.service.FunctionService;

/**
 * 功能管理
 * @author zhangle
 */
@Controller
@RequestMapping(value="/system/function")
public class FunctionAction extends ModelAction<Function>{

	@Autowired private FunctionService  functionService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String list() {
		return "system/function/function-list";
	}
    
    @RequestMapping(value="/query",method=RequestMethod.GET)
    public String query() {
    	return "system/function/function-query";
    }

    @RequestMapping(value="/query",method=RequestMethod.POST)
    @ResponseBody
	public List<TreeNode> query(Function param) {
		
		String id=getId();
		
		List<Function> functions=functionService.getChildren(id, null);
		List<TreeNode> tree=new ArrayList<TreeNode>(functions.size());
		
		for(Function f:functions) {
			TreeNode node=new TreeNode();
			node.setId(f.getId());
			node.setText(f.getName());
			if(f.getType()==Function.TYPE_DIRECTORY) {
				node.setState(TreeNode.STATE_CLOSED);
				node.setIconCls("icon-folder");
			} else {
				node.setState(TreeNode.STATE_OPEN);
				if(f.getStatus()==Function.STATUS_DEFAULT) {
					node.setIconCls("icon-star");
				} else {
					node.setIconCls("icon-star-dis");
				}
			}
			node.getAttributes().put("parentId", f.getParentId());
			node.getAttributes().put("type", f.getType());
			tree.add(node);
		}
		return tree;
	}
    
    @RequestMapping(value="/add",method=RequestMethod.GET)
   	public String add(@RequestParam(required=false) String parentId) {
    	if(!StringUtils.isBlank(parentId)) {
    		Function parent=functionService.get(parentId);
    		attr("parent", parent);
    	}
   		return "system/function/function-update";
   	}
   	
   	@RequestMapping(value="/add",method=RequestMethod.POST)
   	@ResponseBody
   	public JsonResult doAdd(Function function) {
   		function.setCreateUserId(loginuser().getId());
   		try {
   			functionService.add(function);
   		} catch(Exception e) {
   			return result(-1, e.getMessage());
   		}
   		return result(200, "OK");
   	}
   	
   	@RequestMapping(value="/update/{id}",method=RequestMethod.GET)
   	public String update(@PathVariable String id) {
   		Function function=functionService.get(id);
   		attr("function", function);
   		if(!StringUtils.isBlank(function.getParentId())) {
    		Function parent=functionService.get(function.getParentId());
    		attr("parent", parent);
    	}
   		return "system/function/function-update";
   	}
   	
   	@RequestMapping(value="/update/{id}",method=RequestMethod.POST)
   	@ResponseBody
   	public JsonResult doUpdate(@PathVariable String id,Function param) {
   		Function function=functionService.get(id);
   		if(function==null) {
   			return result(-1, "功能不存在!");
   		}
   		function.setName(param.getName());
   		function.setCode(param.getCode());
   		function.setType(param.getType());
   		function.setStatus(param.getStatus());
   		function.setUrl(param.getUrl());
   		function.setRemark(param.getRemark());
   		try {
   			functionService.update(function);
   			return result(200, "OK");
   		} catch(Exception e) {
   			return result(-1, e.getMessage());
   		}
   	}
   	
   	@RequestMapping(value="/delete/{id}",method=RequestMethod.POST)
   	@ResponseBody
   	public JsonResult doDelete(@PathVariable String id) {
   		functionService.delete(id);
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
		functionService.move(targetId, sourceId, point);
		return result(200, "OK");
	}
}
