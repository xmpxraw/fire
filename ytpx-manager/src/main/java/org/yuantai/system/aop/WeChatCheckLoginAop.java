package org.yuantai.system.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.yuantai.common.Constants;
import org.yuantai.system.web.BaseAction;

import com.alibaba.fastjson.JSONObject;


/**
 * 检查是否可获取uid
 * @author zhangle
 */
@Aspect
@Order(3)
@Component
public class WeChatCheckLoginAop {
	
	/**
	 * 为加了@WeChatCheckLogin的方法,检查是否可获取openid
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around("target(action) && @annotation(weChatCheckLogin)")
	public Object check(final ProceedingJoinPoint pjp,BaseAction action,WeChatCheckLogin weChatCheckLogin) throws Throwable {
		
		String openid =(String) action.sattr(Constants.WECHAT_LOGIN_SESSION_KEY);
		if(openid==null||"".equals(openid)) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("result", 1);// 没有绑定
			return jsonObject;
		}
	    return pjp.proceed();
	}
}
