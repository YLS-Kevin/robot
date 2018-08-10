package com.yls.projects.robot.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;

public class HttpPostUtil {
	
	private static final int SOCKET_TIME_OUT = 60000;    // 设置读取超时
    private static final int CONNECT_TIME_OUT = 60000;    // 设置连接超时 

    private static CookieStore cookieStore = null;
	
	/**
     * 发送 POST 请求（HTTP），K-V形式
     * @param url
     * @param param
     * @return
     * @throws Exception
     */
    public static String doPost(HttpServletRequest request,String url, Map<String, Object> param) throws Exception{
        String returnString = "";
        
        
       // HttpServletRequest httpRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        
        String sessionId =  RequestContextHolder.getRequestAttributes().getSessionId();
        
        //String sessionId = httpRequest.getRequestedSessionId();
	      
	   // System.out.println("request-->sessionid:"+request.getRequestedSessionId());
	      
	      if (cookieStore == null) {
	    	  cookieStore = new BasicCookieStore();
	      }
	      CloseableHttpClient client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        //CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        
       param.put("sbid", sessionId);
        
        //设置字符集的两种方式
        //new UrlEncodedFormEntity( createParam(params), Charset.forName("UTF-8"))
        HttpEntity entity = new UrlEncodedFormEntity(createParam(param), Consts.UTF_8);
        try {
            HttpPost httpPost = new HttpPost(url);
            
            httpPost.setConfig(getRequestConfig());
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            httpPost.setEntity(entity);
            
            response = client.execute(httpPost);
            returnString = EntityUtils.toString(response.getEntity(), "UTF-8");
            
            EntityUtils.consume(response.getEntity());    //关闭请求
            return returnString;
        } catch(Exception e) {
            e.printStackTrace();
        } 
        
        return returnString;
    }
    
    /**
     * 设置请求的参数值
     * @return
     */
    private static RequestConfig getRequestConfig() {
        return RequestConfig.custom().setSocketTimeout(SOCKET_TIME_OUT).setConnectTimeout(CONNECT_TIME_OUT).build();
    }
    
    /**
     * 设置参数列表
     * @param param
     * @return
     */
    private static List<NameValuePair> createParam(Map<String, Object> param) {
        List<NameValuePair> nvps = new ArrayList <NameValuePair>();
        if(param != null) {
            for(String k : param.keySet()) {
                nvps.add(new BasicNameValuePair(k, param.get(k).toString()));
            }
        }
        return nvps;
    }
    
    
    
    /**
     * 处理url和所带的参数
     *
     * @param url
     * @return
     * @author SunQiChao
     * @Date 2015年9月2日
     */
    public static String loadJSON(String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL oracle = new URL(url);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {

        } catch (IOException e) {
        }
        return json.toString();
    }
    
}
