package org.yuantai.common.util;

import org.quartz.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BeanUtils {

	private static Logger log = LoggerFactory.getLogger(BeanUtils.class);
	
	public static Class<?> getClass(String className){
		Class<?> clazz = null;
		
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			log.info( "指定的类[{}]未定义!", className);
		}
		
		return clazz;
	}
	
	/**
	 * 指定类名的类是否实现了接口
	 * */
	public static boolean isImplementationFrom(String clazzName,Class<?> interfaceClass) {
		boolean result = true;		
		if( !StringUtil.isEmpty(clazzName) && interfaceClass !=null ){
			Class<?> clazz = getClass(clazzName);
			result = isImplementationFrom( clazz , interfaceClass );
		}
		return result;
	}
	
	/**
	 * 判断类是否实现了接口
	 * */
	public static boolean isImplementationFrom(Class<?> clazz,Class<?> interfaceClass) {
		boolean result = false;
		
		String interfaceName = interfaceClass.getName();
		
		for(Class<?> superInterface : clazz.getInterfaces()  ){
			if( superInterface.getName().equals( interfaceName ) ){
				result = true;
				break;
			}
		}
		return result;
	}
	
	public static void main(String[] args){
		
	//	System.out.println( isImplementationFrom(  TestJob.class.getName(), Job.class  ) ); 
		
	}
	
	
}
