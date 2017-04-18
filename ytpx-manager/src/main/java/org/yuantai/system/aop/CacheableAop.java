package org.yuantai.system.aop;

import java.lang.annotation.Annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.yuantai.common.cache.RedisUtil;
import org.yuantai.system.aop.Cacheable.KeyMode;

/**
 * 缓存功能aop拦截器
 * @author zhangle
 */
/*@Aspect
@Order(10)
@Component*/
public class CacheableAop {
	
	@Autowired private RedisUtil redisUtil;
	
	@Around("@annotation(cache)")
	public Object cache(final ProceedingJoinPoint pjp,Cacheable cache) throws Throwable {
		
		String key=getCacheKey(pjp, cache);
		Object value=redisUtil.get(key);	//从缓存获取数据
		if(value!=null) {
			return value;		//如果有数据,则直接返回
		}
		
		value = pjp.proceed();		//跳过缓存,到后端查询数据
		if(cache.expire()<=0) {		//如果没有设置过期时间,则无限期缓存
			redisUtil.set(key, value);
		} else {					//否则设置缓存时间
			redisUtil.set(key, value,cache.expire());
		}
		return value;
	}
	
	/**
	 * 获取缓存的key值
	 * @param pjp
	 * @param cache
	 * @return
	 */
	private String getCacheKey(ProceedingJoinPoint pjp,Cacheable cache) {
		
		StringBuilder buf=new StringBuilder();
		if(cache.key().length()>0) {
			buf.append(cache.key());
		} else {
			buf.append(pjp.getTarget().getClass().getSimpleName()).append(".").append(pjp.getSignature().getName());
		}
		
		Object[] args=pjp.getArgs();
		if(cache.keyMode()==KeyMode.DEFAULT) {		//只有加了@CacheKey的参数,才加入key中
			Annotation[][] pas=((MethodSignature)pjp.getSignature()).getMethod().getParameterAnnotations();
			for(int i=0;i<pas.length;i++) {
				for(Annotation an:pas[i]) {
					if(an instanceof CacheKey) {
						buf.append(".").append(args[i].toString());
						break;
					}
				}
			}
		} else if(cache.keyMode()==KeyMode.BASIC) {	//只有基本类型参数,才加入key中,如:String,Number,Boolean
			for(Object arg:args) {
				if(arg instanceof String) {
					buf.append(".").append(arg);
				} else if(arg instanceof Integer || arg instanceof Long || arg instanceof Short) {
					buf.append(".").append(arg.toString());
				} else if(arg instanceof Boolean) {
					buf.append(".").append(arg.toString());
				}
			}
		} else if(cache.keyMode()==KeyMode.ALL) {		//所有参数都加入key,使用参数的toString()值加入key后缀
			for(Object arg:args) {
				buf.append(".").append(arg.toString());
			}
		}
		
		return buf.toString();
	}
}
