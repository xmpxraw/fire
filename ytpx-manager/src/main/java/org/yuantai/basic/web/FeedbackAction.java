package org.yuantai.basic.web;

import java.util.Date;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yuantai.basic.pojo.Feedback;
import org.yuantai.basic.service.FeedbackService;
import org.yuantai.common.JsonResult;
import org.yuantai.common.Paginator;
import org.yuantai.common.util.StringUtil;
import org.yuantai.system.web.ModelAction;

@Controller
@RequestMapping(value="/basic/feedback")
public class FeedbackAction extends ModelAction<Feedback> {

	@Autowired private FeedbackService feedbackService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String list() {
		return "basic/feedback/feedback-list";
	}
    
    @RequestMapping(value="/query",method=RequestMethod.GET)
    public String query() {
    	return "basic/feedback/feedback-query";
    }
    
    @RequestMapping(value="/query",method=RequestMethod.POST)
	public String query(Feedback param) {
		
		Paginator<Feedback> pagin = getPaginator();
		DetachedCriteria criteria=pagin.getCriteria();
		if(!StringUtils.isEmpty(param.getName())) {
			criteria.add(Restrictions.like("name", param.getName(),MatchMode.ANYWHERE));
		}
		if(StringUtils.isEmpty(param.getId())) {
			criteria.add(Restrictions.eq("pid", Feedback.ROOT));
		} else {
			criteria.add(Restrictions.eq("pid", param.getId()));
		}
		criteria.add(Restrictions.ge("status", Feedback.STATUS_PRIVATE));
		criteria.addOrder(Order.asc("seqnum"));
		feedbackService.find(pagin);
		if(StringUtils.isEmpty(param.getId())) {
			for(Feedback feed:pagin.getDataList()) {
				feed.setState("closed");
			}
		}
		return pagin(pagin);
	}
	
    @RequestMapping(value="/add",method=RequestMethod.GET)
	public String add() {
		return "basic/feedback/feedback-update";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doAdd(@RequestBody Feedback feedback) {
		
		if(StringUtil.isEmpty(feedback.getPid())) {
			feedback.setPid(Feedback.ROOT);
		}
		feedback.setCreatetime(new Date());
		feedback.setVisitcount(0);
		feedback.setUsername(loginuser().getUsername());
		feedbackService.add(feedback);
		return result(200, "OK");
	}
	
	@RequestMapping(value="/update/{id}",method=RequestMethod.GET)
	public String update(@PathVariable String id) {
		Feedback feedback=feedbackService.get(id);
		attr("feedback", feedback);
		return "basic/feedback/feedback-update";
	}
	
	@RequestMapping(value="/update/{id}",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doUpdate(@PathVariable String id,@RequestBody Feedback param) {
		
		Feedback feedback=feedbackService.get(id);
		feedback.setName(param.getName());
		feedback.setContent(param.getContent());
		feedback.setStatus(param.getStatus());
		feedback.setResolvestatus(param.getResolvestatus());
		feedbackService.update(feedback);
		return result(200, "OK");
	}
	
	@RequestMapping(value="/delete/{id}",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doDelete(@PathVariable String id) {
		feedbackService.delete(id);
		return result(200, "OK");
	}
	
    @RequestMapping(value="/sort",method=RequestMethod.GET)
   	public String sort() {
   		return "basic/feedback/feedback-sort";
   	}
   	
   	@RequestMapping(value="/sort",method=RequestMethod.POST)
   	@ResponseBody
   	public JsonResult doSort(@RequestBody Feedback[] feedback) {
   		feedbackService.sort(feedback);
   		return result(200, "OK");
   	}
   	
   	//=====================  首页使用  =============================
   	/**
   	 * 打开意见反馈
   	 * @param id
   	 * @return
   	 */
   	@RequestMapping(value="/open/{id}",method=RequestMethod.GET)
	public String open(@PathVariable String id) {
   		Feedback feedback=feedbackService.get(id);
   		feedback.setVisitcount(feedback.getVisitcount()+1);
   		feedbackService.update(feedback);
		attr("feedback", feedback);
		
		//查询回复内容
		Paginator<Feedback> pagin=this.getPaginator();
		pagin.setPageSize(100);	//只查询100条回复记录
   		DetachedCriteria criteria=pagin.getCriteria();
   		criteria.add(Restrictions.eq("pid", id));
   		criteria.add(Restrictions.eq("status", Feedback.STATUS_PUBLIC));
   		criteria.addOrder(Order.asc("seqnum"));
   		feedbackService.find(pagin);
   		attr("comments", pagin.getDataList());
   		
		return "basic/feedback/feedback-open";
	}
   	
   	/**
   	 * 首页的意见反馈列表
   	 * @return
   	 */
   	@RequestMapping(value="/homelist",method=RequestMethod.GET)
	public String homelist() {
   		
   		Paginator<Feedback> pagin=this.getPaginator();
   		DetachedCriteria criteria=pagin.getCriteria();
   		criteria.add(Restrictions.eq("pid", Feedback.ROOT));
   		criteria.add(Restrictions.eq("status", Feedback.STATUS_PUBLIC));
   		criteria.addOrder(Order.desc("seqnum"));
   		feedbackService.find(pagin);
   		
		return "basic/feedback/feedback-homelist";
	}
   	
   	/**
   	 * 回复意见
   	 * @param id
   	 * @param content
   	 * @return
   	 */
   	@RequestMapping(value="/addcomment/{id}",method=RequestMethod.POST)
	public @ResponseBody JsonResult addComment(@PathVariable String id,@RequestParam String content) {
		
		Feedback feedback=new Feedback();
		feedback.setPid(id);
		feedback.setContent(content);
		feedback.setCreatetime(new Date());
		feedback.setVisitcount(0);
		feedback.setUsername(loginuser().getUsername());
		feedback.setResolvestatus(0);
		feedback.setStatus(Feedback.STATUS_PUBLIC);
		feedbackService.add(feedback);
		return result(200, "OK");
	}
   	
   	/**
   	 * 提交意见页面
   	 * @return
   	 */
   	@RequestMapping(value="/submit",method=RequestMethod.GET)
    public String submit() {
    	return "basic/feedback/feedback-submit";
    }
   	
}
