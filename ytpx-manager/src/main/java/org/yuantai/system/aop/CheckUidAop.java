package org.yuantai.system.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.yuantai.common.util.StringUtil;
import org.yuantai.system.web.BaseAction;

/**
 * 检查是否可获取uid
 * @author zhangle
 */
@Aspect
@Order(3)
@Component
public class CheckUidAop {
	
	/**
	 * 为加了@CheckUid的方法,检查是否可获取uid,若不可获取,那么抛出异常
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around("target(action) && @annotation(checkUid)")
	public Object check(final ProceedingJoinPoint pjp,BaseAction action,CheckUid checkUid) throws Throwable {
		
		if(StringUtil.isEmpty(action.uid())) {
			throw new RuntimeException("用户未登录!");
		}
		
	    return pjp.proceed();
	}
}
