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
import org.yuantai.school.pojo.Registe;
import org.yuantai.school.pojo.Student;
import org.yuantai.school.pojo.Term;
import org.yuantai.school.service.StudentService;
import org.yuantai.school.service.TermService;
import org.yuantai.system.pojo.OnlineUser;
import org.yuantai.system.service.OnlineUserService;
import org.yuantai.system.web.ModelAction;


/**
 * 学校管理
 * @author zamn
 *
 */
@Controller
@RequestMapping(value="/school/term")
public class TermAction extends ModelAction<Term>{
	
	private static final Logger logger = LogManager.getLogger(TermAction.class);
	@Autowired private OnlineUserService onlineUserService;
    @Autowired private TermService termService;
    @Autowired private StudentService studentService;
	
    /**
     * 跳到期数列表页面
     */
    @RequestMapping(method=RequestMethod.GET)
	public String list() {
		return "school/term/term-list";
	}
    
    @RequestMapping(value="/query",method=RequestMethod.GET)
    public String query() {
    	return "school/term/term-query";
    }
    
    /**
	 * 查询期数
	 */
    @RequestMapping(value="/query",method=RequestMethod.POST)
	public String query(ServletRequest request, ServletResponse response,Term param) {
    	HttpServletRequest req=(HttpServletRequest)request;
		DetachedCriteria criteria=getPaginator().getCriteria();
		if(!StringUtils.isEmpty(param.getYears())) {
			criteria.add(Restrictions.eq("years", param.getYears()));
		}	
		OnlineUser currentUser =  onlineUserService.getLoginUser(req.getSession());
		if(currentUser.getSysCode()!=null&&currentUser.getSysCode()!=""){
			criteria.add(Restrictions.eq("sysCode", currentUser.getSysCode()));
		}
		criteria.addOrder(Order.desc("createTime"));
		termService.find(getPaginator());
		List<Term> list =getPaginator().getDataList();
		for(Term term:list){
			int registryCount =studentService.queryStudentCountByterm(term.getTermCode(),currentUser.getSysCode()).size();
			term.setRegistryCount(registryCount);
		}
		getPaginator().setDataList(list);
		return pagin(getPaginator());
	}
    
    
   
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add() {
		return "school/term/term-add";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doAdd(@RequestBody Term term,ServletRequest request, ServletResponse response) {
	 	HttpServletRequest req=(HttpServletRequest)request;
	 	OnlineUser currentUser =  onlineUserService.getLoginUser(req.getSession());
	 	int termCount=0;
	 	String termCode=null;
	 	if(currentUser.getSysCode()!=null){
	 		List<Term> list = termService.queryBySysYear(currentUser.getSysCode(),term.getYears());
	 		termCount= list.size();
	 		termCode = ((term.getYears()).substring(2, term.getYears().length()))+String.format("%02d", termCount+1);//YY+01
	 		 if(termCount>0){
	 			 String lastCode=list.get(0).getTermCode();
	 			termCode=Integer.parseInt(lastCode)+1+"";//自增
	 		 }
	 	}

	 	term.setTermCode(termCode);
	 	term.setSysCode(currentUser.getSysCode());
	 	term.setClassCount(0);
	 	term.setRegistryCount(0);
	 	term.setCreateTime(new Date());
		termService.add(term);
		return result(200, "OK");
	}
	
	/**
	 * 检查期数是否存在
	 * @param term
	 * @return code:200 表示不存在,code:-1 表示已存在
	 */
	@RequestMapping(value="/check_term",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult checkTerm(String term,ServletRequest request, ServletResponse response) {
		HttpServletRequest req=(HttpServletRequest)request;
	 	OnlineUser currentUser =  onlineUserService.getLoginUser(req.getSession());
	 	if(currentUser.getSysCode()!=null&&currentUser.getSysCode()!=""){
		boolean exist=!termService.check(term, currentUser.getSysCode()).isEmpty();
		if(exist) {
			return result(-1, "already exists");
		} else {
			return result(200, "not exist");
		}	 
		}else {
			return result(200, "not exist");//超级管理员不做校验
		}
	}

	@RequestMapping(value="/update/{id}",method=RequestMethod.GET)
	public String update(@PathVariable String id) {
		Term term=termService.get(id);
		attr("term", term);
		return "school/term/term-update";
	}
	
	@RequestMapping(value="/update/{id}",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doUpdate(@PathVariable String id,@RequestBody Term param,ServletRequest request, ServletResponse response) {
		HttpServletRequest req=(HttpServletRequest)request;
	 	OnlineUser currentUser =  onlineUserService.getLoginUser(req.getSession());
	
		Term term=termService.get(id);
	 	term.setSysCode(currentUser.getSysCode());
	 	term.setBeginTime(param.getBeginTime());
	 	term.setDuration(param.getDuration());
	 	term.setSpecialty(param.getSpecialty());
	 	term.setYears(param.getYears());
	 	term.setTerm(param.getTerm());
	 	term.setPlanCount(param.getPlanCount());
		termService.update(term);
		return result(200, "OK");
	}
	
	@RequestMapping(value="/change/{id}",method=RequestMethod.GET)
	public String change(@PathVariable String id,ServletRequest request, ServletResponse response) {
		Term term=termService.get(id);
		HttpServletRequest req=(HttpServletRequest)request;
		OnlineUser currentUser =  onlineUserService.getLoginUser(req.getSession());
		List<Term> terms=null;
		if(currentUser.getSysCode()!=null){
			terms =termService.checkBySysCode(currentUser.getSysCode());
		}else{
			terms =termService.findAll();
		}
		attr("term", term);
		attr("terms", terms);
		return "school/term/term-change";
	}
	
	@RequestMapping(value="/change/{id}",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doChange(@PathVariable String id,@RequestBody Term param,ServletRequest request, ServletResponse response) {
		HttpServletRequest req=(HttpServletRequest)request;
		OnlineUser currentUser =  onlineUserService.getLoginUser(req.getSession());
		Term termOld =termService.get(id);
		List<Student> students =studentService.checkByTerm(termOld.getTermCode(),currentUser.getSysCode());
		Term term=  termService.get(param.getId());
		Integer registeCount = term.getRegistryCount();
		if(registeCount+students.size()>term.getPlanCount()){
			return result(-1, "超出改期期数人数已满！");
		}
		
		term.setRegistryCount(registeCount+students.size());
		termService.update(term);
		
		termOld.setRegistryCount(termOld.getRegistryCount()-students.size());
		termService.update(termOld);
		
		for(Student student:students){
			student.setTermCode(term.getTermCode());
			studentService.update(student);
		}
		
		return result(200, "OK");
	}
	
	@RequestMapping(value="/delete/{id}",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doDelete(@PathVariable String id) {
		if(termService.get(id).getClassCount()>0){
			 return result(-1, "is used");
		}else {
			termService.delete(id);
			
		  	 return result(200, "OK");
		}
		
	}
    
	
}
