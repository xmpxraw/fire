package org.yuantai.school.web;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import org.yuantai.school.pojo.Classes;
import org.yuantai.school.pojo.Company;
import org.yuantai.school.pojo.FeesScheme;
import org.yuantai.school.pojo.Registe;
import org.yuantai.school.pojo.Student;
import org.yuantai.school.pojo.StudentScore;
import org.yuantai.school.pojo.Term;
import org.yuantai.school.service.ClassesService;
import org.yuantai.school.service.CompanyService;
import org.yuantai.school.service.FeesSchemeService;
import org.yuantai.school.service.RegisteService;
import org.yuantai.school.service.StudentScoreService;
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
@RequestMapping(value="/school/student")
public class StudentAction extends ModelAction<Student>{
	
	private static final Logger logger = LogManager.getLogger(StudentAction.class);
	@Autowired private OnlineUserService onlineUserService;
    @Autowired private StudentService studentService;
    @Autowired private ClassesService classesService;
    @Autowired private CompanyService companyService;
    @Autowired private RegisteService registeService;
    @Autowired private TermService termService;
    @Autowired private FeesSchemeService feesSchemeService;
    @Autowired private StudentScoreService studentScoreService;

	
    /**
     * 跳到学员列表页面
     */
    @RequestMapping(method=RequestMethod.GET)
	public String list() {
		return "school/student/student-list";
	}
    
    /**
     * 跳到学员列表页面--财务部看的页面
     */
    @RequestMapping(value="/listForGA",method=RequestMethod.GET)
	public String listForGA() {
		return "school/student/student-list2";
	}
    
    @RequestMapping(value="/query",method=RequestMethod.GET)
    public String query(ServletRequest request, ServletResponse response) {
    	HttpServletRequest req=(HttpServletRequest)request;
    	OnlineUser currentUser =  onlineUserService.getLoginUser(req.getSession());
    	List<Classes> classes =null;
    	List<Company> companys=null;
    	if(currentUser.getSysCode()!=null){
    		classes= classesService.checkBySysCode(currentUser.getSysCode());
    		companys= companyService.checkBySysCode(currentUser.getSysCode());
    	}else{
    		classes=classesService.findAll();
    		companys=companyService.findAll();
    	}
    	attr("classes", classes);
    	attr("companys", companys);
    	return "school/student/student-query";
    }
    
    /**
	 * 查询学员
	 */
    @RequestMapping(value="/query",method=RequestMethod.POST)
	public String query(ServletRequest request, ServletResponse response,Student param) {
    	HttpServletRequest req=(HttpServletRequest)request;
		DetachedCriteria criteria=getPaginator().getCriteria();
		if(!StringUtils.isEmpty(param.getStudnetName())) {
			criteria.add(Restrictions.like("studnetName",param.getStudnetName(),MatchMode.ANYWHERE));
		}
		if(!StringUtils.isEmpty(param.getIdcard())) {
			criteria.add(Restrictions.eq("idcard", param.getIdcard()));
		}if(param.getRegistTime()!=null) {
			criteria.add(Restrictions.eq("registTime", param.getRegistTime()));
		}if(param.getMobile()!=null) {
			criteria.add(Restrictions.like("mobile",param.getMobile(),MatchMode.ANYWHERE));
		}
		if(!StringUtils.isEmpty(param.getClassCode())) {
			criteria.add(Restrictions.eq("classCode", param.getClassCode()));
		}if(!StringUtils.isEmpty(param.getCompanyCode())) {
			criteria.add(Restrictions.eq("companyCode", param.getCompanyCode()));
		}
		OnlineUser currentUser =  onlineUserService.getLoginUser(req.getSession());
		if(currentUser.getSysCode()!=null&&currentUser.getSysCode()!=""){
			criteria.add(Restrictions.eq("sysCode", currentUser.getSysCode()));
		}
		criteria.addOrder(Order.desc("createTime"));
		criteria.addOrder(Order.desc("registTime"));
		studentService.find(getPaginator());
		List<String>idcrads=new ArrayList<>();
		List<Student> list =getPaginator().getDataList();
		List<Student> delList = new ArrayList<>();
		for(Student student:list){		
			if(idcrads.contains(student.getIdcard())){
				delList.add(student);
			}else{
				idcrads.add(student.getIdcard());
			}
			
			if(student.getClassCode()!=null){
				student.setClassName(classesService.getClassesByCode(student.getClassCode()).getClassName());
			}
			if(student.getCompanyCode()!=null){
				student.setCompanyName(companyService.get(student.getCompanyCode()).getCompanyName());
			}	
		}
		list.removeAll(delList);
		getPaginator().setDataList(list);
		return pagin(getPaginator());
	}
    
    
   
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add() {
		return "school/student/student-add";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doAdd(@RequestBody Student data,ServletRequest request, ServletResponse response) {
		
		HttpServletRequest req=(HttpServletRequest)request;
		OnlineUser currentUser =  onlineUserService.getLoginUser(req.getSession());
		data.setSysCode(currentUser.getSysCode());
		studentService.add(data);
		return result(200, "OK");
	}
	
	@RequestMapping(value="/query/{id}",method=RequestMethod.GET)
	public String query(@PathVariable String id,ServletRequest request, ServletResponse response) {
		HttpServletRequest req=(HttpServletRequest)request;
    	OnlineUser currentUser =  onlineUserService.getLoginUser(req.getSession());
		List<Student> students=studentService.checkRegiste(currentUser.getSysCode(),id);
		attr("students", students);
		attr("id", id);
		return "school/student/student-registe";
	}
	
	 @RequestMapping(value="/query/{id}",method=RequestMethod.POST)
		public String queryRegiste(@PathVariable String id,ServletRequest request, ServletResponse response) {
		 HttpServletRequest req=(HttpServletRequest)request;
	    	OnlineUser currentUser =  onlineUserService.getLoginUser(req.getSession());
		 	String registeCode=registeService.get(id).getRegisteCode();
			List<Student> list =studentService.checkRegiste(currentUser.getSysCode(),registeCode);
			for(Student student:list){
				if(student.getClassCode()!=null){
					student.setClassName(classesService.getClassesByCode(student.getClassCode()).getClassName());
				}
				if(student.getCompanyCode()!=null){
					student.setCompanyName(companyService.get(student.getCompanyCode()).getCompanyName());
				}	
			}
			getPaginator().setDataList(list);
			return pagin(getPaginator());
		}
	 
		@RequestMapping(value="/viewStudent/{id}",method=RequestMethod.GET)
		public String viewStudent(@PathVariable String id,ServletRequest request, ServletResponse response) {
			 HttpServletRequest req=(HttpServletRequest)request;
		    	OnlineUser currentUser =  onlineUserService.getLoginUser(req.getSession());
			List<Student> students=studentService.checkClass(currentUser.getSysCode(),id);
			attr("students", students);
			attr("id", id);
			return "school/student/student-classes";
		}
	
	@RequestMapping(value="/viewDetail/{id}",method=RequestMethod.GET)
	public String viewDetail(@PathVariable String id,ServletRequest request, ServletResponse response) {
		 HttpServletRequest req=(HttpServletRequest)request;
	    	OnlineUser currentUser =  onlineUserService.getLoginUser(req.getSession());
		Student student=studentService.get(id);
		String idcard =student.getIdcard();
		List<StudentScore> studentScores=null;
		if(currentUser.getSysCode()!=null){
			 studentScores =studentScoreService.checkIdcard(idcard, currentUser.getSysCode());
		}else {
			studentScores=studentScoreService.findAll();
		}
		String payScheme =feesSchemeService.get(student.getPayScheme()).getSchemeName();
		String company =companyService.get(student.getCompanyCode()).getCompanyName();
		String payStatus=null;
		String status=null;
		if(student.getPayStatus().equals("0")){payStatus="未缴费";}
		if(student.getPayStatus().equals("1")){payStatus="已缴费";}
		if(student.getStudentStatus().equals(0)){status="已报名";}
		if(student.getStudentStatus().equals(1)){status="学习中";}
		if(student.getStudentStatus().equals(2)){status="通过考试";}
		if(student.getStudentStatus().equals(3)){status="待复考";}
		if(student.getStudentStatus().equals(4)){status="需复读";}
		if(student.getStudentStatus().equals(5)){status="国考合格";}
		if(student.getStudentStatus().equals(6)){status="国考不合格";}
		if(student.getStudentStatus().equals(8)){status="已领证";}
		if(student.getStudentStatus().equals(8)){status="已退学";}
		attr("payScheme", payScheme);
		attr("company", company);
		attr("student", student);
		attr("payStatus", payStatus);
		attr("status", status);
		attr("studentScores", studentScores);
		return "school/student/student-detail";
	}
	
	 @RequestMapping(value="/viewStudent/{id}",method=RequestMethod.POST)
		public String view(@PathVariable String id,ServletRequest request, ServletResponse response) {
		 HttpServletRequest req=(HttpServletRequest)request;
	    	OnlineUser currentUser =  onlineUserService.getLoginUser(req.getSession());
		 	String classCode=classesService.get(id).getClassCode();
			List<Student> list =studentService.checkClass(currentUser.getSysCode(),classCode);
			for(Student student:list){
				if(student.getClassCode()!=null){
					Classes classes=classesService.getClassesByCode(student.getClassCode());
					
					student.setClassName(classes.getClassName());
				}
				if(student.getCompanyCode()!=null){
					student.setCompanyName(companyService.get(student.getCompanyCode()).getCompanyName());
				}	
			}
			getPaginator().setDataList(list);
			return pagin(getPaginator());
		}

	@RequestMapping(value="/update/{id}",method=RequestMethod.GET)
	public String update(@PathVariable String id) {
		Student student=studentService.get(id);
		attr("student", student);
		attr("id", id);
		return "school/student/student-update";
	}
	
	@RequestMapping(value="/update/{id}",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doUpdate(@PathVariable String id,@RequestBody Student param) {
		
		Student student=studentService.get(id);
		student.setStudnetName(param.getStudnetName());
		student.setMobile(param.getMobile());
		student.setIdcard(param.getIdcard());
		if(param.getWeixinOpenid()!=null){
			student.setWeixinOpenid(param.getWeixinOpenid());
		}
		studentService.update(student);
		return result(200, "OK");
	}
	
	@RequestMapping(value="/changeTerm/{id}",method=RequestMethod.GET)
	public String changeTerm(@PathVariable String id,ServletRequest request, ServletResponse response) {
		 HttpServletRequest req=(HttpServletRequest)request;
	    OnlineUser currentUser =  onlineUserService.getLoginUser(req.getSession());
		Student student=studentService.get(id);
		student.setTermName(termService.checkTermCode(student.getTermCode(), currentUser.getSysCode()).get(0).getTerm());
		List<Term> terms=null;
		if(currentUser.getSysCode()!=null){
			terms =termService.checkBySysCode(currentUser.getSysCode());
		}else{
			terms =termService.findAll();
		}
		attr("student", student);
		attr("terms", terms);
		return "school/student/student-changeTerm";
	}
	
	@RequestMapping(value="/changeTerm/{id}",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doChangeTerm(@PathVariable String id,@RequestBody Student param,ServletRequest request, ServletResponse response) {
		 HttpServletRequest req=(HttpServletRequest)request;
		    OnlineUser currentUser =  onlineUserService.getLoginUser(req.getSession());
		Term term=  termService.checkTermCode(param.getTermCode(), currentUser.getSysCode()).get(0);
		Integer registeCount = term.getRegistryCount();
		if(registeCount+1>term.getPlanCount()){
			return result(-1, "期数人数已满！");
		}
		term.setRegistryCount(registeCount+1);
		termService.update(term);
		Student student=studentService.get(id);
		Term termOld= termService.checkTermCode(student.getTermCode(), currentUser.getSysCode()).get(0);
		//Term termOld=termService.get(student.getTermCode());
		termOld.setRegistryCount(termOld.getRegistryCount()-1);//原来期数人数减一
		termService.update(termOld);
		student.setTermCode(param.getTermCode());
		studentService.update(student);		

		return result(200, "OK");
	}
	
	@RequestMapping(value="/pay/{id}",method=RequestMethod.GET)
	public String pay(@PathVariable String id) {
		Student student=studentService.get(id);
		student.setCompanyName(companyService.get(student.getCompanyCode()).getCompanyName());
		student.setSchemeName(feesSchemeService.get(student.getPayScheme()).getSchemeName());
		FeesScheme feesScheme=feesSchemeService.get(student.getPayScheme());
		student.setFeesHotel(feesScheme.getFeesHotel());
		student.setFeesMeal(feesScheme.getFeesMeal());
		student.setFeesStudy(feesScheme.getFeesStudy());
		student.setFeesTotal(feesScheme.getFeesTotal());
		student.setFeesTrain(feesScheme.getFeesTrain());
		attr("student", student);
		return "school/student/student-pay";
	}
	
	@RequestMapping(value="/pay/{id}",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doPay(@PathVariable String id,@RequestBody Student param) {	
		Student student=studentService.get(id);
			
			student.setPayStatus("1");
			student.setFeesHotel(param.getFeesHotel());
			student.setFeesMeal(param.getFeesMeal());
			student.setFeesStudy(param.getFeesStudy());
			student.setFeesTrain(param.getFeesTrain());
			student.setFeesTotal(param.getFeesTotal());
			student.setPayShould(param.getPayShould());
			student.setPayReal(param.getPayReal());
			student.setPayType(param.getPayType());//
			student.setPayShould(param.getFeesTotal());
			student.setPayReal(param.getFeesTotal());
		
			Registe registe= registeService.getRegisteByName(param.getRegisteCode());
			int registed=studentService.checkRegisteStatus(param.getRegisteCode()).size();//已经缴费的
			if((registe.getRegisteCount()-registed)==1){
				registe.setPayStatus(1);//状态修改为已缴费
			}else{
				registe.setPayStatus(2);//学员缴费、为部分缴费
			}
			if(registe.getPayAlready()!=null){
				BigDecimal payAlready= registe.getPayAlready();
				registe.setPayAlready(payAlready.add(param.getFeesTotal()));
			}else{
				registe.setPayAlready(param.getFeesTotal());
			}
			registe.setPayType("学员缴费");
			studentService.update(student);
			registeService.update(registe);
	//	}
		return result(200, "OK");
	}
	
	@RequestMapping(value="/delete/{id}",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doDelete(@PathVariable String id) {
		Student student = studentService.get(id);
		List<Student>students = studentService.checkIdcard(student.getIdcard(), student.getSysCode());
		for(Student s : students){
			studentService.delete(s.getId());
		}
		
		
  	 return result(200, "OK");
  	 
	}
	//退学
	@RequestMapping(value="/terminate/{id}",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doTerminate(@PathVariable String id) {
		
	Student student=studentService.get(id);
	student.setStudentStatus(9);
	student.setTerminate(1);//退学
	studentService.update(student);
  	 return result(200, "OK"); 
	}
	
	//b'y领证
	@RequestMapping(value="/graduate/{id}",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doGraduate(@PathVariable String id) {
		
		Student student=studentService.get(id);
		student.setStudentStatus(8);
		student.setGraduate(1);
		studentService.update(student);
  	 return result(200, "OK");
  	 
	}
}
