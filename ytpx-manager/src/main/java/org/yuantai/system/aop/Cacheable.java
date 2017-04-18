package org.yuantai.system.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识缓存方法的返回结果
 * @author zhangle
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Cacheable {
	
	public enum KeyMode{
		DEFAULT,	//只有加了@CacheKey的参数,才加入key中
		BASIC,		//只有基本类型参数,才加入key中,如:String,Number,Boolean
		ALL;		//所有参数都加入key,使用参数的toString()值加入key后缀
	}
	
	public String key() default "";		//缓存的key
	public KeyMode keyMode() default KeyMode.DEFAULT;		//key的后缀模式
	public int expire() default 0;		//缓存多少秒,默认0为无限期
}
