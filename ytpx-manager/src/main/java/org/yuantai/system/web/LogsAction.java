package org.yuantai.system.web;

import java.util.Date;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.yuantai.common.util.DateUtil;
import org.yuantai.common.util.StringUtil;
import org.yuantai.system.pojo.Logs;
import org.yuantai.system.service.LogsService;

@Controller
@RequestMapping(value="/system/logs")
public class LogsAction extends ModelAction<Logs> {
	
	@Autowired private LogsService logsService;
	
	/**
     * 跳到日志列表页面
     */
    @RequestMapping(method=RequestMethod.GET)
	public String list() {
		return "system/logs/logs-list";
	}
    
    @RequestMapping(value="/query",method=RequestMethod.GET)
    public String query() {
    	return "system/logs/logs-query";
    }
    
    @RequestMapping(value="/query",method=RequestMethod.POST)
	public String query(Logs param) {
		
		DetachedCriteria criteria=getPaginator().getCriteria();
		if(!StringUtil.isEmpty(param.getUsername())) {
			criteria.add(Restrictions.like("username", param.getUsername(),MatchMode.ANYWHERE));
		}
		if(!StringUtil.isEmpty(param.getHost())) {
			criteria.add(Restrictions.like("host", param.getHost(),MatchMode.ANYWHERE));
		}
		if(!StringUtil.isEmpty(param.getModule())) {
			criteria.add(Restrictions.like("module", param.getModule(),MatchMode.ANYWHERE));
		}
		if(!StringUtil.isEmpty(param.getEntity())) {
			criteria.add(Restrictions.like("entity", param.getEntity(),MatchMode.ANYWHERE));
		}
		if(!StringUtil.isEmpty(param.getType())) {
			criteria.add(Restrictions.eq("type", param.getType()));
		}
		if(!StringUtil.isEmpty(param("fromDate"))) {
			Date fromDate=DateUtil.parse(param("fromDate"), "yyyy-MM-dd");
			criteria.add(Restrictions.ge("createTime", fromDate));
		}
		if(!StringUtil.isEmpty(param("toDate"))) {
			Date toDate=DateUtil.parse(param("toDate")+" 23:59:59", "yyyy-MM-dd HH:mm:ss");
			criteria.add(Restrictions.le("createTime", toDate));
		}
		
		criteria.addOrder(Order.desc("createTime"));
		logsService.find(getPaginator());
		return pagin(getPaginator());
	}
    
    @RequestMapping(value="/view/{id}",method=RequestMethod.GET)
	public String view(@PathVariable String id) {
		Logs logs=logsService.get(id);
		attr("logs", logs);
		return "system/logs/logs-view";
	}
}
