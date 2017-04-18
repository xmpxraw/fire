package org.yuantai.weixin.web;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yuantai.common.Constants;
import org.yuantai.common.util.SystemUtil;
import org.yuantai.school.pojo.Classes;
import org.yuantai.school.pojo.School;
import org.yuantai.school.pojo.Student;
import org.yuantai.school.pojo.WeixinAccounts;
import org.yuantai.school.service.ClassesService;
import org.yuantai.school.service.SchoolService;
import org.yuantai.school.service.StudentService;
import org.yuantai.school.service.WeixinAccountsService;
import org.yuantai.system.aop.WeChatCheckLogin;
import org.yuantai.system.web.ModelAction;
import org.yuantai.weixin.service.WechatAuthService;

import com.alibaba.fastjson.JSONObject;

/**
 * 微信端类
 * 
 * @author zamn
 *
 */
@Controller
@RequestMapping(value = "/school/weixin")
public class WXAction extends ModelAction<WeixinAccounts> {

	private static final Logger logger = LogManager.getLogger(WXAction.class);
	@Autowired
	private ClassesService classesService;
	@Autowired
	private SchoolService schoolService;;
	@Autowired
	private WechatAuthService wechatAuthService;
	@Autowired
	private WechatAuthService wechatService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private WeixinAccountsService weixinAccountsService;


	/**
	 * 进报班页面
	 * 
	 * @param data
	 * @return
	 */
	@WeChatCheckLogin
	@RequestMapping(value = "/class/viewClass", method = RequestMethod.GET)
	public @ResponseBody Object viewClass() {
		String openId = (String) sattr(Constants.WECHAT_LOGIN_SESSION_KEY);
		logger.debug("=============class/view/======"+openId);
		JSONObject jsonObject = new JSONObject();
		Student student = studentService.checkByOPenId(openId);
		logger.debug("========student.getSysCode()=========="+student.getId());
		if (student != null) {// 是否绑定
			if (student.getStudentStatus()==0 && student.getClassCode() == null) {// 结业考试未报名
				Classes classes = classesService.checkStatus(student.getSysCode(),student.getTermCode()).get(0);//查询正在报班的学习班
				logger.debug("========checkStatus========="+classes);
				School school = schoolService.checkBySysCode(classes.getSysCode()).get(0);
				jsonObject.put("schoolName", school.getSchoolName());
				jsonObject.put("classCode", classes.getClassCode());
				jsonObject.put("classId", classes.getId());
				jsonObject.put("classType", classes.getClassType());
				jsonObject.put("specialty", classes.getSpecialty());
				jsonObject.put("beginTime", classes.getBeginTime());
				jsonObject.put("duration", classes.getPeriod());
				jsonObject.put("planCount", classes.getPlanCount());
				jsonObject.put("result", 0);// 已经绑定
				jsonObject.put("code", 200);
				jsonObject.put("laset", classes.getPlanCount() - classes.getRegistryCount());// 剩余名额
			}else if (student.getStudentStatus()==2 || student.getStudentStatus() ==6) {// 国考未报名
				Classes classes = classesService.findSpringClass(student.getSysCode(),student.getTermCode()).get(0);//查询正在报班的突击班
				logger.debug("========findSpringClass=========="+classes);
				if(classes!=null){
					School school = schoolService.checkBySysCode(classes.getSysCode()).get(0);
					jsonObject.put("schoolName", school.getSchoolName());
					jsonObject.put("classCode", classes.getClassCode());
					jsonObject.put("classId", classes.getId());
					jsonObject.put("classType", classes.getClassType());
					jsonObject.put("specialty", classes.getSpecialty());
					jsonObject.put("beginTime", classes.getBeginTime());
					jsonObject.put("duration", classes.getPeriod());
					jsonObject.put("planCount", classes.getPlanCount());
					jsonObject.put("result", 0);// 已经绑定
					jsonObject.put("code", 200);
					jsonObject.put("laset", classes.getPlanCount() - classes.getRegistryCount());// 剩余名额
				}else{
					jsonObject.put("result", 3);// 暂时没有报班的班级
				}
				
			} 
			else {
				jsonObject.put("result", 2);// 已经报名跳转到报班成功页面
			}

		} else {
			jsonObject.put("result", 1);// 没有绑定
		}
		return jsonObject;
	}

	/**
	 * 报班
	 * 
	 * @param data
	 * @return
	 */
	@WeChatCheckLogin
	@RequestMapping(value = "/class/signUp", method = RequestMethod.POST)
	public @ResponseBody Object signUp(@RequestBody String data, HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject json = JSONObject.parseObject(data);
		String openId = (String) request.getSession().getAttribute("WECHAT_OPENID");
		Student student = studentService.checkByOPenId(openId);
		String classCode = json.getString("classCode");
		Classes classes = classesService.getClassesByCode(classCode);
		JSONObject jsonObject = new JSONObject();
		int studentCount = 0;
		String studentNo=null;
		if (student.getSysCode() != null) {
			List<Student>list = studentService.checkClass(student.getSysCode(), classCode);
			studentCount = list.size();
			studentNo = classCode + String.format("%03d", studentCount + 1);//YY+01
	 		 if(studentCount>0){
	 			 String lastCode=list.get(0).getStudentNo();
	 			studentNo=Integer.parseInt(lastCode)+1+"";//自增
	 		 }
		}
		if ((classes.getPlanCount() - classes.getRegistryCount()) > 0) {
			jsonObject.put("code", 200);
			jsonObject.put("msg", "报班成功");
			student.setClassCode(classCode);
			student.setStudentNo(studentNo);
	//		student.setStudentNo(classCode + String.format("%03d", studentCount + 1));
			student.setStudentStatus(1);// 学习中
			studentService.update(student);
			if (classes.getRegistryCount() == null) {
				classes.setRegistryCount(1);
			} else {
				classes.setRegistryCount(classes.getRegistryCount() + 1);
			}
			classesService.update(classes);
		} else {
			classes.setClassStatus(1);
			classesService.update(classes);
			jsonObject.put("code", -1);
			jsonObject.put("msg", "名额已满、不能报班!");
		}

		return jsonObject;
	}

	/**
	 * 报班成功后跳转
	 * 
	 * @param data
	 * @return
	 */
	@WeChatCheckLogin
	@RequestMapping(value = "/class/signed", method = RequestMethod.GET)
	public @ResponseBody Object signed(HttpServletRequest request,
			HttpServletResponse response) {
		String openId = (String) request.getSession().getAttribute("WECHAT_OPENID");
		Student student = studentService.checkByOPenId(openId);
		Classes classes = classesService.findClasses(student.getClassCode(), student.getSysCode()).get(0);
		JSONObject jsonObject = new JSONObject();
		School school = schoolService.checkBySysCode(classes.getSysCode()).get(0);
		jsonObject.put("schoolName", school.getSchoolName());
		jsonObject.put("classCode", classes.getClassCode());
		jsonObject.put("classType", classes.getClassType());//2:突击班,1:学习班
		jsonObject.put("specialty", classes.getSpecialty());
		jsonObject.put("beginTime", classes.getBeginTime());
		jsonObject.put("duration", classes.getPeriod());
		jsonObject.put("planCount", classes.getPlanCount());
		jsonObject.put("code", 200);
		jsonObject.put("laset", classes.getPlanCount() - classes.getRegistryCount());// 剩余名额
		return jsonObject;
	}

	/**
	 * 学校信息
	 * 
	 * @param data
	 * @return
	 */
	@WeChatCheckLogin
	@RequestMapping(value = "/school/view", method = RequestMethod.GET)
	public @ResponseBody Object viewSchool() {
		String openId = (String) sattr(Constants.WECHAT_LOGIN_SESSION_KEY);
		Student student = studentService.checkByOPenId(openId);
		School school = schoolService.checkBySysCode(student.getSysCode()).get(0);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("schoolName", school.getSchoolName());
		jsonObject.put("code", 200);
		logger.debug("====当前logo路径===：", "http://"+request().getHeader("Host")+"/fileUpload/"+school.getSchoolCode()+school.getLogo().substring(school.getLogo().lastIndexOf(".")));
		jsonObject.put("logo", "http://"+request().getHeader("Host")+"/fileUpload/"+school.getSchoolCode()+school.getLogo().substring(school.getLogo().lastIndexOf(".")));
		jsonObject.put("content", school.getContent());
		return jsonObject;
	}

	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response) {
		
		String openId = (String) request.getParameter("openId");
		HttpSession session = request.getSession();
		Student student = studentService.checkByOPenId(openId);
		logger.debug("========index1======"+openId+"=======student====="+student+"student");
		if (student != null) {
			session.setAttribute(Constants.WECHAT_LOGIN_SESSION_KEY, openId);
			session.setAttribute("WECHAT_OPENID", openId);
			/*logger.debug("=========session.getAttribute(Constants.WECHAT_LOGIN_SESSION_KEY);=="+session.getAttribute(Constants.WECHAT_LOGIN_SESSION_KEY));
			session.setAttribute("WECHAT_ACCESS_TOKEN", accessTokenAndOpenId[0]);*/
		} else {
			session.setAttribute("WECHAT_OPENID", null);
		}
		return "index";
	}
	
	
	@RequestMapping(value = "/index1", method = RequestMethod.GET)
	public String index1(HttpServletRequest request, HttpServletResponse response) {
		String code = (String) request.getParameter("code");
		String saveurl = (String) request.getParameter("saveurl");
		logger.debug("当前code信息：", code);
		logger.debug("当前saveurl", saveurl);
		try {
			// 开始调用微信的接口取得access_token和openId
			long startTime = System.currentTimeMillis();
			String data = wechatAuthService.getOpenId(request, code);
			String[] accessTokenAndOpenId = data.split("\\|");
			if (logger.isDebugEnabled()) {
				logger.debug("[Wechatcontroller:auth] 取得微信用户的信息[openId:{}] [token:{}] 用时:[{}ms]",
						accessTokenAndOpenId[1], accessTokenAndOpenId[0], (System.currentTimeMillis() - startTime));
			}
			String openId = accessTokenAndOpenId[1];
			String url=  saveurl+"?openId="+openId;
			// 跳转到用户实际请求的地址
			Properties props = SystemUtil.getSpringAppProperties();
			String baseUrl = props.getProperty("system.host.url"); // 获取配置的地址
			logger.debug("[Wechatcontroller:auth] 将要跳转的地址是[{}]", baseUrl + url);
			 response.sendRedirect(baseUrl + url);
		} catch (Exception ex) {
			logger.error("[Wechatcontroller:auth] 微信授权出错!", ex);
		}
		return "index";
	}

	@RequestMapping(value = "/auth/{code}", method = RequestMethod.GET)
	public String index(@PathVariable String code) {
		logger.debug("WXAction:index:code={}", code);

		School school = schoolService.getSchoolCode(code);
		List<WeixinAccounts> weixinAccounts = weixinAccountsService.checkBySysCode(school.getSysCode());
		if (school != null && !weixinAccounts.isEmpty()) {
			HttpSession session = request().getSession();
			session.setAttribute("WECHAT_APPID", weixinAccounts.get(0).getAppId());
			session.setAttribute("WECHAT_SECRET", weixinAccounts.get(0).getSecret());
		}

		String currUrl = "/school/weixin/index";
		logger.debug("当前需要保存的地址是:[{}]", currUrl);
		try {
			String redirectUrl = wechatService.makeAuthRedirectUrl(request(), currUrl,code);
			logger.debug("makeAuthRedirectUrl(request, currUrl)的返回值：" + redirectUrl);
			response().sendRedirect(redirectUrl);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "index";
	}

}
