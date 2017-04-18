package org.yuantai.basic.web;

import java.util.Date;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yuantai.basic.pojo.Changelog;
import org.yuantai.basic.service.ChangelogService;
import org.yuantai.common.JsonResult;
import org.yuantai.common.Paginator;
import org.yuantai.system.web.ModelAction;

@Controller
@RequestMapping("/basic/changelog")
public class ChangelogAction extends ModelAction<Changelog> {

	@Autowired private ChangelogService changelogService;

	/**
	 * 关于我们
	 * @return
	 */
	@RequestMapping(value="/aboutme",method=RequestMethod.GET)
    public String aboutme() {
		
		Map<String, Changelog> logmap=changelogService.findForAboutus();
		attr("logmap",logmap);
    	return "basic/changelog/changelog-aboutme";
    }
	
	@RequestMapping(method=RequestMethod.GET)
	public String list() {
		return "basic/changelog/changelog-list";
	}
    
	@RequestMapping(value="/query",method=RequestMethod.POST)
	public String query(String keyword) {
		
		Paginator<Changelog> pagin=this.getPaginator();
		DetachedCriteria criteria=pagin.getCriteria();
		criteria.add(Restrictions.eq("status", Changelog.STATUS_DEFAULT));
		criteria.addOrder(Order.asc("createtime"));
		changelogService.find(pagin);
		
		return pagin(getPaginator());
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public @ResponseBody JsonResult get(@PathVariable String id) {
		Changelog changelog=changelogService.get(id);
		return result(200,"OK",changelog);
	}
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
    public String add() {
    	return "basic/changelog/changelog-update";
    }
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public @ResponseBody JsonResult add(@RequestBody Changelog changelog) {
		
		changelog.setStatus(Changelog.STATUS_DEFAULT);
		changelog.setCreatetime(new Date());
		changelog.setUsername(loginuser().getUsername());
		changelogService.add(changelog);
		return result(200,"OK");
	}
	
	@RequestMapping(value="/update/{id}",method=RequestMethod.GET)
    public String update(@PathVariable String id) {
    	Changelog changelog=changelogService.get(id);
    	attr("changelog",changelog);
    	return "basic/changelog/changelog-update";
    }
	
	@RequestMapping(value="/update/{id}",method=RequestMethod.POST)
	public @ResponseBody JsonResult update(@PathVariable String id,@RequestBody Changelog param) {
		
		Changelog changelog=changelogService.get(id);
		changelog.setName(param.getName());
		changelog.setContent(param.getContent());
		changelogService.update(changelog);
		return result(200,"OK");
	}
	
	@RequestMapping(value="/delete/{id}",method=RequestMethod.POST)
	public @ResponseBody JsonResult delete(@PathVariable String id) {
		changelogService.delete(id);
		return result(200,"OK");
	}

}
