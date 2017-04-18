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
import org.yuantai.system.service.DataService;

@Controller
@RequestMapping(value="/system/data")
public class DataAction extends ModelAction<Data> {

	@Autowired private DataService dataService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String list() {
		return "system/data/data-list";
	}
    
    @RequestMapping(value="/query",method=RequestMethod.GET)
    public String query() {
    	return "system/data/data-query";
    }
    
    @RequestMapping(value="/query",method=RequestMethod.POST)
    @ResponseBody
	public List<TreeNode> query(Data param) {
		
		String id=getId();
		
		List<Data> datas=dataService.getChildren(id, null);
		List<TreeNode> tree=new ArrayList<TreeNode>(datas.size());
		
		for(Data m:datas) {
			TreeNode node=new TreeNode();
			node.setId(m.getId());
			node.setText(m.getText());
			if(m.getType()==Data.TYPE_DIRECTORY) {
				node.setState(TreeNode.STATE_CLOSED);
				node.setIconCls("icon-folder");
			} else {
				node.setState(TreeNode.STATE_OPEN);
				if(m.getStatus()==Data.STATUS_DEFAULT) {
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
    		Data parent=dataService.get(parentId);
    		attr("parent", parent);
    	}
   		return "system/data/data-update";
   	}
   	
   	@RequestMapping(value="/add",method=RequestMethod.POST)
   	@ResponseBody
   	public JsonResult doAdd(Data data) {
   		data.setCreateUserId(loginuser().getId());
   		try {
   			dataService.add(data);
   		} catch(Exception e) {
   			return result(-1, e.getMessage());
   		}
   		return result(200, "OK");
   	}
   	
   	@RequestMapping(value="/update/{id}",method=RequestMethod.GET)
   	public String update(@PathVariable String id) {
   		Data data=dataService.get(id);
   		attr("data", data);
   		if(!StringUtils.isBlank(data.getParentId())) {
    		Data parent=dataService.get(data.getParentId());
    		attr("parent", parent);
    	}
   		return "system/data/data-update";
   	}
   	
   	@RequestMapping(value="/update/{id}",method=RequestMethod.POST)
   	@ResponseBody
   	public JsonResult doUpdate(@PathVariable String id,Data param) {
   		Data data=dataService.get(id);
   		if(data==null) {
   			return result(-1, "基础数据不存在!");
   		}
   		data.setText(param.getText());
   		data.setValue(param.getValue());
   		data.setCode(param.getCode());
   		data.setStatus(param.getStatus());
   		data.setType(param.getType());
   		data.setRemark(param.getRemark());
   		try {
   			dataService.update(data);
   			return result(200, "OK");
   		} catch(Exception e) {
   			return result(-1, e.getMessage());
   		}
   	}
   	
   	@RequestMapping(value="/delete/{id}",method=RequestMethod.POST)
   	@ResponseBody
   	public JsonResult doDelete(@PathVariable String id) {
   		dataService.delete(id);
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
		dataService.move(targetId, sourceId, point);
		return result(200, "OK");
	}
	
	@RequestMapping(value="/check_code",method=RequestMethod.POST)
   	@ResponseBody
   	public JsonResult checkCode(@RequestParam(required=false) String id,@RequestParam String code) {
		Data data=dataService.getByCode(code);
		//如果是添加,那么判断是否已有同名数据
		if(StringUtils.isBlank(id)) {
			if(data==null) {
				return result(200, "OK");
			} else {
				return result(-1, "Code已存在!");
			}
		} else {
			//否则是更新,判断是否有同名角色,如果同名且ID相同,那么通过;否则同名且ID不相同,那么不通过
			if(data==null) return result(200, "OK");;
			if(id.equals(data.getId())) {
				return result(200, "OK");
			} else {
				return result(-1, "Code已存在!");
			}
		}
   	}
}
