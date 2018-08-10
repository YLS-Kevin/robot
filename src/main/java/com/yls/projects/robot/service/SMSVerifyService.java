package com.yls.projects.robot.service;

import com.yls.projects.robot.entity.SMSVerify;

/**
 * 
 * 短信验证Service接口
 * @author 陈华湛
 * @date 2018年4月28日下午5:05:27
 */
public interface SMSVerifyService {

	/**
	 * 根据手机号查找短信验证信息
	 * @param sMSVerify
	 * @return
	 */
	SMSVerify getByTelPhone(SMSVerify sMSVerify);
	
	/**
	 * 保存短信验证信息
	 * @param sMSVerify
	 * @return
	 */
	Integer save(SMSVerify sMSVerify);
	
}
