package org.yuantai.school.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import org.yuantai.common.SystemConfig;
import org.yuantai.common.util.DateUtil;
import org.yuantai.common.util.HttpClientUtils;
import org.yuantai.common.util.SystemUtil;
import org.yuantai.school.pojo.School;
import org.yuantai.school.pojo.SchoolVo;
import org.yuantai.school.service.SchoolService;
import org.yuantai.system.pojo.OnlineUser;
import org.yuantai.system.pojo.User;
import org.yuantai.system.service.OnlineUserService;
import org.yuantai.system.service.RoleService;
import org.yuantai.system.service.UserService;
import org.yuantai.system.web.ModelAction;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


/**
 * 学校管理
 * @author zamn
 *
 */
@Controller
@RequestMapping(value="/school/school")
public class SchoolAction extends ModelAction<School>{
	
	private static final Logger logger = LogManager.getLogger(SchoolAction.class);
	@Autowired private OnlineUserService onlineUserService;
    @Autowired private SchoolService schoolService;
    @Autowired private UserService userService;
    @Autowired private RoleService roleService;
    @Value("${school.student.uploaddir}")
  	private String logeUploadDir;
    /**
     * 跳到学校列表页面
     */
    @RequestMapping(method=RequestMethod.GET)
	public String list() {
		return "school/school/school-list";
	}
    
    @RequestMapping(value="/query",method=RequestMethod.GET)
    public String query() {
    	return "school/school/school-query";
    }
    
    /**
	 * 查询学校
	 */
    @RequestMapping(value="/query",method=RequestMethod.POST)
	public String query(ServletRequest request, ServletResponse response,School param) {
    	HttpServletRequest req=(HttpServletRequest)request;
		DetachedCriteria criteria=getPaginator().getCriteria();
		if(!StringUtils.isEmpty(param.getSchoolName())) {
			criteria.add(Restrictions.like("schoolName",param.getSchoolName(),MatchMode.ANYWHERE));
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
		schoolService.find(getPaginator());
		return pagin(getPaginator());
	}
    
    
   
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add() {
		return "school/school/school-update";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doAdd( SchoolVo data,MultipartFile file) throws IOException {
		String  filePath=null;
		if(file!=null){
		File parentFile=new File(logeUploadDir);
		if(!parentFile.exists()) parentFile.mkdirs();	
		File uploadfile=new File(parentFile,data.getSchoolCode()+"."+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1));
		filePath =uploadfile.getPath();
		try {
			file.transferTo(uploadfile);
		} catch (IllegalStateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		}
		try {
			String sysCode= UUID.randomUUID().toString();
			School school= new School();
			school.setCreateTime(new Date());
			school.setCurrentTotal(data.getCurrentTotal());
			school.setContent(data.getContent());
			school.setDistrict(data.getDistrict());
			school.setProvince(data.getProvince());
			school.setCity(data.getCity());
			school.setSchoolName(data.getSchoolName());
			school.setSchoolAddr(data.getSchoolAddr());
			school.setSchoolCode(data.getSchoolCode());
			school.setStatus(0);//0表新建状态,1表示上线，2表示下线
			school.setSysCode(sysCode);
			school.setTelephone(data.getTelephone());
			school.setTotal(data.getTotal());
			school.setTransportation(data.getTransportation());
			school.setCurrentTotal(0);
			school.setTotal(0);
			school.setLogo(filePath);
			schoolService.add(school);
			User user =new User();
			user.setUsername(data.getUsername());
			user.setPassword(data.getPassword());
			user.setSysCode(sysCode);
			user.setStatus(1);
			user.setMobile(data.getTelephone());
			user.setCreateTime(new Date());
			String schoolManagerRole =SystemConfig.getProperty("system.role.school.manager");
			String[] checkedRoleId = new String[]{roleService.getRoleByType(schoolManagerRole).getId()};
			user.setCheckedRoleId(checkedRoleId);
			userService.add(user);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result(200, "OK");
	}
	

	@RequestMapping(value="/update/{id}",method=RequestMethod.GET)
	public String update(@PathVariable String id) {
		School school=schoolService.get(id);
		attr("school", school);
		return "school/school/school-update";
	}
	
	@RequestMapping(value="/update/{id}",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doUpdate(@PathVariable String id, School param,MultipartFile file,HttpServletRequest request, HttpServletResponse response) throws IOException {
		String  filePath=null;
		if(file!=null){
		File parentFile=new File(logeUploadDir);
		if(!parentFile.exists()) parentFile.mkdirs();	
		File uploadfile=new File(parentFile,param.getSchoolCode()+"."+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1));
		filePath =uploadfile.getPath();
		try {
			file.transferTo(uploadfile);
		} catch (IllegalStateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		}
		try {
			School school=schoolService.get(id);
			school.setDistrict(param.getDistrict());
			school.setProvince(param.getProvince());
			school.setCity(param.getCity());
			school.setSchoolName(param.getSchoolName());
			school.setSchoolAddr(param.getSchoolAddr());
			school.setTelephone(param.getTelephone());
			school.setTransportation(param.getTransportation());
			school.setContent(param.getContent());
			school.setSchoolCode(param.getSchoolCode());
			school.setLogo(filePath);
			schoolService.update(school);
		
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result(200, "OK");
	}
	
	/**
	 * 检查学校状态
	 * @param status
	 * @return code:200 表示不存在,code:-1 表示已存在
	 */
	@RequestMapping(value="/check_status",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult checkStatus(String id) {
	//	boolean exist=userService.getUserByName(username)!=null;
		Integer status= schoolService.get(id).getStatus();
		if(status==0) {//新建状态
			return result(0, "create");
		}else if(status==1) {//下线状态
			return result(1, "off line");//下线
		}else if(status==2) {//上线状态
			return result(2, "on line");//
		}else {
			return result(200, "on line");
		}
	}
	
	@RequestMapping(value="/offline/{id}",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult offline(@PathVariable String id) {
		
		School school=schoolService.get(id);
		school.setStatus(1);//0表示新建；1表示下线；2表示上线
		schoolService.update(school);
		String sysCode =school.getSysCode();
		List<User>users = userService.getUserBySysCode(sysCode);
		schoolService.update(school);
		for(User user:users){
			user.setStatus(1);
			userService.update(user);
		}
		return result(200, "OK");
	}
	
	@RequestMapping(value="/online/{id}",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult online(@PathVariable String id) {
		
		School school=schoolService.get(id);
		school.setStatus(2);//0表示新建；1表示下线；2表示上线
		String sysCode =school.getSysCode();
		List<User>users = userService.getUserBySysCode(sysCode);
		schoolService.update(school);
		for(User user:users){
			user.setStatus(0);
			userService.update(user);
		}
		return result(200, "OK");
	}
	
	@RequestMapping(value="/delete/{id}",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doDelete(@PathVariable String id) {
		String sysCode = schoolService.get(id).getSysCode();
		if(sysCode!=null&&sysCode!=""){
			List<User> users= userService.getUserBySysCode(sysCode);
			if(!users.isEmpty()){
				for(User user:users){
					userService.delete(user.getId());			
				}		
			}
		}
		schoolService.delete(id);
		
  	 return result(200, "OK");
	}
    
	
}
