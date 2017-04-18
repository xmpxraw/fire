package org.yuantai.system.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yuantai.common.JsonResult;
import org.yuantai.common.Paginator;
import org.yuantai.system.pojo.OnlineUser;
import org.yuantai.system.service.OnlineUserService;

/**
 * 用户管理
 * @author zhangle
 */
@Controller
@RequestMapping(value="/system/online_user")
public class OnlineUserAction extends BaseAction {
	
	@Autowired private OnlineUserService onlineUserService;

    /**
     * 在线用户列表页
     * @return
     */
	@RequestMapping(method=RequestMethod.GET)
    public String online() {
    	return "system/user/user-online-list";
    }
    
    /**
     * 查询在线用户
     */
	@RequestMapping(value="/query",method=RequestMethod.POST)
    public String query() {
    	Paginator<OnlineUser> pagin=new Paginator<OnlineUser>(0,10000);
    	List<OnlineUser> users=onlineUserService.getOnlineUsers();
    	pagin.setTotalCount(users.size());
    	pagin.setDataList(users);
    	return pagin(pagin);
    }
	
	@RequestMapping(value="/logout/{sessionId}",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult logout(@PathVariable String sessionId) {
		if(session().getId().equals(sessionId)) {
			return result(-1, "不能注销当前登录用户!");
		}
		onlineUserService.delete(sessionId);
		return result(200, "OK");
	}
}
