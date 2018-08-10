package com.yls.projects.robot.service;

import java.util.Map;

/**
 * 
 * 短信Service接口
 * @author 陈华湛
 * @date 2018年4月28日下午3:14:18
 */
public interface SMSService {
	
	/**
	 * 发送验证码
	 * @param map
	 * @return
	 */
	Map<String, Object> sendVerifyCode(Map<String, Object> map);


}
