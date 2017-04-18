package org.yuantai.school.web;

import java.util.Date;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.yuantai.common.JsonResult;
import org.yuantai.school.pojo.FeesScheme;
import org.yuantai.school.pojo.School;
import org.yuantai.school.service.FeesSchemeService;
import org.yuantai.system.pojo.OnlineUser;
import org.yuantai.system.pojo.User;
import org.yuantai.system.service.OnlineUserService;
import org.yuantai.system.web.ModelAction;


/**
 * 费用方案管理
 * @author zamn
 *
 */
@Controller
@RequestMapping(value="/school/fees")
public class FeesSchemeAction extends ModelAction<FeesScheme>{
	
	private static final Logger logger = LogManager.getLogger(FeesSchemeAction.class);
	@Autowired private OnlineUserService onlineUserService;
    @Autowired private FeesSchemeService feesSchemeService;

	
    /**
     * 跳到费用方案列表页面
     */
    @RequestMapping(method=RequestMethod.GET)
	public String list() {
		return "school/fees/fees-list";
	}
    
    @RequestMapping(value="/query",method=RequestMethod.GET)
    public String query() {
    	return "school/fees/fees-query";
    }
    
    /**
	 * 查询费用方案
	 */
    @RequestMapping(value="/query",method=RequestMethod.POST)
	public String query(ServletRequest request, ServletResponse response,FeesScheme param) {
    	HttpServletRequest req=(HttpServletRequest)request;
		DetachedCriteria criteria=getPaginator().getCriteria();
		if(!StringUtils.isEmpty(param.getSchemeName())) {
			criteria.add(Restrictions.like("schemeName",param.getSchemeName(),MatchMode.ANYWHERE));
		}
		
		if(param.getSchemeStatus()!=null) {
			criteria.add(Restrictions.eq("schemeStatus", param.getSchemeStatus()));
		}
		
		OnlineUser currentUser =  onlineUserService.getLoginUser(req.getSession());
		if(currentUser.getSysCode()!=null&&currentUser.getSysCode()!=""){
			criteria.add(Restrictions.eq("sysCode", currentUser.getSysCode()));
		}
		criteria.addOrder(Order.desc("createTime"));
		feesSchemeService.find(getPaginator());
		return pagin(getPaginator());
	}
    
    @RequestMapping(value="/check_status",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult checkStatus(String id) {
	//	boolean exist=userService.getUserByName(username)!=null;
		Integer status= feesSchemeService.get(id).getSchemeStatus();
		if(status==0) {//暂停状态
			return result(0, "stop");
		}else if(status==1) {//启用状态
			return result(1, "start");//暂停
		}else {
			return result(500, "error");
		}
	}
	
	@RequestMapping(value="/stop/{id}",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult stop(@PathVariable String id) {
		
		FeesScheme fees=feesSchemeService.get(id);
		fees.setSchemeStatus(0);
		feesSchemeService.update(fees);
		
		return result(200, "OK");
	}
	
	@RequestMapping(value="/start/{id}",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult start(@PathVariable String id) {
		FeesScheme fees=feesSchemeService.get(id);
		fees.setSchemeStatus(1);
		feesSchemeService.update(fees);
		
		return result(200, "OK");
	}
   
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add() {
		return "school/fees/fees-add";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doAdd(@RequestBody FeesScheme feesScheme,ServletRequest request, ServletResponse response) {
		HttpServletRequest req=(HttpServletRequest)request;
		OnlineUser currentUser =  onlineUserService.getLoginUser(req.getSession());
		feesScheme.setSysCode(currentUser.getSysCode());
		feesScheme.setSchemeStatus(0);
		feesScheme.setCreateTime(new Date());
		feesSchemeService.add(feesScheme);
		return result(200, "OK");
	}
	

	@RequestMapping(value="/update/{id}",method=RequestMethod.GET)
	public String update(@PathVariable String id) {
		FeesScheme feesScheme=feesSchemeService.get(id);
		attr("fees", feesScheme);
		return "school/fees/fees-update";
	}
	
	@RequestMapping(value="/update/{id}",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doUpdate(@PathVariable String id,@RequestBody FeesScheme param) {
		
		FeesScheme feesScheme=feesSchemeService.get(id);
		feesScheme.setFeesHotel(param.getFeesHotel());
		feesScheme.setFeesMeal(param.getFeesMeal());
		feesScheme.setFeesStudy(param.getFeesStudy());
		feesScheme.setFeesTrain(param.getFeesTrain());
		feesScheme.setFeesTotal(param.getFeesTotal());
		feesScheme.setSchemeDesc(param.getSchemeDesc());
		feesScheme.setSchemeName(param.getSchemeName());

		feesSchemeService.update(feesScheme);
		return result(200, "OK");
	}
	
	@RequestMapping(value="/delete/{id}",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doDelete(@PathVariable String id) {
		
		feesSchemeService.delete(id);
		
  	 return result(200, "OK");
	}
    
	
}
