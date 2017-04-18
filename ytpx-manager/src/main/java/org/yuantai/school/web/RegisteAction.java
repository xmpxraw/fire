package org.yuantai.school.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.yuantai.common.JsonResult;
import org.yuantai.common.util.DateUtil;
import org.yuantai.common.util.REValidateUtil;
import org.yuantai.school.pojo.Company;
import org.yuantai.school.pojo.FeesScheme;
import org.yuantai.school.pojo.Registe;
import org.yuantai.school.pojo.School;
import org.yuantai.school.pojo.Student;
import org.yuantai.school.pojo.Term;
import org.yuantai.school.service.CompanyService;
import org.yuantai.school.service.FeesSchemeService;
import org.yuantai.school.service.RegisteService;
import org.yuantai.school.service.SchoolService;
import org.yuantai.school.service.StudentService;
import org.yuantai.school.service.TermService;
import org.yuantai.system.pojo.OnlineUser;
import org.yuantai.system.service.OnlineUserService;
import org.yuantai.system.web.ModelAction;


/**
 * 报名管理
 * @author zamn
 *
 */
@Controller
@RequestMapping(value="/school/registe")
public class RegisteAction extends ModelAction<Registe>{
	
	private static final Logger logger = LogManager.getLogger(RegisteAction.class);
	@Autowired private OnlineUserService onlineUserService;
    @Autowired private RegisteService registeService;
    @Autowired private CompanyService companyService;
    @Autowired private FeesSchemeService feesSchemeService;
    @Autowired private StudentService studentService;
    @Autowired private SchoolService schoolService;
    @Autowired private TermService termService;
    @Value("${school.student.uploaddir}")
	private String studentUploadDir;

    
    /**
     * 跳到报名列表页面
     */
    @RequestMapping(method=RequestMethod.GET)
	public String list() {
		return "school/registe/registe-list";
	}
    
    /**
     * 跳到报名列表页面--财务专用
     */
    @RequestMapping(value="/listForGA",method=RequestMethod.GET)
	public String listForGA() {
		return "school/registe/registe-list2";
	}
    
    @RequestMapping(value="/query",method=RequestMethod.GET)
    public String query() {
    	return "school/registe/registe-query";
    }
    
    /**
	 * 查询报名
	 */
    @RequestMapping(value="/query",method=RequestMethod.POST)
	public String query(ServletRequest request, ServletResponse response,Registe param) {
    	HttpServletRequest req=(HttpServletRequest)request;
		DetachedCriteria criteria=getPaginator().getCriteria();
		OnlineUser currentUser =  onlineUserService.getLoginUser(req.getSession());
		if(param.getPayStatus()!=null) {
			criteria.add(Restrictions.eq("payStatus", param.getPayStatus()));
		}
		
		if(!StringUtils.isEmpty(param.getPayType())) {
			criteria.add(Restrictions.like("payType", param.getPayType(),MatchMode.ANYWHERE));
		}
		if(!StringUtils.isEmpty(param.getCompanyName())) {
			List<String> values= companyService.likeCompanyName(param.getCompanyName(), currentUser.getSysCode());
			criteria.add(Restrictions.in("companyCode", values));
		}
		
		if(currentUser.getSysCode()!=null&&currentUser.getSysCode()!=""){
			criteria.add(Restrictions.eq("sysCode", currentUser.getSysCode()));
		}
		criteria.addOrder(Order.desc("createTime"));	
		criteria.addOrder(Order.desc("registeTime"));	
		registeService.find(getPaginator());
		List<Registe> list =getPaginator().getDataList();
		for(Registe registe:list){
			registe.setSchemeName(feesSchemeService.get(registe.getPayScheme()).getSchemeName());
			registe.setCompanyName(companyService.get(registe.getCompanyCode()).getCompanyName());
		}
		getPaginator().setDataList(list);
		return pagin(getPaginator());
	}
    
    
   
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(ServletRequest request, ServletResponse response) {
		HttpServletRequest req=(HttpServletRequest)request;
	 	OnlineUser currentUser =  onlineUserService.getLoginUser(req.getSession());
		 	List<Company> companys=null;
		 	List<FeesScheme> feesSchemes=null;
		 	List<Term> terms =null;
		 	if(currentUser.getSysCode()!=null&&currentUser.getSysCode()!=""){
				companys = companyService.checkBySysCode(currentUser.getSysCode());
				 feesSchemes= feesSchemeService.check(1, currentUser.getSysCode());
				 terms = termService.checkBySysCode(currentUser.getSysCode());
				 String dateStr=String.valueOf(new Date().getYear()).substring(1, 3)+String.valueOf(new Date().getMonth()+1)+"01";
				 String registCode=null;
				 int registeCount=0;
				 List<Registe>list=registeService.queryByDate(currentUser.getSysCode(), "%"+dateStr+"%");
				 registeCount=list.size();
				 registCode=dateStr+String.format("%03d", registeCount+1);
				 if(registeCount>0){
					 String lastCode=list.get(0).getRegisteCode();
					 registCode=Integer.parseInt(lastCode)+1+"";//自增
				 }
				 attr("registCode",registCode);
			}else {
				companys = companyService.findAll();
				 feesSchemes= feesSchemeService.check(1);
				 terms=termService.findAll();
			}
		attr("companys", companys);	
		attr("fees", feesSchemes);
		attr("terms", terms);
		return "school/registe/registe-add";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doAdd(Registe data,MultipartFile file,ServletRequest request, ServletResponse response) {
		HttpServletRequest req=(HttpServletRequest)request;
		OnlineUser currentUser =  onlineUserService.getLoginUser(req.getSession());
		data.setSysCode(currentUser.getSysCode());
		Integer code =200;
		String msg ="OK";
		try {
			File parentFile=new File(studentUploadDir);
			if(!parentFile.exists()) parentFile.mkdirs();
			
			File uploadfile=new File(parentFile,data.getRegisteCode()+"."+DateUtil.getCurrentDateStr("yyyyMMddHHmmss"));
			file.transferTo(uploadfile);
			data.setTemplate(uploadfile.getName());
			InputStream is =null;
			XSSFWorkbook xWorkbook = null;
			is = new FileInputStream(uploadfile);
			xWorkbook = new XSSFWorkbook(is);
			// 第一个工作表
			XSSFSheet xSheet = xWorkbook.getSheetAt(0);
		      // getPhysicalNumberOfRows行的总数
		      int length = xSheet.getPhysicalNumberOfRows();//导入excel表需要带表头
		      if(data.getRegisteCount()!=length-1){
		    	  code=-1;
	    		  msg="学员数跟导入的学员数不一致!";
		      }
		      if(code!=-1){	    	  
		    	  List<String> list = new ArrayList<String>();		    	 
			      for(int i=1;i<length;i++){//第一行为表头、不保存
		    	  XSSFRow  row1 = xSheet.getRow(i);			
		    	  row1.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
		    	  row1.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
		    	  if(!studentService.checkMobile(row1.getCell(4).toString(), currentUser.getSysCode()).isEmpty()){//校验手机是否与数据库重复记录
		    		  code=-1;
		    		  msg=row1.getCell(4).toString()+" 手机号码已经存在";
						break;
					}else if(row1.getCell(4).toString().startsWith("170")){
						  code=-1;
			    		  msg="手机号码不能为170开头："+row1.getCell(4).toString();
						  break;
					}else if(!REValidateUtil.isChinaPhoneLegal(row1.getCell(4).toString())){
						  code=-1;
			    		  msg="手机号码不合法："+row1.getCell(4).toString();
						  break;
					}else if(!REValidateUtil.isIdCardLegal(row1.getCell(3).toString())){
						  code=-1;
			    		  msg="身份证号不合法："+row1.getCell(3).toString();
						  break;
					}else if(!studentService.checkIdcard(row1.getCell(3).toString(), currentUser.getSysCode()).isEmpty()){//校验身份证是否与数据库重复记录
						  code=-1;
						  msg=row1.getCell(3).toString()+"身份证已经存在";
						  break;
					}else if(list.contains(row1.getCell(3).toString())){//校验身份证是否与数据库重复记录
						  code=-1;
						  msg=row1.getCell(3).toString()+"身份证重复";
						  break;
					}else if(list.contains(row1.getCell(4).toString())){//校验身份证是否与数据库重复记录
						  code=-1;
						  msg=row1.getCell(4).toString()+"手机号码重复";
						  break;
					}else {
						list.add(row1.getCell(3).toString());
						list.add(row1.getCell(4).toString());
					}
		      }} 
		       if(code!=-1){		    	   
			      for(int i=1;i<length;i++){
			    	  XSSFRow  row = xSheet.getRow(i);					    	  
						Student student=new Student();				
						student.setStudnetName(row.getCell(0).toString());
						student.setSex(row.getCell(1).toString());
						row.getCell(2).setCellType(Cell.CELL_TYPE_NUMERIC);
						if(row.getCell(2)!=null&&!row.getCell(2).equals("")&&row.getCell(2).getCellType() !=HSSFCell.CELL_TYPE_BLANK){
						student.setAge(row.getCell(2).CELL_TYPE_NUMERIC);
						}
						row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
						student.setIdcard(row.getCell(3).toString());
						row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
						student.setMobile(row.getCell(4).toString());
						if(row.getCell(5)!=null&&!row.getCell(5).equals("")&&row.getCell(5).getCellType() !=HSSFCell.CELL_TYPE_BLANK){
						student.setEducationLevel(row.getCell(5).toString());
						}
						if(row.getCell(6)!=null&&!row.getCell(6).equals("")&&row.getCell(6).getCellType() !=HSSFCell.CELL_TYPE_BLANK){
						student.setExpiry(row.getCell(6).toString());
						}				
						student.setTermCode(data.getTermCode());
						student.setSysCode(currentUser.getSysCode());
						student.setRegisteCode(data.getRegisteCode());
						student.setRegistTime(new Date());
						student.setCreateTime(new Date());
						student.setCompanyCode(data.getCompanyCode());
						student.setRegistType("单位报名");
						student.setPayStatus("0");//报名完默认未缴费、需要通过才确认缴费
						student.setStudentStatus(0);//0：已报名,1:学习中,2:通过考试,3:待复考,4:需复考，5国考合格，6：国考不合格
						BigDecimal total= feesSchemeService.get(data.getPayScheme()).getFeesTotal();//应缴总费用
						student.setPayShould(total);
						student.setPayReal(total);
						student.setPayScheme(data.getPayScheme());
						studentService.add(student);
			      } 
	    	   
	       }
		  if(code!=-1){	
			  if(data.getPayDelay()==null){
				  data.setPayDelay("0");
			  }
			BigDecimal total= feesSchemeService.get(data.getPayScheme()).getFeesTotal();
			data.setRegisteTime(new Date());
			data.setRegisteType("单位报名");
			data.setPayStatus(0);//默认未缴费
			BigDecimal registes=new BigDecimal(data.getRegisteCount());
			data.setPayShould(total.multiply(registes));
			data.setPayReal(total.multiply(registes));
			data.setPayAlready(new BigDecimal(0));
			data.setCompanyCode(data.getCompanyCode());
			data.setCreateTime(new Date());
			registeService.add(data);
			if(currentUser.getSysCode()!=null){
			School school=	schoolService.checkBySysCode(currentUser.getSysCode()).get(0);
			school.setTotal(school.getTotal()+data.getRegisteCount());
			school.setCurrentTotal(school.getCurrentTotal()+data.getRegisteCount());//更新学校学员数
				schoolService.update(school);
			}
			Company company =companyService.get(data.getCompanyCode());
			int registCount = company.getRegistCount();
			int studentCount =company.getStudentTotal();
			company.setRegistCount(registCount+1);
			company.setStudentTotal(studentCount+length-1);
			companyService.update(company);//更新单位报名人数
		  }
		} catch (IllegalStateException | IOException e) {
			return result(-1,"添加报名失败");
		}
		
		return result(code, msg);
	}
	


	@RequestMapping(value="/update/{id}",method=RequestMethod.GET)
	public String update(@PathVariable String id) {
		Registe registe=registeService.get(id);
		attr("registe", registe);
		return "school/registe/registe-update";
	}
	
	@RequestMapping(value="/discounte/{id}",method=RequestMethod.GET)
	public String discounte(@PathVariable String id) {
		Registe registe=registeService.get(id);
		registe.setCompanyName(companyService.get(registe.getCompanyCode()).getCompanyName());
		registe.setSchemeName(feesSchemeService.get(registe.getPayScheme()).getSchemeName());
		attr("registe", registe);
		return "school/registe/registe-discounte";
	}
	
	@RequestMapping(value="/discounte/{id}",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doDiscounte(@PathVariable String id,@RequestBody Registe param,ServletRequest request, ServletResponse response) {
		HttpServletRequest req=(HttpServletRequest)request;
		OnlineUser currentUser =  onlineUserService.getLoginUser(req.getSession());
		Registe registe=registeService.get(id);
		registe.setRegisteCount(param.getRegisteCount());
		registe.setPayShould(param.getPayShould());
		registe.setDiscountType(param.getDiscountType());
		registe.setDiscount(param.getDiscount());
		registe.setPayReal(param.getPayReal());
		registeService.update(registe);
		List<Student> students =studentService.checkRegiste(currentUser.getSysCode(),param.getRegisteCode());
		for(Student student : students){
			if(param.getDiscountType().equals("1")){
				student.setPayReal(student.getPayShould().multiply(param.getDiscount()));
			}if(param.getDiscountType().equals("0")){
				student.setPayReal(student.getPayShould().subtract(param.getDiscount()));
			}
		
			studentService.update(student);
		}
		return result(200, "OK");
	}
	
	
	@RequestMapping(value="/pay/{id}",method=RequestMethod.GET)
	public String pay(@PathVariable String id) {
		Registe registe=registeService.get(id);
		registe.setCompanyName(companyService.get(registe.getCompanyCode()).getCompanyName());
		registe.setSchemeName(feesSchemeService.get(registe.getPayScheme()).getSchemeName());
		attr("registe", registe);
		return "school/registe/registe-pay";
	}
	
	@RequestMapping(value="/pay/{id}",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doPay(@PathVariable String id,@RequestBody Registe param,ServletRequest request, ServletResponse response) {
		
		HttpServletRequest req=(HttpServletRequest)request;
		OnlineUser currentUser =  onlineUserService.getLoginUser(req.getSession());
		Registe registe=registeService.get(id);
		registe.setRegisteCount(param.getRegisteCount());
		registe.setPayShould(param.getPayShould());
		registe.setPayReal(param.getPayReal());
		registe.setPayType(param.getPayType());
		registe.setPayAlready(param.getPayReal());
		registe.setPayStatus(1);
		registeService.update(registe);
		FeesScheme feesScheme=  feesSchemeService.get(param.getPayScheme());
		List<Student> students =studentService.checkRegiste(currentUser.getSysCode(),param.getRegisteCode());
		for(Student student : students){
			if(student.getPayStatus()!="1"){//未缴费部分学员	
			student.setPayStatus("1");
			student.setFeesHotel(feesScheme.getFeesHotel());
			student.setFeesMeal(feesScheme.getFeesMeal());
			student.setFeesStudy(feesScheme.getFeesStudy());
			student.setFeesTrain(feesScheme.getFeesTrain());
			student.setFeesTotal(feesScheme.getFeesTotal());
			student.setPayType(param.getPayType());//
			studentService.update(student);			
			}
		}
		return result(200, "OK");
	}
	
	@RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult doUpdate(@PathVariable String id, @RequestBody Registe param) {

		Registe registe = registeService.get(id);
		registeService.update(registe);
		return result(200, "OK");
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult doDelete(@PathVariable String id) {

		registeService.delete(id);
		return result(200, "OK");
	}
    
	
}
