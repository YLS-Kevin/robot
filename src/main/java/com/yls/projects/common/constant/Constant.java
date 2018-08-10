package com.yls.projects.common.constant;

/**
 * 
 * 常量值类
 * @author 陈华湛
 * @date 2018年5月2日下午4:56:10
 */
public class Constant {

	/**
	 * 验证码过期时间
	 */
	public static final long VERIFY_CODE_OVERTIME = 5*60*1000L;
	
	/**
	 * 短信发送返回参数错误码
	 */
	public static final String SMS_JSON_RESULT_KEY  = "result";
	
	/**
	 * 短信发送返回参数错误码值，0-短信发送成功
	 */
	public static final String SMS_JSON_RESULT_VALUE  = "0";
	
	/**
	 * 默认的普通免费会员id
	 */
	public static final String FREE_VIP_ID = "f47e52424e9d11e8ac9f6c92bf48ca44";
	
	/**
	 * 默认的普通免费会员过期时间
	 */
	public static final String FREE_VIP_EXPIRETIME = "2037-12-31 23:59:00";
	
	
	
	
}
