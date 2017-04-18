package org.yuantai.system.aop;

import java.util.Arrays;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.yuantai.common.util.JsonUtil;
import org.yuantai.common.util.StringUtil;
import org.yuantai.system.aop.Logging.LoggingTarget;
import org.yuantai.system.pojo.Logs;
import org.yuantai.system.pojo.Pojo;
import org.yuantai.system.pojo.User;
import org.yuantai.system.service.BaseService;
import org.yuantai.system.service.LogsService;
import org.yuantai.system.service.OnlineUserService;

/**
 * 系统模块-日志记录类
 * @author zhanngle
 */
@Aspect
@Component
public class SystemLogsAop {

	private static final Logger logger = LogManager.getLogger(SystemLogsAop.class);
	
	@Autowired private LogsService logsService;
	@Autowired private OnlineUserService onlineUserService;
	
	@Pointcut("@target(org.yuantai.system.aop.Logging)")
	public void logging() {}
	
	@Pointcut("execution(public * delete(..))")
	public void deleteMethod() {}
	
	@Pointcut("execution(public * find(..))")
	public void findMethod() {}
	
	@Pointcut("execution(public * update(..))")
	public void updateMethod() {}
	
	@Pointcut("execution(public * add(..))")
	public void addMethod() {}
	
	@PostConstruct
	public void startup() {
		Logs logs=newLogs("system", "system", "startup", "启动系统","",null);
		logsService.add(logs);
	}
	
	@PreDestroy
	public void shutdown() {
		Logs logs=newLogs("system", "", "shutdown", "关闭系统","",null);
		logsService.add(logs);
	}
	
	/**
	 * 对登录用户进行记录
	 */
	@AfterReturning(value="bean(onlineUserServiceImpl) && addMethod()")
	public void login() {
		Logs logs = newLogs("system","User","login","用户登录","",null);
		logsService.add(logs);
	}
	
	/**
	 * 对注销用户进行记录
	 */
	@Before(value="bean(onlineUserServiceImpl) && deleteMethod()")
	public void logout() {
		Logs logs = newLogs("system","User","logout","用户注销","",null);
		logsService.add(logs);
	}
	
	/**
	 * 测试对Baseservice的find
	 */
	@AfterReturning(value="bean(roleServiceImpl) && findMethod()")
	public void find() {
		System.out.println(".................................find..........................");
	}
	
	/**
	 * 对删除操作进行记录
	 * @param dao
	 * @param id
	 */
	@Before(value="logging() && deleteMethod() && target(service) && args(id)",argNames="service,id")
	public void delete(BaseService<Object> service,Object id) {

		if(service==null || id==null) return;
		
		Object pojo=null;
		if(id instanceof String) {
			pojo=service.get((String)id);
		}
		if(pojo==null) return;
		if(pojo instanceof Logs) return;
		
		Logs logs =newLogs(pojo,"delete","删除记录");
		logsService.add(logs);
	}
	
	/**
	 * 对添加操作进行记录
	 * 拦截service层,这里要考虑不能死循环添加日志,因此判断pojo是否Logs类
	 * @param bean
	 */
	@AfterReturning(value="logging() && addMethod() && target(service) && args(pojo)",argNames="service,pojo")
	public void add(BaseService<Object> service,Pojo pojo) {
		
		if(pojo==null) return;
		if(pojo instanceof Logs) return;
		
		Logs logs =newLogs(pojo,"add","增加记录");
		logsService.add(logs);
	}
	
	/**
	 * 对更新操作进行记录
	 * 拦截service层,这里要考虑不能死循环添加日志,因此判断pojo是否Logs类
	 * @param bean
	 */
	@AfterReturning(value="logging() && updateMethod() && target(service) && args(pojo)",argNames="service,pojo")
	public void update(BaseService<Object> service,Pojo pojo) {
		
		if(pojo==null) return;
		if(pojo instanceof Logs) return;
		
		Logs logs =newLogs(pojo,"update","修改记录");
		logsService.add(logs);
	}
	
	/**
	 * 为任意加了@Logging的方法,增加日志记录
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around("@annotation(logging)")
	public Object logMethod(ProceedingJoinPoint pjp,Logging logging) throws Throwable {
		
		if(logging.target()==LoggingTarget.DATABASE) {
			return database(pjp,logging);
		} else if(logging.target()==LoggingTarget.LOG4J) {
			return log4j(pjp,logging);
		} else {
			return console(pjp,logging);
		}
	}
	
	private Object console(ProceedingJoinPoint pjp,Logging logging) throws Throwable {
		long start=System.currentTimeMillis();
	    Object result = pjp.proceed();
	    long end=System.currentTimeMillis();
	    
	    String module=getModule(pjp.getTarget());
	    String entity=getEntity(pjp.getTarget());
	    String type=pjp.getSignature().getName();
	    String name=logging.name();
	    String remark=logging.remark();
	    if(remark.length()==0) {
	    	remark=result+"="+toStringForArgs(pjp.getArgs());
	    }
	    
	    Logs logs =newLogs(module,entity,type,name,remark,(int)(end-start));
	    System.out.println(JsonUtil.toJson(logs));
	    return result;
	}
	
	private Object log4j(ProceedingJoinPoint pjp,Logging logging) throws Throwable {
		long start=System.currentTimeMillis();
	    Object result = pjp.proceed();
	    long end=System.currentTimeMillis();
	    
	    String module=getModule(pjp.getTarget());
	    String entity=getEntity(pjp.getTarget());
	    String type=pjp.getSignature().getName();
	    String name=logging.name();
	    String remark=logging.remark();
	    if(remark.length()==0) {
	    	remark=result+"="+toStringForArgs(pjp.getArgs());
	    }
	    
	    Logs logs =newLogs(module,entity,type,name,remark,(int)(end-start));
	    logger.info(JsonUtil.toJson(logs));
	    return result;
	}
	
	private Object database(ProceedingJoinPoint pjp,Logging logging) throws Throwable {
		
		long start=System.currentTimeMillis();
	    Object result = pjp.proceed();
	    long end=System.currentTimeMillis();
	    
	    String module=getModule(pjp.getTarget());
	    String entity=getEntity(pjp.getTarget());
	    String type=pjp.getSignature().getName();
	    String name=logging.name();
	    String remark=logging.remark();
	    if(remark.length()==0) {
	    	remark=result+"="+toStringForArgs(pjp.getArgs());
	    }
	    
	    Logs logs =newLogs(module,entity,type,name,remark,(int)(end-start));
		logsService.add(logs);
	    return result;
	}

	/**
	 * 将参数转换为字符串
	 * @param args
	 * @return
	 */
	private String toStringForArgs(Object[] args) {
		
		if(args==null || args.length==0) return "()";
		StringBuilder sb=new StringBuilder();
		sb.append("(");
		for(int i=0;i<args.length;i++) {
			if(args[i] instanceof Object[]) {
				sb.append(Arrays.toString((Object[])args[i]));
			} else {
				sb.append(args[i]==null?null:args[i].toString());
			}
			if(i<args.length-1) sb.append(",");
		}
		sb.append(")");
		return sb.toString();
	}
	
	/**
	 * 快速创建一个logs对象
	 * @param module
	 * @param entity
	 * @param type
	 * @param remark
	 * @return
	 */
	private Logs newLogs(String module,String entity,String type,String name,String remark,Integer elapsed) {
		
		Logs logs=new Logs();
		
		try {
			User user=onlineUserService.getLoginUser(getRequest().getSession());
			if(user!=null) {
				logs.setUsername(user.getUsername());
			}
			logs.setHost(getRequest().getRemoteAddr());
		} catch (Exception e) {}
		
		logs.setModule(module);
		logs.setEntity(entity);
		logs.setType(type);
		logs.setName(name);
		logs.setRemark(StringUtil.getString(remark, 500));
		logs.setElapsed(elapsed);
		logs.setCreateTime(new Date());
		return logs;
	}
	
	/**
	 *  快速创建一个logs对象
	 * @param pojo
	 * @param type
	 * @return
	 */
	private Logs newLogs(Object pojo,String type,String name) {
		return newLogs(getModule(pojo),getEntity(pojo),type,name,getRemark(pojo),null);
	}
	
	/**
	 * 获取模块名
	 * @param clazz
	 * @return
	 */
	private String getModule(Object pojo) {
		Class clazz=pojo.getClass();
		String[] name=clazz.getName().split("\\.");
		return name.length>=4?name[3]:clazz.getSimpleName();
	}
	
	/**
	 * 获取实体名
	 * @param pojo
	 * @return
	 */
	private String getEntity(Object pojo) {
		return pojo.getClass().getSimpleName();
	}
	
	/**
	 * 获取备注
	 * @param pojo
	 * @return
	 */
	private String getRemark(Object pojo) {
		return pojo.toString();
	}
	
	/**
	 * 获取request对象
	 * @return
	 */
	private HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}
}
