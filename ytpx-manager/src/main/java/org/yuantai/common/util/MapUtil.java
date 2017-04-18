package org.yuantai.common.util;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.beanutils.BeanUtils;

public class MapUtil {

	public static Map<String,Object> buildMap(Object... entrys) {
		
		return buildMapObject(entrys);
	}
	
	private static Map<String,Object> buildMapObject(Object[] entrys) {
		
		if(entrys==null || entrys.length==0) {
			return Collections.emptyMap();
		}
		if(entrys.length%2==1) {
			throw new IllegalArgumentException("必须是偶数个参数!");
		}
		
		Map<String, Object> map=new TreeMap<String, Object>();
		for(int i=0;i<entrys.length;i+=2) {
			map.put(entrys[i].toString(), entrys[i+1]);
		}
		return map;
	}
	
	public static Map<String,Object> buildBeanMap(Object bean,Object... entrys) {
		
		try {
			Map<String, Object> map=BeanUtils.describe(bean);
			map.putAll(buildMapObject(entrys));
			return map;
		} catch (Exception e) {
			throw new RuntimeException("MapUtil.buildBeanMap() 执行异常,请检查参数是否正确!",e);
		}
	}
}
