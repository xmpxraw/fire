package org.yuantai.basic.web;

import java.util.Date;
import java.util.List;

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
import org.yuantai.basic.pojo.Helptext;
import org.yuantai.basic.service.HelptextService;
import org.yuantai.common.JsonResult;
import org.yuantai.system.web.ModelAction;

@Controller
@RequestMapping(value="/basic/helptext")
public class HelptextAction extends ModelAction<Helptext> {

	@Autowired private HelptextService helptextService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String list() {
		return "basic/helptext/helptext-list";
	}
    
    @RequestMapping(value="/query",method=RequestMethod.GET)
    public String query() {
    	return "basic/helptext/helptext-query";
    }
    
    @RequestMapping(value="/query",method=RequestMethod.POST)
	public String query(Helptext param) {
		
		DetachedCriteria criteria=getPaginator().getCriteria();
		if(!StringUtils.isEmpty(param.getName())) {
			criteria.add(Restrictions.like("name", param.getName(),MatchMode.ANYWHERE));
		}
		criteria.add(Restrictions.ge("status", Helptext.STATUS_PRIVATE));
		criteria.addOrder(Order.asc("seqnum"));
		helptextService.find(getPaginator());
		return pagin(getPaginator());
	}
	
    @RequestMapping(value="/add",method=RequestMethod.GET)
	public String add() {
		return "basic/helptext/helptext-update";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doAdd(@RequestBody Helptext helptext) {
		
		helptext.setCreatetime(new Date());
		helptext.setVisitcount(0);
		helptext.setUsername(loginuser().getUsername());
		helptextService.add(helptext);
		return result(200, "OK");
	}
	
	@RequestMapping(value="/update/{id}",method=RequestMethod.GET)
	public String update(@PathVariable String id) {
		Helptext helptext=helptextService.get(id);
		attr("helptext", helptext);
		return "basic/helptext/helptext-update";
	}
	
	@RequestMapping(value="/update/{id}",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doUpdate(@PathVariable String id,@RequestBody Helptext param) {
		
		Helptext helptext=helptextService.get(id);
		helptext.setName(param.getName());
		helptext.setContent(param.getContent());
		helptext.setStatus(param.getStatus());
		helptextService.update(helptext);
		return result(200, "OK");
	}
	
	@RequestMapping(value="/delete/{id}",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doDelete(@PathVariable String id) {
		helptextService.delete(id);
		return result(200, "OK");
	}
	
    @RequestMapping(value="/sort",method=RequestMethod.GET)
   	public String sort() {
   		return "basic/helptext/helptext-sort";
   	}
   	
   	@RequestMapping(value="/sort",method=RequestMethod.POST)
   	@ResponseBody
   	public JsonResult doSort(@RequestBody Helptext[] helptexts) {
   		helptextService.sort(helptexts);
   		return result(200, "OK");
   	}
   	
   	@RequestMapping(value="/open/{id}",method=RequestMethod.GET)
	public String open(@PathVariable String id) {
		Helptext helptext=helptextService.get(id);
		helptext.setVisitcount(helptext.getVisitcount()+1);
		helptextService.update(helptext);
		attr("helptext", helptext);
		return "basic/helptext/helptext-open";
	}
   	
   	@RequestMapping(value="/homelist",method=RequestMethod.GET)
	public String homelist() {
		
   		DetachedCriteria criteria=getPaginator().getCriteria();
		criteria.add(Restrictions.ge("status", Helptext.STATUS_PUBLIC));
		criteria.addOrder(Order.asc("seqnum"));
		List<Helptext> helplist=helptextService.find(criteria);
		attr("helplist",helplist);
		return "basic/helptext/helptext-homelist";
	}
}
