package com.yls.projects.robot.service.impl;

import java.io.IOException;
import java.util.Map;

import org.json.JSONException;
import org.springframework.stereotype.Service;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yls.projects.robot.service.SMSService;

/**
 * 
 * 短信Service实现类
 * @author 陈华湛
 * @date 2018年4月28日下午3:48:19
 */
@Service("sMSService")
public class SMSServiceImpl implements SMSService {

	@Override
	public Map<String, Object> sendVerifyCode(Map<String, Object> map) {
		
		//国家码
		String nationcode = map.get("nationcode").toString();
		
		// 短信应用SDK AppID
		int appid = Integer.parseInt(map.get("appid").toString()); // 1400开头

		// 短信应用SDK AppKey
		String appkey = map.get("appkey").toString();

		// 需要发送短信的手机号码
		String telphone = map.get("telphone").toString();
		
		// 短信模板ID，需要在短信应用中申请
		int templateId = Integer.parseInt(map.get("templateId").toString()); 

		// 签名  NOTE: 这里的签名"腾讯云"只是一个示例，真实的签名需要在短信控制台中申请，另外签名参数使用的是`签名内容`，而不是`签名ID`
		String smsSign = map.get("smsSign").toString(); 
		
		//验证码
		String[] params = {map.get("code").toString(),"5"};
		
		
		try {
		    
		    SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
		    // 签名参数未提供或者为空时，会使用默认签名发送短信
		    SmsSingleSenderResult result = ssender.sendWithParam(nationcode, telphone,templateId, params, smsSign, "", "");  
		    Gson gson = new Gson();
		    Map<String, Object> returnMap  = gson.fromJson(result.toString(), new TypeToken<Map<String, String>>() {}.getType());
		    return returnMap;
		    
		} catch (HTTPException e) {
		    // HTTP响应码错误
		    e.printStackTrace();
		} catch (JSONException e) {
		    // json解析错误
		    e.printStackTrace();
		} catch (IOException e) {
		    // 网络IO错误
		    e.printStackTrace();
		}
		
		return null;
	}

}
