package com.spring.common.util.httpclient;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

/**
* @author 作者：赵进华 
* @version 创建时间：2019年3月21日 上午9:36:54
* @Desc类说明:httpclient连接池网络请求
*/
public class HttpClientUtils {
	
	private static PoolingHttpClientConnectionManager httpClientConnectionManager = null; 
	private int maxTotal = 200;
	private int defaultMaxPerRoute  = 20;
	private static final int TIME_OUT = 10 * 1000;
	private static final int SOCKET_TIMEOUT = 60 * 1000;
	
	private HttpClientUtils(){
		init();
	}
	
	private HttpClientUtils(int maxTotal, int defaultMaxPerRoute){
		this.maxTotal = maxTotal;
		this.defaultMaxPerRoute = defaultMaxPerRoute;
		init();
	}
	
	private void init() {
		try {
			SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(null,new TrustStrategy(){
				@Override
				public boolean isTrusted(X509Certificate[] arg0, String arg1)
						throws CertificateException {
					// TODO Auto-generated method stub
					return true;
				}
			 }).build();
			 HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.getDefaultHostnameVerifier(); 
			 ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
			 LayeredConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, hostnameVerifier);
				Registry<ConnectionSocketFactory> registry = RegistryBuilder
			            .<ConnectionSocketFactory> create().register("http", plainsf)
			            .register("https", sslsf).build();
				httpClientConnectionManager = new PoolingHttpClientConnectionManager(
			            registry);
				// 将最大连接数增加
				httpClientConnectionManager.setMaxTotal(maxTotal);
				// 将每个路由基础的连接增加
				httpClientConnectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute );
		} catch (Exception e) {
		}
	}
	
	private static class HttpClientUtilsHolder{
		private static final HttpClientUtils INSTANCE = new HttpClientUtils();
	}
	public static HttpClientUtils getInstance() {
        return HttpClientUtilsHolder.INSTANCE;
    }
	
	// 请求重试处理
    HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
        @Override
		public boolean retryRequest(IOException exception,
									int executionCount, HttpContext context) {
            if (executionCount >= 2) {// 如果已经重试了2次，就放弃
                return false;
            }
            if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
                return true;
            }
            if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
                return false;
            }
            if (exception instanceof InterruptedIOException) {// 超时
                return false;
            }
            if (exception instanceof UnknownHostException) {// 目标服务器不可达
                return false;
            }
            if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
                return false;
            }
            if (exception instanceof SSLException) {// SSL握手异常
                return false;
            }

            HttpClientContext clientContext = HttpClientContext
                    .adapt(context);
            HttpRequest request = clientContext.getRequest();
            // 如果请求是幂等的，就再次尝试
            return !(request instanceof HttpEntityEnclosingRequest);
        }
    };
	
    /**
	 * 创建HttpClient对象
	 */
	public CloseableHttpClient createHttpClient() {
	    CloseableHttpClient httpClient = HttpClients.custom()
	            .setConnectionManager(httpClientConnectionManager)
	            .setRetryHandler(httpRequestRetryHandler).build();
	    return httpClient;
	}
	
	public static CloseableHttpClient getHttpClient() {
		return HttpClientUtils.getInstance().createHttpClient();
	}
    
    
    
	private static void config(HttpRequestBase httpRequestBase,int timeOut) {
	    //配置请求的超时设置
	    RequestConfig requestConfig = RequestConfig.custom()
	            .setConnectionRequestTimeout(timeOut)
	            .setConnectTimeout(timeOut).setSocketTimeout(SOCKET_TIMEOUT).build();
	    httpRequestBase.setConfig(requestConfig);
	}
	
	private static void config(HttpRequestBase httpRequestBase) {
	    // 配置请求的超时设置
	    RequestConfig requestConfig = RequestConfig.custom()
	            .setConnectionRequestTimeout(TIME_OUT)
	            .setConnectTimeout(TIME_OUT).setSocketTimeout(SOCKET_TIMEOUT).build();
	    httpRequestBase.setConfig(requestConfig);
	}

	private static void setPostParams(HttpPost httpost, Map<String, Object> params) {
	    List<NameValuePair> nvps = new ArrayList<NameValuePair>();
	    Set<String> keySet = params.keySet();
	    for (String key : keySet) {
	        nvps.add(new BasicNameValuePair(key, params.get(key).toString()));
	    }
	    try {
	        httpost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
	    } catch (UnsupportedEncodingException e) {
	    }
	}

	public static String post(String url, Map<String, Object> params,int timeOut) throws Exception {
	    HttpPost httppost = new HttpPost(url);
	    config(httppost,timeOut);
	    setPostParams(httppost, params);
	    CloseableHttpResponse response = null;
	    try {
	        response = getHttpClient().execute(httppost,
	                HttpClientContext.create());
	        HttpEntity entity = response.getEntity();
	        String result = EntityUtils.toString(entity, "utf-8");
	        EntityUtils.consume(entity);
	        return result;
	    } finally {
	        try {
	            if (response != null) {
					response.close();
				}
	        } catch (IOException e) {
	        }
	    }
	}
	
	public static String post(String url, Map<String, Object> params) throws Exception {
	    HttpPost httppost = new HttpPost(url);
	    config(httppost);
	    setPostParams(httppost, params);
	    CloseableHttpResponse response = null;
	    try {
	        response = getHttpClient().execute(httppost,
	                HttpClientContext.create());
	        HttpEntity entity = response.getEntity();
	        String result = EntityUtils.toString(entity, "utf-8");
	        EntityUtils.consume(entity);
	        return result;
	    } finally {
	        try {
	            if (response != null) {
					response.close();
				}
	        } catch (IOException e) {
	        }
	    }
	}
	
	
	public static String seedWebService(String postUrl, String soapXml,String soapAction){
		HttpPost httpPost = new HttpPost(postUrl);  
		config(httpPost);
	    CloseableHttpResponse response = null;
	    try {
	    	httpPost.setHeader("Content-Type", "text/xml;charset=UTF-8");  
            httpPost.setHeader("SOAPAction", soapAction);  
            StringEntity data = new StringEntity(soapXml,
                    StandardCharsets.UTF_8);
            httpPost.setEntity(data); 
	        response = getHttpClient().execute(httpPost,
	                HttpClientContext.create());
	        HttpEntity entity = response.getEntity();
	        String result = EntityUtils.toString(entity, "utf-8");
	        EntityUtils.consume(entity);
	        return result;
	    } catch(Exception e){
	    } finally {
	        try {
	            if (response != null) {
					response.close();
				}
	        } catch (IOException e) {
	        }
	    }
	    return null;
	}
	
	public static String get(String url,int timeOut) {
	    HttpGet httpget = new HttpGet(url);
	    config(httpget,timeOut);
	    CloseableHttpResponse response = null;
	    try {
	        response = getHttpClient().execute(httpget,
	                HttpClientContext.create());
	        HttpEntity entity = response.getEntity();
	        String result = EntityUtils.toString(entity, "utf-8");
	        EntityUtils.consume(entity);
	        return result;
	    } catch (IOException e) {
	    } finally {
	        try {
	            if (response != null) {
					response.close();
				}
	        } catch (IOException e) {
	        }
	    }
	    return null;
	}
	
	public static String get(String url) {
	    HttpGet httpget = new HttpGet(url);
	    config(httpget);
	    CloseableHttpResponse response = null;
	    try {
	        response = getHttpClient().execute(httpget,
	                HttpClientContext.create());
	        HttpEntity entity = response.getEntity();
	        String result = EntityUtils.toString(entity, "utf-8");
	        EntityUtils.consume(entity);
	        return result;
	    } catch (IOException e) {
	    } finally {
	        try {
	            if (response != null) {
					response.close();
				}
	        } catch (IOException e) {
	        }
	    }
	    return null;
	}
	
	public static String postUac(String url, Map<String, Object> params,int timeOut) {
	    HttpPost httppost = new HttpPost(url);
	    config(httppost,timeOut);
	    String jsonStr = JSONObject.toJSONString(params);
	    StringEntity entityStr = new StringEntity(jsonStr,"UTF-8");
	    entityStr.setContentType("application/json");
	    httppost.setEntity(entityStr);
	    CloseableHttpResponse response = null;
	    try {
	        response = getHttpClient().execute(httppost,
	                HttpClientContext.create());
	        HttpEntity entity = response.getEntity();
	        String result = EntityUtils.toString(entity, "utf-8");
	        EntityUtils.consume(entity);
	        return result;
	    } catch (Exception e) {
	    } finally {
	        try {
	            if (response != null) {
					response.close();
				}
	        } catch (IOException e) {
	        }
	    }
	    return null;
	}

	public static void main(String[] args) throws Exception {
		String url = "http://10.17.178.12:8080/UAC_CJ507_MngServer/uacConfig/getUacConfigByCode.serv";
		Map<String,Object> map = new HashMap<String,Object>(16);
		map.put("code", "deviceRelieveBinding");

		String str = postUac(url,map,5000);
		System.out.println(str);
	}
	   
}
