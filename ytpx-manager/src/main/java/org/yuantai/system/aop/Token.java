package org.yuantai.system.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识防止重复提交
 * @author zhangle
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Token {
	
	public int expire() default 10;		//限制重复提交时间,默认:10秒 单位:秒
}
