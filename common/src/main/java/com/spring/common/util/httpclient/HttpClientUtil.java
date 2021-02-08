package com.spring.common.util.httpclient;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 作者：赵进华
 * @version 创建时间：2019年3月21日 上午9:36:54
 * @Desc类说明:HttpClient工具类，含get,post,put,delete方法
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class HttpClientUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
    // url连接超时,单位ms
    private static final int CONNECT_TIME_OUT = 3000;
    // 从连接池获取Connection 超时时间单位ms
    private static final int CONNECT_REQ_TIME_OUT = 1000;
    // 读取超时时间,单位ms
    private static final int SOCKET_TIME_OUT = 60000;

    /**
     * post方式请求 ，参数放RequestBody里面
     *
     * @param url             访问url地址
     * @param params          post参数
     * @param headerParamsMap 传入header参数
     * @return
     * @throws Exception
     * @author 10148712
     */
    public static String httpPostWithJson(String url, String params, Map<String, String> headerParamsMap)
            throws Exception {
        // 数据必填项校验
        if (StringUtils.isBlank(url)) {
            throw new Exception("url  can't be empty");
        }
        // 数据必填项校验
        if (StringUtils.isBlank(params)) {
            params = "";
        }
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(CONNECT_TIME_OUT).setConnectionRequestTimeout(CONNECT_REQ_TIME_OUT)
                .setSocketTimeout(SOCKET_TIME_OUT).build();
        httpPost.setConfig(requestConfig);
        httpPost.addHeader("Content-type", "application/json; charset=utf-8");
        httpPost.setHeader("Accept", "application/json");
        // 传入的header参数
        if (null != headerParamsMap) {
            for (Map.Entry<String, String> entry : headerParamsMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                httpPost.setHeader(key, value);
            }
        }

        httpPost.setEntity(new StringEntity(params, StandardCharsets.UTF_8));
        try {
            CloseableHttpResponse res = httpClient.execute(httpPost);
            result = EntityUtils.toString(res.getEntity());
            if (res.getStatusLine().getStatusCode() != 200 && res.getStatusLine().getStatusCode() != 201) {
                throw new Exception("SERVICE_POST_ERR");
            }
            res.close();
        } catch (IOException e) {
            String errorMsg = url + ":httpPostWithJSON connect faild";
            throwsException(errorMsg, e, "POST_CONNECT_FAILD");
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                String errorMsg = url + ":close  httpClient faild";
                throwsException(errorMsg, e, "CLOSE_CONNECT_FAILD");
            }
        }

        return result;
    }

    /**
     * delete方式请求,以?参数带过去，不是放RequestBody里面
     *
     * @param url
     * @param paramsMap
     * @param headerParamsMap 传入的header参数
     * @return
     * @throws Exception
     * @author 10148712
     */
    public static String httpDelete(String url, Map<String, String> paramsMap,
                                    Map<String, String> headerParamsMap) throws Exception {
        // 数据必填项校验
        if (StringUtils.isBlank(url)) {
            throw new Exception("url  can't be empty");
        }
        String result = null;
        String baseUrl;
        if (paramsMap != null) {
            List params = new ArrayList();
            for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                params.add(new BasicNameValuePair(key, value));
            }
            baseUrl = url + "?" + URLEncodedUtils.format(params, "UTF-8");
        } else {
            baseUrl = url;
        }

        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpDelete httpDelete = new HttpDelete(baseUrl);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(CONNECT_TIME_OUT).setConnectionRequestTimeout(CONNECT_REQ_TIME_OUT)
                    .setSocketTimeout(SOCKET_TIME_OUT).build();
            httpDelete.setConfig(requestConfig);
            httpDelete.addHeader("Content-type", "application/json; charset=utf-8");
            httpDelete.setHeader("Accept", "application/json");
            // 传入的header参数
            if (headerParamsMap != null) {
                for (Map.Entry<String, String> entry : headerParamsMap.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    httpDelete.setHeader(key, value);
                }
            }

            CloseableHttpResponse res = httpClient.execute(httpDelete);
            if (null != res.getEntity()) {
                result = EntityUtils.toString(res.getEntity());
            }
            if (res.getStatusLine().getStatusCode() != 200 && res.getStatusLine().getStatusCode() != 204) {
                throw new Exception("SERVICE_DELETE_ERR");
            }

            res.close();
        } catch (IOException e) {
            String errorMsg = baseUrl + ":delete connect faild";
            throwsException(errorMsg, e, "DELETE_CONNECT_FAILD");
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                String errorMsg = baseUrl + ":close  httpClient faild";
                throwsException(errorMsg, e, "CLOSE_CONNECT_FAILD");
            }
        }
        return result;
    }

    /**
     * delete方式请求， 参数以?形式拼接在url后面，不是放RequestBody里面
     *
     * @param url
     * @param params
     * @param headerParamsMap 传入的header参数
     * @return
     * @throws Exception
     * @author 10148712
     */
    public static String httpDeleteWithJson(String url, String params, Map<String, String> headerParamsMap)
            throws Exception {
        // 数据必填项校验
        if (StringUtils.isBlank(url)) {
            throw new Exception("url  can't be empty");
        }
        // 数据必填项校验
        if (StringUtils.isBlank(params)) {
            params = "";
        }
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            HttpDeleteWithBody httpDelete = new HttpDeleteWithBody(url);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(CONNECT_TIME_OUT).setConnectionRequestTimeout(CONNECT_REQ_TIME_OUT)
                    .setSocketTimeout(SOCKET_TIME_OUT).build();
            httpDelete.setConfig(requestConfig);
            httpDelete.addHeader("Content-type", "application/json; charset=utf-8");
            httpDelete.setHeader("Accept", "application/json");

            // 传入的header参数
            if (headerParamsMap != null) {
                for (Map.Entry<String, String> entry : headerParamsMap.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    httpDelete.setHeader(key, value);
                }
            }

            httpDelete.setEntity(new StringEntity(params, StandardCharsets.UTF_8));
            CloseableHttpResponse res = httpClient.execute(httpDelete);
            if (null != res.getEntity()) {
                result = EntityUtils.toString(res.getEntity());
            }

            if (res.getStatusLine().getStatusCode() != 200 && res.getStatusLine().getStatusCode() != 204) {
                throw new Exception("SERVICE_DELETE_ERR");
            }

            res.close();
        } catch (IOException e) {
            String errorMsg = url + ":delete connect faild";
            throwsException(errorMsg, e, "DELETE_CONNECT_FAILD");
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                String errorMsg = url + ":close  httpClient faild";
                throwsException(errorMsg, e, "CLOSE_CONNECT_FAILD");
            }
        }
        return result;
    }

    /**
     * get方式请求， 参数以?形式拼接在url后面，不是放RequestBody里面
     *
     * @param url
     * @param paramsMap
     * @param headerParamsMap 传入的header参数
     * @return
     * @throws Exception
     * @author 10148712
     */

    public static String httpGet(String url, Map<String, String> paramsMap,
                                 Map<String, String> headerParamsMap) throws Exception {
        // 数据必填项校验
        if (StringUtils.isBlank(url)) {
            throw new Exception("url  can't be empty");
        }
        String result = null;
        String baseUrl;
        if (paramsMap != null) {
            List params = new ArrayList();
            for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                params.add(new BasicNameValuePair(key, value));
            }
            baseUrl = url + "?" + URLEncodedUtils.format(params, "UTF-8");
        } else {
            baseUrl = url;
        }

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(baseUrl);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(CONNECT_TIME_OUT).setConnectionRequestTimeout(CONNECT_REQ_TIME_OUT)
                .setSocketTimeout(SOCKET_TIME_OUT).build();
        httpGet.setConfig(requestConfig);
        httpGet.addHeader("Content-type", "application/json; charset=utf-8");
        httpGet.setHeader("Accept", "application/json");
        // 传入的header参数
        if (headerParamsMap != null) {
            for (Map.Entry<String, String> entry : headerParamsMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                httpGet.setHeader(key, value);
            }
        }

        try {
            CloseableHttpResponse res = httpClient.execute(httpGet);
            result = EntityUtils.toString(res.getEntity());
            if (res.getStatusLine().getStatusCode() != 200) {
                throw new Exception(result);
            }
            res.close();
        } catch (ClientProtocolException e) {
            String errorMsg = url + ":httpGetWithJSON connect faild";
            throwsException(errorMsg, e, "GET_CONNECT_FAILD");
        } catch (IOException e) {
            String errorMsg = url + ":httpGetWithJSON connect faild";
            throwsException(errorMsg, e, "GET_CONNECT_FAILD");
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                String errorMsg = url + ":close  httpClient faild";
                throwsException(errorMsg, e, "CLOSE_CONNECT_FAILD");
            }
        }

        return result;
    }

    /**
     * put方式请求，参数放RequestBody里面
     *
     * @param url
     * @param params
     * @param headerParamsMap 传入的header参数
     * @return
     * @throws Exception
     * @author 10148712
     */
    public static String httpPutWithJson(String url, String params, Map<String, String> headerParamsMap)
            throws Exception {
        // 数据必填项校验
        if (StringUtils.isBlank(url)) {
            throw new Exception("url  can't be empty");
        }
        // 数据必填项校验
        if (StringUtils.isBlank(params)) {
            params = "";
        }
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(CONNECT_TIME_OUT).setConnectionRequestTimeout(CONNECT_REQ_TIME_OUT)
                .setSocketTimeout(SOCKET_TIME_OUT).build();
        httpPut.setConfig(requestConfig);
        httpPut.addHeader("Content-type", "application/json; charset=utf-8");
        httpPut.setHeader("Accept", "application/json");

        // 传入的header参数
        if (headerParamsMap != null) {
            for (Map.Entry<String, String> entry : headerParamsMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                httpPut.setHeader(key, value);
            }
        }

        httpPut.setEntity(new StringEntity(params, StandardCharsets.UTF_8));
        try {
            CloseableHttpResponse res = httpClient.execute(httpPut);
            result = EntityUtils.toString(res.getEntity());
            if (res.getStatusLine().getStatusCode() != 200 && res.getStatusLine().getStatusCode() != 201) {
                throw new Exception(result);
            }
            res.close();
        } catch (IOException e) {
            String errorMsg = url + ":httpPostWithJSON connect faild";
            throwsException(errorMsg, e, "POST_CONNECT_FAILD");
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                String errorMsg = url + ":close  httpClient faild";
                throwsException(errorMsg, e, "CLOSE_CONNECT_FAILD");
            }
        }

        return result;
    }

    private static void throwsException(String errorMsg, Exception e, String errorCode)
            throws Exception {
        String msg = errorMsg + ".errorMsg:" + e.getMessage();
        logger.error(msg);
        throw new Exception(errorMsg);
    }

    /**
     * @return
     * @Author Liu Pinghui
     * @Description 设置请求参数
     * @Date 2019/8/30 15:55
     * @Param
     **/
    public static HttpEntity<String> setParam(String userName, String pwd, String busObj, String queryCon, Integer pageNo) {

        String param = "{\n" +
                "         \"username\":\"" + userName + "\",\n" +
                "       \"pwd\":\"" + pwd + "\",\n" +
                "         \"orgRangeInfo\":{\"01\":1},\n" +
                "         \"isContain\":1,\n" +
                "         \"pageNo\":" + pageNo + ",\n" +
                "         \"busObj\":\""+(StringUtils.isNotEmpty(busObj) ? busObj : "")+"\",\n" +
                "         \"fieldList\":\"*\",\n" +
                "         \"queryCon\":\"" + (StringUtils.isNotEmpty(queryCon) ? queryCon : "") + "\",\n" +
                "         \"pageSize\":5000\n" +
                "}";
        logger.info("参数：{}", param);
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(mediaType);
        HttpEntity<String> entity = new HttpEntity<String>(param, headers);
        return entity;
    }


}