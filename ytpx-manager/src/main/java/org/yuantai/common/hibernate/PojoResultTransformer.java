package org.yuantai.common.hibernate;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;

import org.apache.commons.beanutils.ConvertUtils;
import org.hibernate.transform.BasicTransformerAdapter;

/**
 * hibernate的扩展类,用于将一行记录转换为指定的pojo类
 * @ClassName: PojoResultTransformer
 * @Description:
 * @author zhangle
 * @email  zhanngle@163.com
 * @date 2015年8月18日 下午7:21:25
 */
public class PojoResultTransformer extends BasicTransformerAdapter {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LogManager.getLogger(PojoResultTransformer.class.getName());

	private static final long serialVersionUID = 1L;
	private static Map<Class<?>,Map<String,Method>> classmap=new HashMap<Class<?>,Map<String,Method>>();
	
	private final Class<?> resultClass;
	
	public PojoResultTransformer(Class<?> resultClass) {
		if ( resultClass == null ) {
			throw new IllegalArgumentException( "resultClass cannot be null" );
		}
		this.resultClass = resultClass;
	}
	
	/**
	 * 将一行结果转换为pojo对象
	 */
	@Override
	public Object transformTuple(Object[] tuple, String[] aliases) {
		
		Object result=null;
		try {
			result = resultClass.newInstance();
			Map<String,Method> methodmap=getMethodMap(resultClass);
			for ( int i = 0; i < aliases.length; i++ ) {
				Method setter=methodmap.get(aliases[i]);
				if(setter==null) continue;
				try {
					setter.invoke(result, tuple[i]);
				} catch (Exception e) {
					try {
						Object value=ConvertUtils.convert(tuple[i], setter.getParameterTypes()[0]);
						setter.invoke(result, value);
					} catch (Exception e1) {}
				}
			}
		} catch (Exception e) {
			logger.error("PojoResultTransformer.transformTuple(Object[], String[])",e);
		}

		return result;
	}
	
	/**
	 * 获取所有字段的的setter方法
	 * @param resultClass
	 * @return
	 */
	private static Map<String,Method> getMethodMap(Class<?> resultClass) {
		
		Map<String,Method> methodmap=classmap.get(resultClass);
		if(methodmap!=null) {
			return methodmap;
		}
		
		methodmap=new HashMap<String,Method>();
		classmap.put(resultClass, methodmap);
		
		Class<?> superClass=resultClass;
		while(superClass!=Object.class) {
			Field[] fields=superClass.getDeclaredFields();
			for(Field f:fields) {
				if(Modifier.isStatic(f.getModifiers()) || !Modifier.isPrivate(f.getModifiers())) {
					continue;
				}
				
				String name=f.getName();
				Column col=f.getAnnotation(Column.class);
				if(col!=null) {
					methodmap.put(col.name().replaceAll("`", ""), setter(resultClass,f));
				} else {
					methodmap.put(name, setter(resultClass,f));
				}
			}
			superClass=superClass.getSuperclass();
		}
		
		return methodmap;
	}
	
	/**
	 * 获取字段的setter方法
	 * @param resultClass
	 * @param f
	 * @return
	 */
	private static Method setter(Class<?> resultClass,Field f) {
		String name=f.getName();
		String methodName="set"+name.substring(0,1).toUpperCase()+name.substring(1);
		try {
			Method method=resultClass.getMethod(methodName, f.getType());
			return method;
		} catch (Exception e) {
			return null;
		}
	}
	
}
