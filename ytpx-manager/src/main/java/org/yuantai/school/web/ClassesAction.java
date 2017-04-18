package org.yuantai.school.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import org.yuantai.common.export.ExportExcel;
import org.yuantai.common.util.DateUtil;
import org.yuantai.school.pojo.Classes;
import org.yuantai.school.pojo.School;
import org.yuantai.school.pojo.Student;
import org.yuantai.school.pojo.StudentScore;
import org.yuantai.school.pojo.Term;
import org.yuantai.school.service.ClassesService;
import org.yuantai.school.service.SchoolService;
import org.yuantai.school.service.StudentScoreService;
import org.yuantai.school.service.StudentService;
import org.yuantai.school.service.TermService;
import org.yuantai.system.aop.Logging;
import org.yuantai.system.pojo.OnlineUser;
import org.yuantai.system.service.OnlineUserService;
import org.yuantai.system.web.ModelAction;

import net.ozsofts.wechat.WechatException;
import net.ozsofts.wechat.api.TemplateMsgAPI;


/**
 * 学校管理
 * @author zamn
 *
 */
@Controller
@RequestMapping(value="/school/classes")
public class ClassesAction extends ModelAction<Classes>{
	
	private static final Logger logger = LogManager.getLogger(ClassesAction.class);
	@Autowired private OnlineUserService onlineUserService;
    @Autowired private ClassesService classesService;
    @Autowired private TermService termService;
    @Autowired private StudentService studentService;
    @Autowired private StudentScoreService studentScoreService;
    @Autowired private TemplateMsgAPI templateMsgAPI;
    @Autowired private SchoolService schoolService;
    @Value("${school.student.uploaddir}")
  	private String scoreUploadDir;
	
    /**
     * 跳到班级列表页面
     */
    @RequestMapping(method=RequestMethod.GET)
	public String list() {
		return "school/classes/classes-list";
	}
    
    @RequestMapping(value="/query",method=RequestMethod.GET)
    public String query() {
    	return "school/classes/classes-query";
    }
    
    /**
	 * 查询班级
	 */
    @RequestMapping(value="/query",method=RequestMethod.POST)
	public String query(ServletRequest request, ServletResponse response,Classes param) {
    	HttpServletRequest req=(HttpServletRequest)request;
		DetachedCriteria criteria=getPaginator().getCriteria();
		if(!StringUtils.isEmpty(param.getClassCode())) {
			criteria.add(Restrictions.like("classCode",param.getClassCode(),MatchMode.ANYWHERE));
		}
		if(!StringUtils.isEmpty(param.getClassType())) {
			criteria.add(Restrictions.eq("classType", param.getClassType()));
		}
		OnlineUser currentUser =  onlineUserService.getLoginUser(req.getSession());
		if(currentUser.getSysCode()!=null&&currentUser.getSysCode()!=""){
			criteria.add(Restrictions.eq("sysCode", currentUser.getSysCode()));
		}
		criteria.addOrder(Order.desc("createTime"));
		criteria.addOrder(Order.desc("classCode"));
		classesService.find(getPaginator());
		List<Classes> list =getPaginator().getDataList();
		for(Classes classes:list){
			if(currentUser.getSysCode()!=null){
				String term=termService.checkTermCode(classes.getTermCode(), currentUser.getSysCode()).get(0).getTerm();
				classes.setTerm(term);
			}		
		}
		getPaginator().setDataList(list);
		
		return pagin(getPaginator());
	}
    
    
   
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(ServletRequest request, ServletResponse response) {
		HttpServletRequest req=(HttpServletRequest)request;
		OnlineUser currentUser =  onlineUserService.getLoginUser(req.getSession());
		List<Term> term=null;
		if(currentUser.getSysCode()!=null&&currentUser.getSysCode()!=""){
		 term = termService.checkBySysCode(currentUser.getSysCode());
		}else {
		 term = termService.findAll();
		}  	
    	attr("terms", term);
		return "school/classes/classes-add";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doAdd(@RequestBody Classes data,ServletRequest request, ServletResponse response) {
		
		HttpServletRequest req=(HttpServletRequest)request;
		OnlineUser currentUser =  onlineUserService.getLoginUser(req.getSession());
		data.setSysCode(currentUser.getSysCode());
	//	if(data.getClassType().equals("1")){
			Term term = termService.checkTermCode(data.getTermCode(), currentUser.getSysCode()).get(0);
			if(term!=null){
				Integer oldCount = term.getClassCount();
				Integer newCount =data.getClassCount();
				term.setClassCount(oldCount+newCount);
				termService.update(term);
			}			
	//	}
		for(int i=0;i<data.getClassCount();i++){
			int classCount=0;
			String classCode =null;
			if(currentUser.getSysCode()!=null){
				List<Classes>list=classesService.queryCount(currentUser.getSysCode(),data.getTermCode());
				classCount= list.size();
				classCode= data.getTermCode()+""+String.format("%02d", classCount+1);//班号：期号+序号
				 if(classCount>0){
		 			 String lastCode=list.get(0).getClassCode();
		 			classCode=Integer.parseInt(lastCode)+1+"";//自增
		 		 }
		 	}
			data.setClassCode(classCode);
			data.setRegistryCount(0);
			data.setClassStatus(0);//未满状态
			data.setCreateTime(new Date());
			classesService.add(data);
		}		
		return result(200, "OK");
	}
	

	@RequestMapping(value="/update/{id}",method=RequestMethod.GET)
	public String update(@PathVariable String id,ServletRequest request, ServletResponse response) {
		HttpServletRequest req=(HttpServletRequest)request;
		OnlineUser currentUser =  onlineUserService.getLoginUser(req.getSession());
		List<Term> term=null;
		if(currentUser.getSysCode()!=null&&currentUser.getSysCode()!=""){
		 term = termService.checkBySysCode(currentUser.getSysCode());
		}else {
		 term = termService.findAll();
		}
    	
    	attr("terms", term);
		Classes classes=classesService.get(id);
		if(classes.getTermCode()!=null&&classes.getTermCode()!=""){
			if(!termService.checkTermCode(classes.getTermCode(), currentUser.getSysCode()).isEmpty()){
				classes.setTerm(termService.checkTermCode(classes.getTermCode(), currentUser.getSysCode()).get(0).getTerm());
			}
		}
		
		attr("classes", classes);
		return "school/classes/classes-update";
	}
	
	@RequestMapping(value="/update/{id}",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doUpdate(@PathVariable String id,@RequestBody Classes param) {
		
		Classes classes=classesService.get(id);
		classes.setClassType(param.getClassType());
		if(param.getClassType().equals("2")){
			classes.setTermCode(null);
		}else {
			classes.setTermCode(param.getTermCode());
		}
		classes.setClassCount(param.getClassCount());
		classes.setBeginTime(param.getBeginTime());
		classes.setPeriod(param.getPeriod());
		classes.setPlanCount(param.getPlanCount());
		classes.setSpecialty(param.getSpecialty());
		classesService.update(classes);
		return result(200, "OK");
	}
	
	/**
	 * 导出excel班级列表
	 */
	@Logging(name="导出excel班级列表")
	@RequestMapping(value="/exportTemplet/{id}",method=RequestMethod.GET)
	public @ResponseBody void exportTemplet(@PathVariable String id,ServletRequest request, HttpServletResponse response) throws Exception {
		HttpServletRequest req=(HttpServletRequest)request;
		OnlineUser currentUser =  onlineUserService.getLoginUser(req.getSession());
		List<Student> students =studentService.exportTemplet(currentUser.getSysCode(),classesService.get(id).getClassCode());
		getPaginator(Student.class).setPageSize(1000);//只导出1000条记录
		getPaginator(Student.class).setDataList(students);
		String classCode =classesService.get(id).getClassCode();
		try {
			String title="学员成绩列表模版";
			response.reset();
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");  
		    response.addHeader("Content-Disposition", "attachment;filename="+ new String((classCode+"班的"+title+".xlsx").getBytes(), "ISO-8859-1"));
			ExportExcel.exportExcel(
					title, 
					new String[]{"身份证号码","学员姓名","班号","学号","理论成绩","水系统成绩","控制室成绩","防火巡查成绩","气体灭火成绩","总成绩","是否及格","考试日期","考试地点"}, 
					//getPaginator(Student.class).getDataList(),
					students,
					response.getOutputStream());
			response.getOutputStream().flush();
		} catch (Exception e) {
			logger.error("导出xls学员模版列表出错",e);
		}
	}
	
	@RequestMapping(value="/importScore/{id}",method=RequestMethod.GET)
	public String importScore(@PathVariable String id) {
		Classes classes=classesService.get(id);
		attr("classes", classes);
		return "school/classes/classes-importScore";
	}
	
//导入学校成绩
	@RequestMapping(value="/importScore/{id}",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doImportScore(Classes data,MultipartFile file,ServletRequest request, ServletResponse response) {
		HttpServletRequest req=(HttpServletRequest)request;
		OnlineUser currentUser =  onlineUserService.getLoginUser(req.getSession());
		Classes classes = classesService.get(data.getId());
		School school=schoolService.getSchoolBySysCode(classes.getSysCode());
		String classType=classes.getClassType();
		Integer code =200;
		String msg ="OK";
		try {
			File parentFile=new File(scoreUploadDir);
			if(!parentFile.exists()) parentFile.mkdirs();
			File uploadfile=new File(parentFile,data.getId()+"."+DateUtil.getCurrentDateStr("yyyyMMddHHmmss"));
			file.transferTo(uploadfile);
			InputStream is =null;
			XSSFWorkbook xWorkbook = null;			 
		    is = new FileInputStream(uploadfile);
		     xWorkbook = new XSSFWorkbook(is);
		      //第一个工作表
		    XSSFSheet xSheet = xWorkbook.getSheetAt(0);
		    int length = xSheet.getPhysicalNumberOfRows();//导入excel表需要带表头
		       if(code!=-1){
		      for(int i=2;i<length;i++){
		    	  XSSFRow  row = xSheet.getRow(i);	
		    	  //  row.getCell(11).setCellValue(value););
		    	    String idcard= row.getCell(0).toString();
		    	    String studentNo =row.getCell(3).toString();
		    	    StudentScore studentScore =new StudentScore();
		    	    studentScore.setIdcard(idcard);
		    	    studentScore.setCreateTime(new Date());
		    	    studentScore.setStudnetName(row.getCell(1).toString());
		    	    studentScore.setClasses(row.getCell(2).toString());
		    	    studentScore.setStudentNo(row.getCell(3).toString());    	    
		    	    studentScore.setScoreLl(Float.parseFloat(row.getCell(4).toString()));
		    	    studentScore.setScoreSxt(Float.parseFloat(row.getCell(5).toString()));
		    	    studentScore.setScoreKzs(Float.parseFloat(row.getCell(6).toString()));
		    	    studentScore.setScoreFhxc(Float.parseFloat(row.getCell(7).toString()));
		    	    studentScore.setScoreQtmh(Float.parseFloat(row.getCell(8).toString()));
		    	    studentScore.setScoreTotal(Float.parseFloat(row.getCell(9).toString()));
		    	    studentScore.setPass(row.getCell(10).toString().trim());
		    	    studentScore.setType(classType);
		    	    studentScore.setSysCode(data.getSysCode());
		    	    studentScore.setSysCode(classes.getSysCode());
		    	    studentScoreService.add(studentScore);
		    	    Student student = studentService.queryStudent(idcard, studentNo).get(0);
		    	    String weixinOpenid =student.getWeixinOpenid();
		    	    student.setPass(row.getCell(10).toString().trim());  
		    	    SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd");
		    	    if(row.getCell(11).toString().trim()!=null){
		    	    	  Date date;
						try {
							date = sdf.parse(row.getCell(11).toString());
							  student.setExamTime((date));  
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	    	    	
		    	    }	    	   
		    	    student.setExamLocation(row.getCell(12).toString().trim());  
		    	    if(classType.equals("1")){//结业考试
		    	    	 student.setCommonScore(Float.parseFloat(row.getCell(9).toString()));
		    	    	 
				    	    if(row.getCell(10).toString().trim().equals("否")){//考试没通过、
				    	    	if(student.getStudentStatus()==1){//第一次考试
				    	    		student.setStudentStatus(3);//待复考状态
				    	    	}else if(student.getStudentStatus()==3){//第二次考试没过
				    	    		student.setStudentStatus(4);//复读状态--关闭状态新建一条该学员记录		    	    		
				    	    		student.setWeixinOpenid(null);
				    	    		copy(student,weixinOpenid);
				    	    	}		    	    	
				    	    }else if(row.getCell(10).toString().trim().equals("是")){
				    	    	student.setStudentStatus(2);//考试通过状态
				    	    	student.setPass("是");
				    	    	if(classes.getClassType().equals("1")){//学习班通过新建一条新学员记录、报名突击班
				    	    		student.setWeixinOpenid(null);
				    	    		copy(student,weixinOpenid);
				    	    	}
				    	    }
		    	    }
		    	    if(classType.equals("2")){//国考
		    	    	 student.setNationScore(Float.parseFloat(row.getCell(9).toString()));
		    	    	 if(row.getCell(10).toString().trim().equals("否")){//国考没通过、		    	    		
		    	    		 student.setStudentStatus(6);//国考不合格    	  
		    	    	//	 copy(student,weixinOpenid);
				    	    }else if(row.getCell(10).toString().trim().equals("是")){
				    	    	student.setStudentStatus(5);//国考合格
				    	    }
		    	    }
		    	   
		    	    studentService.update(student);
		    	    /*发送考试成绩模版通知*/
		    	    Map<String, String> params = new HashMap<String, String>();				
					String MsgId =null;
					if(classType.equals("1")){//结业考试
						params.put("studentName",student.getStudnetName());
						params.put("courseName", classes.getSpecialty());
						params.put("examTime", student.getExamTime()+"");
						params.put("examLocation", student.getExamLocation());
						params.put("examScore", student.getCommonScore()+"");
						params.put("telephone", school.getTelephone());
						logger.debug("=====student.getExamTime()======"+student.getExamTime()+"");
						if(student.getPass().equals("是")){	 //结业考试通过								
							try {								
								MsgId = templateMsgAPI.sendByParams(school.getSchoolCode(), weixinOpenid, "wechat.school.exam.pass", "", params);
							} catch (WechatException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						if(student.getPass().equals("否")){//结业考试不通过
							try {
								MsgId = templateMsgAPI.sendByParams(school.getSchoolCode(), weixinOpenid, "wechat.school.exam.fail", "", params);
							} catch (WechatException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}	
					}else{//国考
						 String strPass= null;
				    	    if(student.getPass().equals("是")){
				    	    	strPass="通过";
				    	    }else{
				    	    	strPass="不通过";
				    	    }
						params.put("studentName",student.getStudnetName());
						params.put("studentNo", student.getStudentNo());
						params.put("cexamScore", student.getNationScore()+"");
						
						params.put("examRanking", strPass);//是否通过
						if(student.getPass().equals("是")){	//国考通过								
							try {								
								MsgId = templateMsgAPI.sendByParams(school.getSchoolCode(), weixinOpenid, "wechat.country.exam.pass", "", params);
							} catch (WechatException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						if(student.getPass().equals("否")){//国考不通过
							try {
								MsgId = templateMsgAPI.sendByParams(school.getSchoolCode(), weixinOpenid, "wechat.country.exam.fail", "", params);
							} catch (WechatException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}	
					}
									   
					logger.debug("===MsgId:==="+MsgId);
		      }     	   
	       }
		       int studentCount = studentService.checkClass(currentUser.getSysCode(), classes.getClassCode()).size();
		       int pass= studentService.checkClassPass(currentUser.getSysCode(), classes.getClassCode(), "是").size();//查询班级通过人数
		       BigDecimal s=new BigDecimal(studentCount);
		       BigDecimal p=new BigDecimal(pass);
		       BigDecimal passRale =p.divide(s,2,BigDecimal.ROUND_HALF_UP);
		       classes.setPassCount(pass);
		       classes.setPassRate(passRale.setScale(2, BigDecimal.ROUND_HALF_UP)+"");
		       classesService.update(classes);
		       
		}  catch (IllegalStateException | IOException e) {
			return result(-1,"录入成绩失败");
		}
		
		return result(code, msg);
	}

	
	@RequestMapping(value="/delete/{id}",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doDelete(@PathVariable String id) {
		int code =200;
		String msg="OK";
		Classes classes = classesService.get(id);
		List<Student> list= studentService.checkClass(classes.getSysCode(), classes.getClassCode());
		if(list.isEmpty()){
			classesService.delete(id);
		}else{
			code=-1;
			msg="error";
		}		
  	 return result(code, msg);
	}
	//发送成绩模版通知
	@RequestMapping(value="/notify/{id}",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doNotify(@PathVariable String id,ServletRequest request, ServletResponse response) {
			HttpServletRequest req=(HttpServletRequest)request;
			OnlineUser currentUser =  onlineUserService.getLoginUser(req.getSession());
			Classes classes= classesService.get(id);
			List<Integer> studentStatus= new ArrayList<>();
		//	Integer[] studentStatus ={0};//报名状态
			if(classes.getClassType().equals("2")){//突击班
				studentStatus.add(2);//通过考试状态
				studentStatus.add(6);//不通过国考状态
			}else{//学习班
				studentStatus.add(0);//报名状态状态
			}
			if(classes.getNotifyLog()==null){
				classes.setNotifyLog(1);
			}else{
				int notifyCount= classes.getNotifyLog();
				classes.setNotifyLog(notifyCount+1);//发送消息数次			
			}
			classesService.update(classes);
			List<Student> students = studentService.notify(currentUser.getSysCode(),studentStatus,classes.getTermCode());
			for(Student student:students){
				School school=schoolService.getSchoolBySysCode(student.getSysCode());
				Map<String, String> params = new HashMap<String, String>();
				params.put("studentName",student.getStudnetName());
				params.put("courseName", classes.getSpecialty());
				params.put("telephone", school.getTelephone());
				params.put("classTime", classes.getBeginTime()+"");
				String detailUrl=req.getHeader("Host")+"/school/weixin/auth/"+school.getSchoolCode();
				try {
					String MsgId =null;
					if(classes.getClassType().equals("1")){
						  MsgId = templateMsgAPI.sendByParams(school.getSchoolCode(), student.getWeixinOpenid(), "wechat.class.open", detailUrl, params);
					}
					if(classes.getClassType().equals("2")){
						  MsgId = templateMsgAPI.sendByParams(school.getSchoolCode(), student.getWeixinOpenid(), "wechat.sprintclass.open", detailUrl, params);
					}					   
					logger.debug("===MsgId:==="+MsgId);
				} catch (WechatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
		 return result(200, "OK");
	}
	
	public void copy(Student student,String weixinOpenid){
		
		Student s=new Student();
		s.setAge(student.getAge());
		s.setStudnetName(student.getStudnetName());
		s.setSex(student.getSex());
		s.setIdcard(student.getIdcard());
		s.setMobile(student.getMobile());
		s.setExpiry(student.getExpiry());
		s.setEducationLevel(student.getEducationLevel());
		if(student.getPass().equals("否")){
			s.setStudentStatus(0);
		}else{
			s.setStudentStatus(2);
			s.setCommonScore(student.getCommonScore());
		}
		if(student.getStudentStatus()==6){
			s.setStudentStatus(2);//国考不合格重新报突击班
		}
		s.setRegistType(student.getRegistType());
		s.setRegistTime(new Date());
		s.setWeixinOpenid(weixinOpenid);
		s.setCompanyCode(student.getCompanyCode());
		s.setTermCode(student.getTermCode());
		s.setRegisteCode(student.getRegisteCode());
		s.setSysCode(student.getSysCode());
		s.setPayScheme(student.getPayScheme());
		s.setPayStatus(student.getPayStatus());
		s.setPayReal(student.getPayReal());
		s.setPayShould(student.getPayShould());
		s.setPayType(student.getPayType());
		s.setFeesHotel(student.getFeesHotel());
		s.setFeesMeal(student.getFeesMeal());
		s.setFeesStudy(student.getFeesStudy());
		s.setFeesTrain(student.getFeesTrain());
		s.setFeesTotal(student.getFeesTotal());
		s.setCreateTime(new Date());
		studentService.add(s);
	}
}
