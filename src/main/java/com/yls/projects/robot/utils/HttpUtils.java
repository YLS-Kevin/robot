package com.yls.projects.robot.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.yls.frame.common.config.Global;
import com.yls.frame.common.utils.StringUtils;

/**
 * 
 * @author zzq
 * @Date 2016年12月21日 上午10:05:13
 */
public class HttpUtils {

	private final static Logger logger = Logger.getLogger(HttpUtils.class);

	public static String get(String url) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(1000)
				.setSocketTimeout(15000).build();
		httpGet.setConfig(requestConfig);
		CloseableHttpResponse response = null;
		String result = null;
		try {
			response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity);
			EntityUtils.consume(entity);
		} catch (IOException e1) {
			logger.error("get请求失败, url:" + url);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				logger.error("http--get请求,关闭response失败", e);
			}
		}
		return result;
	}

	public static String post(String url, List<NameValuePair> nvps) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(5000).setSocketTimeout(5000).build();
		httpPost.setConfig(requestConfig);
		CloseableHttpResponse response = null;
		String result = null;
		try {
			if (null != nvps) {
				httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
			}
			response = httpclient.execute(httpPost);
			int status = response.getStatusLine().getStatusCode();
			if (status == 200) {
				HttpEntity entity = response.getEntity();
				result = EntityUtils.toString(entity);
				// EntityUtils.consume(entity);
			}
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			result = "{\"key_code\":-1,\"err_msg\":\"call api error\",\"success\":\"false\"}";
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			result = "{\"key_code\":-1,\"err_msg\":\"call api error\",\"success\":\"false\"}";
			e.printStackTrace();
		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				result = "{\"key_code\":-1,\"err_msg\":\"call api error\",\"success\":\"false\"}";
				logger.error("http--post请求,关闭response失败", e);
			}
		}
		return result;
	}

	public static void main(String[] args) {
		String result = HttpUtils.get(Global.getConfig("forecast_addr") + "/login?userName="
				+ Global.getConfig("forecast_user") + "&secret=" + Global.getConfig("forecast_secret"));
		System.out.println(result);
	}

	/**
	 * 向服务器发送get请求
	 * 
	 * @author zzq 2016-11-27 12:28:41
	 */
	public static String sendGet(String httpUrl, String encode) throws Exception {
		// url 解码
		// httpUrl = Encodes.urlDecode(httpUrl);

		HttpURLConnection httpConn = null;
		InputStream in = null;
		httpConn = (HttpURLConnection) new URL(httpUrl).openConnection();
		httpConn.setRequestMethod("GET");
		// httpConn.setRequestProperty("Accept", "application/json");
		httpConn.setRequestProperty("Accept-Charset", "UTF-8");
		httpConn.setRequestProperty("contentType", "UTF-8");
		httpConn.setConnectTimeout(5000);
		httpConn.connect();
		int responseCode = httpConn.getResponseCode();

		logger.debug("Sending 'POST' request to URL : " + httpUrl);
		logger.debug("Response Code : " + responseCode);

		if (responseCode == HttpURLConnection.HTTP_OK) {
			in = httpConn.getInputStream();
			return readFromInputStream(in, encode);
		} else if (responseCode == HttpURLConnection.HTTP_VERSION) {
			logger.error("505错误");
		} else if (responseCode == HttpURLConnection.HTTP_INTERNAL_ERROR) {
			logger.error("500错误");
		} else if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST) {
			logger.error("400错误");
		} else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
			logger.error("404错误");
		} else {
			logger.error(responseCode + "错误");
		}
		return null;
	}

	/**
	 * 向服务器发送post请求
	 * 
	 * @author zzq 2016-11-27 12:28:03
	 */
	public static String sendPost(String httpUrl, String params, String encode) throws Exception {
		HttpURLConnection httpConn = null;
		InputStream in = null;
		httpConn = (HttpURLConnection) new URL(httpUrl).openConnection();
		httpConn.setRequestMethod("POST");
		if (params == null)
			params = "";
		httpConn.setDoOutput(true);
		httpConn.setConnectTimeout(5000);
		OutputStream out = httpConn.getOutputStream();
		out.write(params.getBytes(encode));
		out.flush();
		out.close();
		int responseCode = httpConn.getResponseCode();

		logger.debug("Sending 'POST' request to URL : " + httpUrl);
		logger.debug("Post parameters : " + params);
		logger.debug("Response Code : " + responseCode);

		if (responseCode == HttpURLConnection.HTTP_OK) {
			in = httpConn.getInputStream();
			return readFromInputStream(in, encode);
		}
		return null;
	}

	/**
	 * 将输入流转化成字符串
	 * 
	 * @author zzq 2016-11-27 12:27:17
	 */
	private static String readFromInputStream(InputStream in, String encoding) {
		String inputLine = "";
		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(in, encoding));
			StringBuffer response = new StringBuffer();
			while ((inputLine = reader.readLine()) != null) {
				response.append(inputLine);
			}

			in.close();
			return response.toString();
		} catch (UnsupportedEncodingException e) {
			logger.error("不支持" + encoding + "编码", e);
		} catch (IOException e) {
			logger.error("输入流转化成字符串错误!", e);
		} finally {
			if (in != null) {
				try {
					in.close();
					in = null;
				} catch (IOException e) {
					logger.error("关闭输入流失败!", e);
				}
			}
		}
		return null;
	}

}
