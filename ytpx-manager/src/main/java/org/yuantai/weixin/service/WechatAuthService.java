package org.yuantai.weixin.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.impl.client.CloseableHttpClient;

public interface WechatAuthService {
	public boolean checkWechatBrowser(String userAgent);
	public String makeAuthRedirectUrl(HttpServletRequest request, String currUr,String codel) throws Exception;
	public String getOpenId(HttpServletRequest request,String code) throws Exception;
	public CloseableHttpClient createSSLClientDefault() throws Exception;
	
}

