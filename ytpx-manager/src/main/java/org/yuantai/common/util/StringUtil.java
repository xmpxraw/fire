package org.yuantai.common.util;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 字符处理工具类
 * @author zhanngle
 */
public class StringUtil {

	/**
	 * 判断字符是否为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str==null || str.trim().length()==0;
	}
	
	/**
	 * 截取指定字符串的字节数
	 * @param str		需要截取的字符串
	 * @param byteNum	字节数
	 * @return			截取后的字符串
	 */
	public static String getString(String str,int byteNum) {
		
		if(str==null) return null;
		str=str.replaceAll("\\s", "");
		str=str.replaceAll("\"|'", "");
		if(str.getBytes().length<=byteNum) return str;
		
		StringBuilder sb=new StringBuilder();
		int count=0;
		for(int i=0;i<str.length();i++) {
			String c=str.substring(i,i+1);
			count+=c.getBytes().length;
			if(count<=byteNum) {
				sb.append(c);
			} else {
				break;
			}
		}
		
		return sb.toString();
	}
	
	public static Boolean contains(Object list, String str) {
		if (!(list instanceof List)) {
			return false;
		}
		List<String> rest = (List<String>) list;
		if (CollectionUtils.isEmpty(rest) || StringUtils.isEmpty(str)) {
			return false;
		}
		return rest.contains(str);
	}
}
