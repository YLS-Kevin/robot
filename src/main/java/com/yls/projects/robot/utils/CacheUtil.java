package com.yls.projects.robot.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.yls.frame.common.utils.StringUtils;
import com.yls.projects.common.cache.ActionEnum;

public class CacheUtil {
	
	private static Logger logger = LoggerFactory.getLogger(CacheUtil.class);
	
	public static String getToken(String clinetId , String  clientSecret, String tokenUrl) {
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		NameValuePair grant_type = new BasicNameValuePair("grant_type", "client_credentials");
		NameValuePair client_id = new BasicNameValuePair("client_id", clinetId);
		NameValuePair client_secret = new BasicNameValuePair("client_secret", clientSecret);
		NameValuePair scope = new BasicNameValuePair("scope", "read write");
		pairs.add(grant_type);
		pairs.add(client_id);
		pairs.add(client_secret);
		pairs.add(scope);
		String str = HttpUtils.post(tokenUrl, pairs);
		Map<String, Object> map = JSON.parseObject(str, Map.class);
		String token = map.get("access_token") == null ? "" : map.get("access_token").toString();
		if (StringUtils.isBlank(token)) {
			 str = HttpUtils.post(tokenUrl, pairs);
			 map = JSON.parseObject(str, Map.class);
			 token = map.get("access_token") == null ? "" : map.get("access_token").toString();
		}
		return token;
	}

	public static String postResult(String action, String token, String cacheUrl, String updateData, String cidStr, String dialogId) {
		String result = null;
		if (action.equals(ActionEnum.ADD_DIALOG.getCode())) {
			List<NameValuePair> pairs1= new ArrayList<NameValuePair>();
			NameValuePair msg = new BasicNameValuePair("id", updateData);
			NameValuePair cid = new BasicNameValuePair("cid", cidStr);
			NameValuePair tokenPair= new BasicNameValuePair("token", token);
			pairs1.add(msg);
			pairs1.add(cid);
			pairs1.add(tokenPair);
			result = HttpUtils.post(cacheUrl + "addDialog.do", pairs1);
		}
		
		if (action.equals(ActionEnum.UPDATE_DIALOG.getCode())) {
			List<NameValuePair> pairs1= new ArrayList<NameValuePair>();
			NameValuePair msg = new BasicNameValuePair("dm", updateData);
			NameValuePair cid = new BasicNameValuePair("cid", cidStr);
			NameValuePair tokenPair= new BasicNameValuePair("token", token);
			NameValuePair id= new BasicNameValuePair("id", dialogId);
			pairs1.add(msg);
			pairs1.add(cid);
			pairs1.add(tokenPair);
			pairs1.add(id);
			result = HttpUtils.post(cacheUrl + "updateDialog.do", pairs1);
		}
		
		if (action.equals(ActionEnum.DELETE_DIALOG.getCode())) {
			List<NameValuePair> pairs1= new ArrayList<NameValuePair>();
			NameValuePair msg = new BasicNameValuePair("id", updateData);
			NameValuePair cid = new BasicNameValuePair("cid", cidStr);
			NameValuePair tokenPair= new BasicNameValuePair("token", token);
			pairs1.add(msg);
			pairs1.add(cid);
			pairs1.add(tokenPair);
			result = HttpUtils.post(cacheUrl + "delDialog.do", pairs1);
		}
		
		if (action.equals(ActionEnum.ADD_DIALOG_TYPE.getCode())) {
			List<NameValuePair> pairs1= new ArrayList<NameValuePair>();
			NameValuePair msg = new BasicNameValuePair("id", updateData);
			NameValuePair cid = new BasicNameValuePair("cid", cidStr);
			NameValuePair tokenPair= new BasicNameValuePair("token", token);
			pairs1.add(msg);
			pairs1.add(cid);
			pairs1.add(tokenPair);
			result = HttpUtils.post(cacheUrl + "addDialogType.do", pairs1);
		}
		
		if (action.equals(ActionEnum.UPDATE_DIALOG_TYPE.getCode())) {
			List<NameValuePair> pairs1= new ArrayList<NameValuePair>();
			NameValuePair msg = new BasicNameValuePair("dt", updateData);
			NameValuePair cid = new BasicNameValuePair("cid", cidStr);
			NameValuePair tokenPair= new BasicNameValuePair("token", token);
			NameValuePair id= new BasicNameValuePair("id", dialogId);
			pairs1.add(msg);
			pairs1.add(cid);
			pairs1.add(tokenPair);
			pairs1.add(id);
			result = HttpUtils.post(cacheUrl + "updateDialogType.do", pairs1);
		}
		
		if (action.equals(ActionEnum.ADD_DIALOG_EXP.getCode())) {
			List<NameValuePair> pairs1= new ArrayList<NameValuePair>();
			NameValuePair msg = new BasicNameValuePair("id", updateData);
			NameValuePair cid = new BasicNameValuePair("cid", cidStr);
			NameValuePair tokenPair= new BasicNameValuePair("token", token);
			pairs1.add(msg);
			pairs1.add(cid);
			pairs1.add(tokenPair);
			result = HttpUtils.post(cacheUrl + "addDialogExp.do", pairs1);
		}
		
		if (action.equals(ActionEnum.UPDATE_DIALOG_EXP.getCode())) {
			List<NameValuePair> pairs1= new ArrayList<NameValuePair>();
			NameValuePair msg = new BasicNameValuePair("de", updateData);
			NameValuePair cid = new BasicNameValuePair("cid", cidStr);
			NameValuePair tokenPair= new BasicNameValuePair("token", token);
			NameValuePair id= new BasicNameValuePair("id", dialogId);
			pairs1.add(msg);
			pairs1.add(cid);
			pairs1.add(tokenPair);
			pairs1.add(id);
			result = HttpUtils.post(cacheUrl + "updateDialogExp.do", pairs1);
		}
		
		if (action.equals(ActionEnum.ADD_DIALOG_MODEL.getCode())) {
			List<NameValuePair> pairs1= new ArrayList<NameValuePair>();
			NameValuePair cid = new BasicNameValuePair("cid", cidStr);
			NameValuePair tokenPair= new BasicNameValuePair("token", token);
			NameValuePair id= new BasicNameValuePair("id", dialogId);
			pairs1.add(cid);
			pairs1.add(tokenPair);
			pairs1.add(id);
			result = HttpUtils.post(cacheUrl + "addDialogTypeSelf.do", pairs1);
		}
		
		if (action.equals(ActionEnum.DELETE_DIALOG_MODEL.getCode())) {
			List<NameValuePair> pairs1= new ArrayList<NameValuePair>();
			NameValuePair cid = new BasicNameValuePair("cid", cidStr);
			NameValuePair tokenPair= new BasicNameValuePair("token", token);
			NameValuePair id= new BasicNameValuePair("id", dialogId);
			pairs1.add(cid);
			pairs1.add(tokenPair);
			pairs1.add(id);
			result = HttpUtils.post(cacheUrl + "delDialogTypeSelf.do", pairs1);
		}
		
		if (action.equals(ActionEnum.UPDATE_INTERFACE.getCode())) {
			List<NameValuePair> pairs1= new ArrayList<NameValuePair>();
			NameValuePair msg = new BasicNameValuePair("dm", updateData);
			NameValuePair cid = new BasicNameValuePair("cid", cidStr);
			NameValuePair tokenPair= new BasicNameValuePair("token", token);
			NameValuePair id= new BasicNameValuePair("id", dialogId);
			pairs1.add(msg);
			pairs1.add(cid);
			pairs1.add(tokenPair);
			pairs1.add(id);
			result = HttpUtils.post(cacheUrl + "updateDialogInterData.do", pairs1);
		}
		if(logger.isDebugEnabled())
			logger.debug(result);
		return result;
	}
}
