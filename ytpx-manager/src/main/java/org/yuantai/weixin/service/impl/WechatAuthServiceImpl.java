/**
 * Copyright (c) 2016, YYD All Rights Reserved.
 */
package org.yuantai.weixin.service.impl;

import java.net.URI;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Properties;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.yuantai.common.util.SystemUtil;
import org.yuantai.system.aop.Logging;
import org.yuantai.weixin.service.WechatAuthService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * description: <br/>
 * date: 2016年1月21日 下午3:36:39 <br/>
 *
 * @author jack
 */
@Logging
@Service
public class WechatAuthServiceImpl implements WechatAuthService {
	private static Logger log = LoggerFactory.getLogger(WechatAuthServiceImpl.class);

	// https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect
	/** 微信的OAtuth2授权地址 */
	private String wechatOauthUrl = "https://open.weixin.qq.com/connect/oauth2/authorize";
	/** 微信公众号应用ID */
	// private String appId = "wxa1588360e25149c1";
	/** 微信公众号的密码 */
	// private String secret = "19b74af1870508d5284f48a5c7a1e08a";
	/** 微信回调校验地址 */
	private String wydOauthUrl = "/school/weixin/index1";

	public boolean checkWechatBrowser(String userAgent) {
		int index = userAgent.indexOf("MicroMessenger");
		if (index < 0) {
			return false;
		}

		return true;
	}

	public String makeAuthRedirectUrl(HttpServletRequest request, String currUrl,String code) throws Exception {
		String appId = (String) request.getSession().getAttribute("WECHAT_APPID");
		String secret = (String) request.getSession().getAttribute("WECHAT_SECRET");
		Properties props = SystemUtil.getSpringAppProperties();
		String domian = props.getProperty("system.host.domain"); // 获取配置的域名
		String baseUrl="http://"+code+domian.substring(3, domian.length());
		StringBuilder redirectUrl = new StringBuilder();
		redirectUrl.append(wechatOauthUrl);
		redirectUrl.append("?appid=").append(appId);
		StringBuilder authUrl = new StringBuilder();
		// authUrl.append("http://wan1dian.100am.cn");
		authUrl.append(baseUrl);
		authUrl.append(wydOauthUrl);
		authUrl.append("?saveurl=");
		try {
			String tempurl = URLEncoder.encode(currUrl, "UTF-8");
			authUrl.append(tempurl);
		} catch (Exception ex) {
			log.error("转换地址时出现问题!", ex);
			authUrl.append(currUrl);
		}
		redirectUrl.append("&redirect_uri=").append(URLEncoder.encode(authUrl.toString(), "UTF-8"));
		redirectUrl.append("&response_type=code");
		redirectUrl.append("&scope=snsapi_base"); // 静默授权
		redirectUrl.append("&state=");
		redirectUrl.append("#wechat_redirect");
		System.out.println(redirectUrl.toString());
		return redirectUrl.toString();
	}

	public String getOpenId(HttpServletRequest request, String code) throws Exception {
		String appId = (String) request.getSession().getAttribute("WECHAT_APPID");
		String secret = (String) request.getSession().getAttribute("WECHAT_SECRET");
		log.debug("WechatAuthService:getOpenId()::[appId={}]  [secret={}]  [code={}]", appId, secret, code);

		CloseableHttpClient httpclient = createSSLClientDefault();
		try {
			URI uri = new URIBuilder() //
					.setScheme("https") //
					.setHost("api.weixin.qq.com") //
					.setPath("/sns/oauth2/access_token") //
					.setParameter("appid", appId) //
					.setParameter("secret", secret) //
					.setParameter("code", code) //
					.setParameter("grant_type", "authorization_code") //
					.build();
			HttpGet httpget = new HttpGet(uri);

			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				HttpEntity entity = response.getEntity();

				if (entity != null) {
					byte[] buff = EntityUtils.toByteArray(entity);
					if (log.isDebugEnabled()) {
						log.debug("[wechat:accessToken] response:[" + new String(buff) + "]");
					}

					JSONObject json = JSON.parseObject(new String(buff));
					if (json.containsKey("errcode")) {
						throw new Exception(json.getString("errmsg"));
					}

					String accessToken = json.getString("access_token");
					String openId = json.getString("openid");

					return accessToken + "|" + openId;
				} else {
					throw new Exception("网页授权失败!");
				}
			} finally {
				response.close();
			}
		} finally {
			if (httpclient != null) {
				httpclient.close();
			}
		}
	}

	public CloseableHttpClient createSSLClientDefault() throws Exception {
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				// 信任所有
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
			return HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (Exception e) {
			throw new Exception("生成SSL客户端出现问题！", e);
		}
	}

	public static void main(String[] args) throws Exception {
		String userAgent = "Mozilla/5.0 (iPhone; CPU iPhone OS 6_1_3 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Mobile/10B329 MicroMessenger/5.0.1";
		System.out.println(userAgent.indexOf("MicroMessenger"));
		System.out.println(userAgent.substring(userAgent.indexOf("MicroMessenger") + "MicroMessenger".length() + 1));

		System.out.println(URLEncoder.encode("http://www.100am.cn/abc?a=1&b=2", "UTF-8"));
	}
}
