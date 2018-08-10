package com.yls.projects.robot.entity;


/**
 * 
 * 短信验证实体类
 * @author 陈华湛
 * @date 2018年4月28日下午5:00:31
 */
public class SMSVerify {

	/**
	 * 手机号
	 */
	private String telphone;
	
	/**
	 * 验证码
	 */
	private String code;
	
	/**
	 * 验证码生成时间
	 */
	private String createDate;
	
	
	public String getTelphone() {
		return telphone;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	
	
}
