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
import org.springframework.web.bind.annotation.ResponseBody;
import org.yuantai.basic.pojo.News;
import org.yuantai.basic.service.NewsService;
import org.yuantai.common.JsonResult;
import org.yuantai.common.Paginator;
import org.yuantai.system.web.ModelAction;

import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping(value="/basic/news")
public class NewsAction extends ModelAction<News> {

	@Autowired private NewsService newsService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String list() {
		return "basic/news/news-list";
	}
    
    @RequestMapping(value="/query",method=RequestMethod.GET)
    public String query() {
    	return "basic/news/news-query";
    }
    
    @RequestMapping(value="/query",method=RequestMethod.POST)
	public String query(News param) {
		
		DetachedCriteria criteria=getPaginator().getCriteria();
		if(!StringUtils.isEmpty(param.getName())) {
			criteria.add(Restrictions.like("name", param.getName(),MatchMode.ANYWHERE));
		}
		criteria.add(Restrictions.ge("status", News.STATUS_PRIVATE));
		criteria.addOrder(Order.asc("seqnum"));
		newsService.find(getPaginator());
		return pagin(getPaginator());
	}
	
    @RequestMapping(value="/add",method=RequestMethod.GET)
	public String add() {
		return "basic/news/news-update";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doAdd(@RequestBody News news) {
		
		news.setCreatetime(new Date());
		news.setVisitcount(0);
		news.setUsername(loginuser().getUsername());
		newsService.add(news);
		return result(200, "OK");
	}
	
	@RequestMapping(value="/update/{id}",method=RequestMethod.GET)
	public String update(@PathVariable String id) {
		News news=newsService.get(id);
		attr("news", news);
		return "basic/news/news-update";
	}
	
	@RequestMapping(value="/update/{id}",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doUpdate(@PathVariable String id,@RequestBody News param) {
		
		News news=newsService.get(id);
		news.setName(param.getName());
		news.setContent(param.getContent());
		news.setStatus(param.getStatus());
		news.setGrade(param.getGrade());
		newsService.update(news);
		return result(200, "OK");
	}
	
	@RequestMapping(value="/delete/{id}",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doDelete(@PathVariable String id) {
		newsService.delete(id);
		return result(200, "OK");
	}
	
    @RequestMapping(value="/sort",method=RequestMethod.GET)
   	public String sort() {
   		return "basic/news/news-sort";
   	}
   	
   	@RequestMapping(value="/sort",method=RequestMethod.POST)
   	@ResponseBody
   	public JsonResult doSort(@RequestBody News[] news) {
   		newsService.sort(news);
   		return result(200, "OK");
   	}
   	
   	@RequestMapping(value="/open/{id}",method=RequestMethod.GET)
	public String open(@PathVariable String id) {
		News news=newsService.get(id);
		news.setVisitcount(news.getVisitcount()+1);
		newsService.update(news);
		attr("news", news);
		return "basic/news/news-open";
	}
   	
   	@RequestMapping(value="/homelist",method=RequestMethod.GET)
	public String homelist() {
   		
   		Paginator<News> pagin=this.getPaginator();
   		DetachedCriteria criteria=pagin.getCriteria();
   		criteria.add(Restrictions.eq("status", News.STATUS_PUBLIC));
   		criteria.addOrder(Order.desc("seqnum"));
   		newsService.find(pagin);
   		
		return "basic/news/news-homelist";
	}
   	
  /* 	@RequestMapping(value = "/person", method = RequestMethod.GET)
	public @ResponseBody
	News getNews() {

   		News news = new News();
		news.setName("00000000");
		news.setUsername("222222");
		return news;
	}*/
   	
   	@RequestMapping(value = "/person", method = RequestMethod.POST)
	public @ResponseBody
	Object getNews(@RequestBody String name ) {

   		JSONObject jsonObject = new JSONObject();
		jsonObject.put("newsName", name);
		jsonObject.put("msg", "注册人员信息成功");
		return jsonObject;
	}
   	
	@RequestMapping(value = "/person1", method = RequestMethod.GET)
	public @ResponseBody
	Object getNews1(@RequestBody String name ) {

   		JSONObject jsonObject = new JSONObject();
		jsonObject.put("newsName", name);
		jsonObject.put("msg", "注册人员信息成功");
		return jsonObject;
	}
   	
}
