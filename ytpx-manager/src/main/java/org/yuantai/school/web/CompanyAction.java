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
import org.yuantai.school.pojo.Classes;
import org.yuantai.school.pojo.Company;
import org.yuantai.school.pojo.Student;
import org.yuantai.school.service.CompanyService;
import org.yuantai.school.service.StudentService;
import org.yuantai.system.pojo.OnlineUser;
import org.yuantai.system.service.OnlineUserService;
import org.yuantai.system.web.ModelAction;


/**
 * 单位管理
 * @author zamn
 *
 */
@Controller
@RequestMapping(value="/school/company")
public class CompanyAction extends ModelAction<Company>{
	
	private static final Logger logger = LogManager.getLogger(CompanyAction.class);
	@Autowired private OnlineUserService onlineUserService;
    @Autowired private CompanyService companyService;
    @Autowired private StudentService studentService;
	
    /**
     * 跳到单位列表页面
     */
    @RequestMapping(method=RequestMethod.GET)
	public String list() {
		return "school/company/company-list";
	}
    
    @RequestMapping(value="/query",method=RequestMethod.GET)
    public String query() {
    	return "school/company/company-query";
    }
    
    /**
	 * 查询单位
	 */
    @RequestMapping(value="/query",method=RequestMethod.POST)
	public String query(ServletRequest request, ServletResponse response,Company param) {
    	HttpServletRequest req=(HttpServletRequest)request;
		DetachedCriteria criteria=getPaginator().getCriteria();
		if(!StringUtils.isEmpty(param.getCompanyName())) {
			criteria.add(Restrictions.like("companyName",param.getCompanyName(),MatchMode.ANYWHERE));
		}
		if(!StringUtils.isEmpty(param.getCity())&&!param.getCity().equals("地级市")) {
			criteria.add(Restrictions.eq("city", param.getCity()));
		}
		if(!StringUtils.isEmpty(param.getProvince())) {
			criteria.add(Restrictions.eq("province", param.getProvince()));
		}
		if(!StringUtils.isEmpty(param.getDistrict())&&!param.getDistrict().equals("市、县级市、县")) {
			criteria.add(Restrictions.eq("district", param.getDistrict()));
		}
		OnlineUser currentUser =  onlineUserService.getLoginUser(req.getSession());
		if(currentUser.getSysCode()!=null&&currentUser.getSysCode()!=""){
			criteria.add(Restrictions.eq("sysCode", currentUser.getSysCode()));
		}
		criteria.addOrder(Order.desc("createTime"));
		companyService.find(getPaginator());
		return pagin(getPaginator());
	}
    
    
   
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add() {
		return "school/company/company-add";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doAdd(@RequestBody Company data,ServletRequest request, ServletResponse response) {
		
		HttpServletRequest req=(HttpServletRequest)request;
		OnlineUser currentUser =  onlineUserService.getLoginUser(req.getSession());
		data.setSysCode(currentUser.getSysCode());
		data.setStudentTotal(0);
		data.setRegistCount(0);
		data.setCreateTime(new Date());
		companyService.add(data);
		return result(200, "OK");
	}
/*	public JsonResult doAdd(@RequestBody Company data) {
		
		companyService.add(data);
		return result(200, "OK");
	}
	*/


	@RequestMapping(value="/update/{id}",method=RequestMethod.GET)
	public String update(@PathVariable String id) {
		Company company=companyService.get(id);
		attr("company", company);
		return "school/company/company-update";
	}
	
	@RequestMapping(value="/update/{id}",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doUpdate(@PathVariable String id,@RequestBody Company param) {
		
		Company company=companyService.get(id);
		company.setProvince(param.getProvince());
		company.setCity(param.getCity());
		company.setDistrict(param.getDistrict());
		company.setCompanyName(param.getCompanyName());
		company.setCompanyAddr(param.getCompanyAddr());
		company.setCompanyType(param.getCompanyType());
		company.setContact(param.getContact());
		company.setTelephone(param.getTelephone());
		company.setInvoiceTaken(param.getInvoiceTaken());
		company.setInvoiceType(param.getInvoiceType());
		company.setInvoiceTitle(param.getInvoiceTitle());
		company.setTaxpayerNo(param.getTaxpayerNo());
		company.setBank(param.getBank());
		company.setBankAccount(param.getBankAccount());

		companyService.update(company);
		return result(200, "OK");
	}
	
	@RequestMapping(value="/delete/{id}",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doDelete(@PathVariable String id) {
		Company company= companyService.get(id);
		companyService.delete(id);
		List<Student>list = studentService.queryByCompany(company.getId(), company.getSysCode());
		for(Student student:list){
			studentService.delete(student.getId());
		}
		
		
  	 return result(200, "OK");
	}
    
	
}
