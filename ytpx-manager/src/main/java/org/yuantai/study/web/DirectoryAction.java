package org.yuantai.study.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yuantai.common.JsonResult;
import org.yuantai.common.TreeNode;
import org.yuantai.common.util.JsonUtil;
import org.yuantai.study.pojo.Directory;
import org.yuantai.study.service.DirectoryService;
import org.yuantai.system.web.BaseAction;

/**
 * DirectoryAction
 * 
 * @author cola
 * @version 2016年12月23日
 */
@Controller
@RequestMapping(value = "/directory")
public class DirectoryAction extends BaseAction {

	private Logger logger = LoggerFactory.getLogger(DirectoryAction.class);

	@Autowired
	private DirectoryService directoryService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list() {
		return "study/directory/list";
	}

	/**
	 * 返回treegrid数据
	 * 
	 * @param parentId
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/treedata", method = RequestMethod.POST, produces = {"application/json;charset=utf8"})
	@ResponseBody
	public String treeData(String parentId, Integer status) {
		List<Directory> directorys = directoryService.getAllChildrenDirectory(parentId, status);

		return JsonUtil.toJson(directorys);
	}
	
	@RequestMapping(value = "/tree_select_data", method = RequestMethod.POST, produces = {"application/json;charset=utf8"})
	@ResponseBody
	public String treeSelectData(String parentId, Integer status) {
		List<Directory> directorys = directoryService.getAllChildrenDirectory(parentId, status);
		List<TreeNode> treeNodes = new ArrayList<TreeNode>();
		for (Directory dir : directorys) {
			treeNodes.add(dir.getTreeNode());
		}
		return JsonUtil.toJson(treeNodes);
	}
	
	
	/**
	 * 禁用或者启用目录
	 */
	@RequestMapping(value = "/change_status", method = RequestMethod.POST, produces = {"application/json;charset=utf8"})
	@ResponseBody
	public JsonResult changeDirectoryStatus(String id, Integer status) {
		if (StringUtils.isEmpty(id) || status == null) {
			return result(500, "记录不存在或状态为空");
		}
		Directory dir = directoryService.get(id);
		if (dir != null) {
			dir.setStatus(status);
			directoryService.update(dir);
		}
		return result(200, "操作成功");
	}
	
	/**
	 * 增加目录
	 */
	@RequestMapping(value = "/add")
	public String add(String id, ModelMap model) {
		Directory directory = new Directory();
		if (StringUtils.isNotEmpty(id)) {
		    directory = directoryService.get(id);
			model.addAttribute("directory", directory);
		}
		return "study/directory/add";
	}
	
	/**
	 * 保存目录
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doAdd(@RequestBody Directory directory) {
		String msg = directoryService.save(directory, loginuser().getId());
		logger.debug("Save directory : " + msg);
		if ("OK".equals(msg)) {
			return result(200, "OK");
		} else {
			return result(500, msg);
		}
		
	}
}
