package org.yuantai.school.web;

import java.util.Date;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yuantai.common.JsonResult;
import org.yuantai.school.pojo.School;
import org.yuantai.school.pojo.WeixinAccounts;
import org.yuantai.school.service.SchoolService;
import org.yuantai.school.service.WeixinAccountsService;
import org.yuantai.system.pojo.OnlineUser;
import org.yuantai.system.service.OnlineUserService;
import org.yuantai.system.web.ModelAction;


/**
 * 微信参数管理
 * @author zamn
 *
 */
@Controller
@RequestMapping(value="/school/weixinAccounts")
public class WeixinAccountsAction extends ModelAction<WeixinAccounts>{
	
	private static final Logger logger = LogManager.getLogger(WeixinAccountsAction.class);
	@Autowired private OnlineUserService onlineUserService;
    @Autowired private WeixinAccountsService weixinAccountsService;
    @Autowired private SchoolService schoolService;
	
    /**
     * 跳到我微信账户参数列表页面
     */
    @RequestMapping(method=RequestMethod.GET)
	public String list() {
		return "school/weixinAccounts/weixinAccounts-list";
	}
    /**
	 * 查询微信账户参数
	 */
    @RequestMapping(value="/query",method=RequestMethod.POST)
	public String query(ServletRequest request, ServletResponse response,WeixinAccounts param) {
    	HttpServletRequest req=(HttpServletRequest)request;
		DetachedCriteria criteria=getPaginator().getCriteria();
		OnlineUser currentUser =  onlineUserService.getLoginUser(req.getSession());
		if(currentUser.getSysCode()!=null&&currentUser.getSysCode()!=""){
			criteria.add(Restrictions.eq("sysCode", currentUser.getSysCode()));
		}
	/*	criteria.addOrder(Order.desc("createTime"));*/
		weixinAccountsService.find(getPaginator());
		return pagin(getPaginator());
	}
    
    
   
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add() {
		return "school/weixinAccounts/weixinAccounts-update";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doAdd(@RequestBody WeixinAccounts data,ServletRequest request, ServletResponse response) {
		
		HttpServletRequest req=(HttpServletRequest)request;
		OnlineUser currentUser =  onlineUserService.getLoginUser(req.getSession());
		if(currentUser.getSysCode()!=null){
			School school= schoolService.getSchoolBySysCode(currentUser.getSysCode());
			data.setSystemId(school.getSchoolCode());
		}
		data.setCreateTime(new Date());
		data.setSysCode(currentUser.getSysCode());
		data.setIsEnabled(true);
		weixinAccountsService.add(data);
		return result(200, "OK");
	}



	@RequestMapping(value="/update/{id}",method=RequestMethod.GET)
	public String update(@PathVariable String id) {
		WeixinAccounts weixin=weixinAccountsService.get(id);
		attr("weixin", weixin);
		return "school/weixinAccounts/weixinAccounts-update";
	}
	
	@RequestMapping(value="/update/{id}",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doUpdate(@PathVariable String id,@RequestBody WeixinAccounts param) {	
		WeixinAccounts weixinAccounts=weixinAccountsService.get(id);
		weixinAccounts.setAccountType(param.getAccountType());
		weixinAccounts.setAppId(param.getAppId());
		weixinAccounts.setEncodingAesKey(param.getEncodingAesKey());
		weixinAccounts.setEncryptType(param.getEncryptType());
		weixinAccounts.setName(param.getName());
		weixinAccounts.setSecret(param.getSecret());
		weixinAccounts.setToken(param.getToken());
		weixinAccounts.setUpdateTime(new Date());
		weixinAccountsService.update(weixinAccounts);
		return result(200, "OK");
	}
	
	@RequestMapping(value="/delete/{id}",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doDelete(@PathVariable String id) {
		
		weixinAccountsService.delete(id);
		
  	 return result(200, "OK");
	}
    
	
}
