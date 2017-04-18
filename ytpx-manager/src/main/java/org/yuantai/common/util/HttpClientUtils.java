package org.yuantai.common.util;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 *  依赖的jar包有：commons-lang-2.6.jar、httpclient-4.3.2.jar、httpcore-4.3.1.jar、commons-io-2.4.jar
 * @author chx
 *
 */
public class HttpClientUtils {

    public static final int connTimeout=10000;
    public static final int readTimeout=10000;
    public static final String charset="UTF-8";
    private static HttpClient client = null;
    public static HttpClient httpclient = MySSLSocketFactory.getNewHttpClient();
    public static String registryHost ;
    
    static {
    	Properties props =null;
		try {
			props = SystemUtil.getSpringAppProperties();
			HttpClientUtils.registryHost= props.getProperty("ku8.registry.host");	
			String registryUsername= props.getProperty("ku8.registry.username");	
			String registryPassword= props.getProperty("ku8.registry.password");	
			CredentialsProvider credsProvider = new BasicCredentialsProvider();
    		//写入认证的用户名与密码	
    		UsernamePasswordCredentials creds = new UsernamePasswordCredentials(registryUsername, registryPassword); 
    		//创建验证  
    	     credsProvider.setCredentials(  
    	         new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),   
    	         creds);
    	     //将认证写入到httpClient中  
    	     ((DefaultHttpClient)httpclient).setCredentialsProvider(credsProvider);   
    	
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(128);
        cm.setDefaultMaxPerRoute(128);
          client = HttpClients.custom().setConnectionManager(cm).build();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
    }
    
    public static String postParameters(String url, String parameterStr) throws ConnectTimeoutException, SocketTimeoutException, Exception{
        return post(url,parameterStr,"application/x-www-form-urlencoded",charset,connTimeout,readTimeout);
    }
    
    public static String postParameters(String url, String parameterStr,String charset, Integer connTimeout, Integer readTimeout) throws ConnectTimeoutException, SocketTimeoutException, Exception{
        return post(url,parameterStr,"application/x-www-form-urlencoded",charset,connTimeout,readTimeout);
    }
    
    public static String postParameters(String url, Map<String, String> params) throws ConnectTimeoutException,  
     SocketTimeoutException, Exception {
         return postForm(url, params, null, connTimeout, readTimeout);
     }
    
    public static String postParameters(String url, Map<String, String> params, Integer connTimeout,Integer readTimeout) throws ConnectTimeoutException,  
    SocketTimeoutException, Exception {
         return postForm(url, params, null, connTimeout, readTimeout);
     }
      
    public static String get(String url) throws Exception {  
        return get(url, charset, null, null);  
    }
    
    public static String get(String url, String charset) throws Exception {  
        return get(url, charset, connTimeout, readTimeout);  
    }
    
    public static long delete(String url, String charset) throws Exception {  
        return delete(url, charset, connTimeout, readTimeout);  
    } 
    
    public static long delete(String url) throws Exception {  
        return delete(url, charset, null, null);  
    }
    
    public static String head(String url, String charset) throws Exception {  
        return head(url, charset, connTimeout, readTimeout);  
    } 
    
    public static String head(String url) throws Exception {  
        return head(url, charset, null, null);  
    }

    /** 
     * 发送一个 Post 请求, 使用指定的字符集编码. 
     *  
     * @param url 
     * @param body RequestBody 
     * @param mimeType 例如 application/xml "application/x-www-form-urlencoded" a=1&b=2&c=3
     * @param charset 编码 
     * @param connTimeout 建立链接超时时间,毫秒. 
     * @param readTimeout 响应超时时间,毫秒. 
     * @return ResponseBody, 使用指定的字符集编码. 
     * @throws ConnectTimeoutException 建立链接超时异常 
     * @throws SocketTimeoutException  响应超时 
     * @throws Exception 
     */  
    public static String post(String url, String body, String mimeType,String charset, Integer connTimeout, Integer readTimeout) 
            throws ConnectTimeoutException, SocketTimeoutException, Exception {
        HttpClient client = null;
        HttpPost post = new HttpPost(url);
        String result = "";
        try {
            if (StringUtils.isNotBlank(body)) {
                HttpEntity entity = new StringEntity(body, ContentType.create(mimeType, charset));
                post.setEntity(entity);
            }
            // 设置参数
            Builder customReqConf = RequestConfig.custom();
            if (connTimeout != null) {
                customReqConf.setConnectTimeout(connTimeout);
            }
            if (readTimeout != null) {
                customReqConf.setSocketTimeout(readTimeout);
            }
            post.setConfig(customReqConf.build());

            HttpResponse res;
            if (url.startsWith("https")) {
                // 执行 Https 请求.
            	httpclient = createSSLInsecureClient();
                res = httpclient.execute(post);
            } else {
                // 执行 Http 请求.
                client = HttpClientUtils.client;
                res = client.execute(post);
            }
            result = IOUtils.toString(res.getEntity().getContent(), charset);
        } finally {
            post.releaseConnection();
            if (url.startsWith("https") && client != null&& client instanceof CloseableHttpClient) {
                ((CloseableHttpClient) client).close();
            }
        }
        return result;
    }  
    
    
    /** 
     * 提交form表单 
     *  
     * @param url 
     * @param params 
     * @param connTimeout 
     * @param readTimeout 
     * @return 
     * @throws ConnectTimeoutException 
     * @throws SocketTimeoutException 
     * @throws Exception 
     */  
    public static String postForm(String url, Map<String, String> params, Map<String, String> headers, Integer connTimeout,Integer readTimeout) throws ConnectTimeoutException,  
            SocketTimeoutException, Exception {  
  
        HttpClient client = null;  
        HttpPost post = new HttpPost(url);  
        try {  
            if (params != null && !params.isEmpty()) {  
                List<NameValuePair> formParams = new ArrayList<org.apache.http.NameValuePair>();  
                Set<Entry<String, String>> entrySet = params.entrySet();  
                for (Entry<String, String> entry : entrySet) {  
                    formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));  
                }  
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);  
                post.setEntity(entity);  
            }
            
            if (headers != null && !headers.isEmpty()) {  
                for (Entry<String, String> entry : headers.entrySet()) {  
                    post.addHeader(entry.getKey(), entry.getValue());  
                }  
            }  
            // 设置参数  
            Builder customReqConf = RequestConfig.custom();  
            if (connTimeout != null) {  
                customReqConf.setConnectTimeout(connTimeout);  
            }  
            if (readTimeout != null) {  
                customReqConf.setSocketTimeout(readTimeout);  
            }  
            post.setConfig(customReqConf.build());  
            HttpResponse res = null;  
            if (url.startsWith("https")) {  
                // 执行 Https 请求.  
            	httpclient = createSSLInsecureClient();  
                res = httpclient.execute(post);  
            } else {  
                // 执行 Http 请求.  
                client = HttpClientUtils.client;  
                res = client.execute(post);  
            }  
            return IOUtils.toString(res.getEntity().getContent(), "UTF-8");  
        } finally {  
            post.releaseConnection();  
            if (url.startsWith("https") && client != null  
                    && client instanceof CloseableHttpClient) {  
                ((CloseableHttpClient) client).close();  
            }  
        }  
    } 
    
    
    
    
    /** 
     * 发送一个 GET 请求 
     *  
     * @param url 
     * @param charset 
     * @param connTimeout  建立链接超时时间,毫秒. 
     * @param readTimeout  响应超时时间,毫秒. 
     * @return 
     * @throws ConnectTimeoutException   建立链接超时 
     * @throws SocketTimeoutException   响应超时 
     * @throws Exception 
     */  
    public static String get(String url, String charset, Integer connTimeout,Integer readTimeout) 
            throws ConnectTimeoutException,SocketTimeoutException, Exception { 
        
    	 HttpClient client = null;  
        HttpGet get = new HttpGet(url);  
        
        
        String result = "";  
        try {   	
            // 设置参数  
            Builder customReqConf = RequestConfig.custom();  
            if (connTimeout != null) {  
                customReqConf.setConnectTimeout(connTimeout);  
            }  
            if (readTimeout != null) {  
                customReqConf.setSocketTimeout(readTimeout);  
            }  
            get.setConfig(customReqConf.build());  
  
            HttpResponse res = null;  
  
            if (url.startsWith("https")) {  
                // 执行 Https 请求.  
         //   	httpclient = createSSLInsecureClient();  
                res = httpclient.execute(get);  
            } else {  
                // 执行 Http 请求.  
            	client = HttpClientUtils.client;  
                res = client.execute(get);  
            }  
  
            result = IOUtils.toString(res.getEntity().getContent(), charset);  
        } finally {  
            get.releaseConnection();  
            if (url.startsWith("https") && client != null && client instanceof CloseableHttpClient) {  
                ((CloseableHttpClient) client).close();  
            }  
        }  
        return result;  
    }  
    
    
    
    /** 
     * 发送一个 DELETE 请求 
     *  
     * @param url 
     * @param charset 
     * @param connTimeout  建立链接超时时间,毫秒. 
     * @param readTimeout  响应超时时间,毫秒. 
     * @return 
     * @throws ConnectTimeoutException   建立链接超时 
     * @throws SocketTimeoutException   响应超时 
     * @throws Exception 
     */  
    public static long delete(String url, String charset, Integer connTimeout,Integer readTimeout) 
            throws ConnectTimeoutException,SocketTimeoutException, Exception { 
        
    	 HttpClient client = null;  
        HttpDelete delete = new HttpDelete(url);  
        
        
        long result ;
        try {   	
            // 设置参数  
            Builder customReqConf = RequestConfig.custom();  
            if (connTimeout != null) {  
                customReqConf.setConnectTimeout(connTimeout);  
            }  
            if (readTimeout != null) {  
                customReqConf.setSocketTimeout(readTimeout);  
            }  
            delete.setConfig(customReqConf.build());  
  
            HttpResponse res = null;  
  
            if (url.startsWith("https")) {  
                // 执行 Https 请求.  
         //   	httpclient = createSSLInsecureClient();  
                res = httpclient.execute(delete);  
            } else {  
                // 执行 Http 请求.  
            	client = HttpClientUtils.client;  
                res = client.execute(delete);  
            }  
            result = res.getEntity().getContentLength();
        //    result = IOUtils.toString(res.getEntity().getContent(), charset);  
        } finally {  
        	delete.releaseConnection();  
            if (url.startsWith("https") && client != null && client instanceof CloseableHttpClient) {  
                ((CloseableHttpClient) client).close();  
            }  
        }  
        return result;  
    }  
    
    
    /** 
     * 发送一个 HEAD 请求 
     *  
     * @param url 
     * @param charset 
     * @param connTimeout  建立链接超时时间,毫秒. 
     * @param readTimeout  响应超时时间,毫秒. 
     * @return 
     * @throws ConnectTimeoutException   建立链接超时 
     * @throws SocketTimeoutException   响应超时 
     * @throws Exception 
     */  
    public static String head(String url, String charset, Integer connTimeout,Integer readTimeout) 
            throws ConnectTimeoutException,SocketTimeoutException, Exception { 
        
    	 HttpClient client = null;  
        HttpHead head = new HttpHead(url);  
        
        
        String result =null;
        try {   	
            // 设置参数  
            Builder customReqConf = RequestConfig.custom();  
            if (connTimeout != null) {  
                customReqConf.setConnectTimeout(connTimeout);  
            }  
            if (readTimeout != null) {  
                customReqConf.setSocketTimeout(readTimeout);  
            }  
            head.setConfig(customReqConf.build());  
           /* HashMap<String, String> headers = new HashMap<String, String>(); 
            headers.put("Accept", "application/vnd.docker.distribution.manifest.v2+json"); */
            head.setHeader("Accept", "application/vnd.docker.distribution.manifest.v2+json");//设置header ,registry v2.3版本以上需要这个
            
            HttpResponse res = null;  
  
            if (url.startsWith("https")) {  
                // 执行 Https 请求.  
         //   	httpclient = createSSLInsecureClient();  
                res = httpclient.execute(head);  
            } else {  
                // 执行 Http 请求.  
            	client = HttpClientUtils.client;  
                res = client.execute(head);  
            }  
            
            result = res.toString();
            int i=result.lastIndexOf("Digest:");
            int j=result.indexOf(", Docker-Distribution-Api-Version:");
             result=result.substring(i+7,j).trim();
             if( !result.startsWith("sha256")){
            	 result=null;
             }
            System.out.println(result);
          //  result = IOUtils.toString(res.getEntity().getContent(), charset);  
        } finally {  
        	head.releaseConnection();  
            if (url.startsWith("https") && client != null && client instanceof CloseableHttpClient) {  
                ((CloseableHttpClient) client).close();  
            }  
        }  
        return result;  
    }  
    
    
    /** 
     * 从 response 里获取 charset 
     *  
     * @param ressponse 
     * @return 
     */  
    @SuppressWarnings("unused")  
    private static String getCharsetFromResponse(HttpResponse ressponse) {  
        // Content-Type:text/html; charset=GBK  
        if (ressponse.getEntity() != null  && ressponse.getEntity().getContentType() != null && ressponse.getEntity().getContentType().getValue() != null) {  
            String contentType = ressponse.getEntity().getContentType().getValue();  
            if (contentType.contains("charset=")) {  
                return contentType.substring(contentType.indexOf("charset=") + 8);  
            }  
        }  
        return null;  
    }  
    
    
    
    /**
     * 创建 SSL连接
     * @return
     * @throws GeneralSecurityException
     */
    private static CloseableHttpClient createSSLInsecureClient() throws GeneralSecurityException {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                        public boolean isTrusted(X509Certificate[] chain,String authType) throws CertificateException {
                            return true;
                        }
                    }).build();
            
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {

                        @Override
                        public boolean verify(String arg0, SSLSession arg1) {
                            return true;
                        }

                        @Override
                        public void verify(String host, SSLSocket ssl)
                                throws IOException {
                        }

                        @Override
                        public void verify(String host, X509Certificate cert)
                                throws SSLException {
                        }

                        @Override
                        public void verify(String host, String[] cns,
                                String[] subjectAlts) throws SSLException {
                        }

                    });
            
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
            
        } catch (GeneralSecurityException e) {
            throw e;
        }
    }
    
    public static void main(String[] args) {  
        try {
        //    String str= post("https://localhost:443/ssl/test.shtml","name=12&page=34","application/x-www-form-urlencoded", "UTF-8", 10000, 10000);
            String str= get("https://my.registry.com/v2/centos/manifests/latest","GBK");
            String getAll = get("https://my.registry.com/v2/_catalog");
            
            JSONObject json =JSONObject.parseObject(getAll);
            JSONArray jsonArray = json.getJSONArray("repositories");
          
            /*Map<String,String> map = new HashMap<String,String>();
            map.put("name", "111");
            map.put("page", "222");
            String str= postForm("https://localhost:443/ssl/test.shtml",map,null, 10000, 10000);*/
        //    System.out.println(str);
       //     System.out.println(getAll);
        //    System.out.println(jsonArray);
          /*  if (jsonArray !=null){
            	for (int i =0;i<jsonArray.size();i++){
            		System.out.println(jsonArray.get(i));
            		System.out.println(get("https://my.registry.com/v2/"+jsonArray.get(i)+"/manifests/latest","GBK"));
            	}
            }*/
            String result = head("https://dockertest:12345678@my.registry.com/v2/nginx/manifests/latest","GBK");
       //    long result = delete("https://my.registry.com/v2/nginx/manifests/sha256:8245bb503d0c627f1426c2d979803743d100addd7e5ce92be8d896cd84a6220e","GBK");
    //        System.out.println(result);//result=0  删除成功
          /*  int i=result.lastIndexOf("Digest:");
            int j=result.indexOf(", Docker-Distribution-Api-Version:");
             result=result.substring(i+7,j).trim();*/
          
          //  System.out.println(result);
        } catch (ConnectTimeoutException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SocketTimeoutException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}