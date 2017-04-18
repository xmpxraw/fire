package org.yuantai.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class REValidateUtil {
	
	public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {  
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";  
        Pattern p = Pattern.compile(regExp);  
        Matcher m = p.matcher(str);  
        System.out.println(m.matches());
        return m.matches();  
    }
	
	public static boolean isIdCardLegal(String str) throws PatternSyntaxException {  
        String regExp = "^(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])";  
        Pattern p = Pattern.compile(regExp);  
        Matcher m = p.matcher(str);  
        System.out.println(m.matches());
        return m.matches();  
    }

}
