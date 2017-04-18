package org.yuantai.common;


/**
 * 全局常量类
 * @ClassName: Constants
 * @Description:
 * @author zhangle
 * @email  lezhang@isoftstone.com
 * @date 2013年9月21日 下午9:30:37
 */
public final class Constants {
	
	//登录验证码存放在session中的key
	public static final String VERIFY_CODE_SESSION_KEY="VERIFY_CODE_SESSION_KEY";
	//登录的用户对象存放在session中的key
	public static final String LOGIN_USER_SESSION_KEY="LOGIN_USER_SESSION_KEY";
	//是否启用登录验证码
	public static final boolean ENABLE_VERIFY_CODE=false;
	
	//登录页面地址
	public static final String LOGIN_PAGE="/login";
	//权限不够,拒绝访问页面
	public static final String ACCESS_DENIED_PAGE="/pages/system/error/access_denied.jsp";
	//系统错误页面
	public static final String SYSTEM_ERROR_PAGE="/pages/system/error/error.jsp";
	
	public static final int YES=1;
	public static final int NO=0;
	//微信openid sessionkey
	public static final String WECHAT_LOGIN_SESSION_KEY = "WECHAT_OPENID";
}
