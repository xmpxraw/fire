package org.yuantai.system.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;
import org.yuantai.common.cache.RedisUtil;
import org.yuantai.system.web.BaseAction;

/**
 * 防止重复提交aop拦截器
 * @author zhangle
 */
/*@Aspect
@Order(2)
@Component*/
public class TokenAop {
	
	private static final Logger logger = Logger.getLogger(TokenAop.class);
	@Autowired private RedisUtil redisUtil;
	
	/**
	 * 验证是否重复提交
	 * @param pjp
	 * @param action
	 * @param token
	 * @return
	 * @throws Throwable
	 */
	@Around("target(action) && @annotation(token)")
	public Object validateToken(final ProceedingJoinPoint pjp,BaseAction action,Token token) throws Throwable {
		
		String key=getCacheKey(pjp,action);
		Object value=redisUtil.get(key);
		
		if(logger.isDebugEnabled()) {
			logger.debug("Got a token from cache with key '"+key+"', value is '"+value+"'");
		}
		
		//如果value不等于null,那么代表是重复提交
		if(value!=null) {
			throw new RuntimeException("重复提交!");
		}
		
		//否则执行提交,并设置token有效期
		Object result=pjp.proceed();
		redisUtil.set(key,"OK",token.expire());
		return result;
	}
	
	/**
	 * 获取cache中的key值
	 * @param pjp
	 * @param cache
	 * @return
	 */
	private String getCacheKey(ProceedingJoinPoint pjp,BaseAction action) {
		
		StringBuilder buf=new StringBuilder();
		buf.append("token.").append(pjp.getTarget().getClass().getSimpleName()).append(".")
			.append(pjp.getSignature().getName()).append(".").append(action.request().getSession().getId());
		return buf.toString();
	}
	
}
