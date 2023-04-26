package com.fyp.adp.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpClientUtils {

    private static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

    private static final int STATUS_SUCCESS = 200;

    private static final CloseableHttpClient HTTP_CLIENT;
    private static final RequestConfig       DEFAULT_REQUEST_CONFIG;

    static {
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        DEFAULT_REQUEST_CONFIG = RequestConfig.custom()
                                              // 建立连接超时时间
                                              .setConnectTimeout(10000)
                                              .setConnectionRequestTimeout(3000)
                                              // 等待数据超时时间
                                              .setSocketTimeout(60000).build();
        httpClientBuilder.setDefaultRequestConfig(DEFAULT_REQUEST_CONFIG);
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                                                                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                                                                    .register("https", SSLConnectionSocketFactory.getSocketFactory()).build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        // 最大总连接数
        connectionManager.setMaxTotal(200);
        connectionManager.setDefaultMaxPerRoute(200);

        SocketConfig defaultSocketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
        connectionManager.setDefaultSocketConfig(defaultSocketConfig);

        httpClientBuilder.setConnectionManager(connectionManager);
        httpClientBuilder.setRedirectStrategy(DefaultRedirectStrategy.INSTANCE);
        HTTP_CLIENT = httpClientBuilder.build();
    }

    public static String execute(HttpUriRequest request) {
        try {
            HttpResponse response = HTTP_CLIENT.execute(request);
            if (response.getStatusLine().getStatusCode() != STATUS_SUCCESS) {
                String errorMsg = buildResponseErrorMsg(response);
                logger.error(errorMsg);
                throw new RuntimeException(errorMsg);
            }
            return getHttpEntityContent(response);
        } catch (IOException e) {
            throw new RuntimeException("io异常：", e);
        }
    }

    public static String doGet(String url, Map<String, String> params, Map<String, String> headerParams) {
        logger.debug("get 请求 url={}", url);
        HttpGet             httpGet    = new HttpGet();
        List<NameValuePair> formparams = setHttpParams(params);
        String              param      = URLEncodedUtils.format(formparams, "UTF-8");
        httpGet.setURI(URI.create(url + "?" + param));
        setHeaders(httpGet, headerParams);
        return execute(httpGet);
    }

    public static String doPost(String url, Map<String, Object> params, Map<String, String> headerParams) {
        logger.debug("post 请求 url={}", url);
        HttpPost httpPost = new HttpPost();
        httpPost.setURI(URI.create(url));
        httpPost.setEntity(new StringEntity(JSONObject.toJSONString(params), ContentType.APPLICATION_JSON));
        setHeaders(httpPost, headerParams);
        return execute(httpPost);
    }

    public static String doPost(String url, Map<String, Object> params) {
        return doPost(url, params, Maps.newHashMap());
    }

    public static String doGet(String url, Map<String, String> params) {
        return doGet(url, params, Maps.newHashMap());
    }

    private static void setHeaders(HttpRequest httpRequest, Map<String, String> headerParams) {
        if (MapUtils.isEmpty(headerParams)) {
            return;
        }
        for (Map.Entry<String, String> entry : headerParams.entrySet()) {
            httpRequest.addHeader(entry.getKey(), entry.getValue());
        }
    }

    /**
     * 设置请求参数
     *
     * @param paramMap
     * @return
     */
    private static List<NameValuePair> setHttpParams(Map<String, String> paramMap) {
        List<NameValuePair>            formparams = new ArrayList<NameValuePair>();
        Set<Map.Entry<String, String>> set        = paramMap.entrySet();
        for (Map.Entry<String, String> entry : set) {
            formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return formparams;
    }

    /**
     * 获得响应HTTP实体内容
     *
     * @param response
     * @return
     * @throws IOException
     */
    private static String getHttpEntityContent(HttpResponse response) throws IOException {
        return EntityUtils.toString(response.getEntity());
    }

    private static String buildResponseErrorMsg(HttpResponse response) throws IOException {
        StringBuilder msg = new StringBuilder();
        msg.append("http响应状态异常，status：")
           .append(response.getStatusLine().toString())
           .append("，响应结果：")
           .append(StringUtils.substring(getHttpEntityContent(response), 0, 5000));
        return msg.toString();
    }

}
