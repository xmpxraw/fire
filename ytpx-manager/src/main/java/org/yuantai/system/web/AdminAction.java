package org.yuantai.system.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yuantai.basic.pojo.Feedback;
import org.yuantai.basic.pojo.Helptext;
import org.yuantai.basic.pojo.News;
import org.yuantai.basic.service.ChangelogService;
import org.yuantai.basic.service.FeedbackService;
import org.yuantai.basic.service.HelptextService;
import org.yuantai.basic.service.NewsService;
import org.yuantai.common.Paginator;
import org.yuantai.common.TreeNode;
import org.yuantai.common.util.StringUtil;
import org.yuantai.system.pojo.Menu;
import org.yuantai.system.pojo.OnlineUser;
import org.yuantai.system.pojo.Organ;
import org.yuantai.system.service.AuthService;


/**
 * 后台首页
 * @author zhangle
 */
@Controller
public class AdminAction extends BaseAction {
	
	@Autowired private AuthService authService;
	@Autowired private NewsService newsService;
	@Autowired private FeedbackService feedbackService;
	@Autowired private ChangelogService changelogService;
	@Autowired private HelptextService helptextService;
	
	/**
	 * 后台框架页
	 */
	@RequestMapping(value="/admin",method=RequestMethod.GET)
	public String admin() {
		OnlineUser loginUser=loginuser();
		attr("loginUser", loginUser);
		List<Menu> menus=authService.checkMenu(loginUser, null);
		attr("menus", menus);
		return "system/admin/admin";
	}
	
	/**
	 * 管理首页
	 * @return
	 */
	@RequestMapping(value="/admin/main",method=RequestMethod.GET)
	public String main() {
			
		List<News> newslist=newsService.findForHome(0, 8);
		attr("newslist",newslist);
		List<Feedback> feedlist=feedbackService.findForHome(Feedback.ROOT,0, 8);
		attr("feedlist",feedlist);
		List<Helptext> helplist=helptextService.findForHome(0, 8);
		attr("helplist",helplist);
		
		return "system/admin/main";
	}
	
	/**
	 * 加载左边的子菜单
	 */
	@RequestMapping(value="/admin/leftmenu/{menuId}",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public List<TreeNode> leftmenu(@PathVariable String menuId,String id) {
		
		String parentId=StringUtil.isEmpty(id)?menuId:id;
		OnlineUser loginUser=loginuser();
		List<Menu> menus=authService.checkMenu(loginUser, parentId);
		List<TreeNode> tree=new ArrayList<TreeNode>(menus.size());
		
		for(Menu m:menus) {
			TreeNode node=new TreeNode();
			node.setId(m.getId());
			node.setText(m.getName());
			if(m.getType()==Menu.TYPE_DIRECTORY) {
				node.setState(TreeNode.STATE_CLOSED);
				if(!StringUtils.isBlank(m.getIcon())) {
					node.setIconCls(m.getIcon());
				}
			} else {
				node.setState(TreeNode.STATE_OPEN);
				if(StringUtils.isBlank(m.getIcon())) {
					node.setIconCls("icon-pict");
				} else {
					node.setIconCls(m.getIcon());
				}
			}
			node.getAttributes().put("parentId", m.getParentId());
			node.getAttributes().put("type", m.getType());
			node.getAttributes().put("url", m.getUrl());
			tree.add(node);
		}
		return tree;
	}
	
	/**
	 * 常用工具页
	 * @return
	 */
	@RequestMapping(value="/admin/tools",method=RequestMethod.GET)
	public String tools() {
		return "system/admin/tools";
	}
	
	
}
