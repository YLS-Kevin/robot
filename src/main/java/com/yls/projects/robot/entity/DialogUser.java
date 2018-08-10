package com.yls.projects.robot.entity;

import java.util.Date;

/**
 * 
 * 用户实体
 * 
 * @author 陈华湛
 * @date 2018年4月27日下午4:50:19
 * @since 1.0
 */
public class DialogUser {

	// 主键用户id
	private String userId;

	// 账户id
	private String idAc;

	// 用户昵称
	private String nickName;

	// 手机
	private String telphone;

	// 是否是安全手机. 0:否, 1:是
	private String isSafetyPhone;

	// 登录密码MD5加密
	private String pwd;

	// 邮箱
	private String email;

	// 是否是安全邮箱. 0:否, 1:是
	private String isSafetyEmail;

	// 1:qq,2:微信,3新浪微博,4:手机短信
	private String registerType;

	// qq唯一标识用户id
	private String qq4UserId;

	// wx唯一标识用户id
	private String wx4UserId;

	// 新浪微博唯一标识用户id
	private String xlwb4UserId;

	// 创建者
	private String createBy;

	// 创建时间
	private Date createDate;

	// 更新者
	private String updateBy;

	// 更新时间
	private Date updateDate;

	// 是否删除，0:否,1:是
	private String delFlag;

	// 备注
	private String remark;

	// 验证码（非数据表字段）
	private String code;

	// 账户名
	private String accountName;

	/**
	 * 是否是安全手机. 0:否, 1:是
	 */
	public static String IS_SAFETY_PHONE_0 = "0";

	/**
	 * 是否是安全手机. 0:否, 1:是
	 */
	public static String IS_SAFETY_PHONE_1 = "1";

	/**
	 * 是否是安全邮箱. 0:否, 1:是
	 */
	public static String IS_SAFETY_EMAIL_0 = "0";

	/**
	 * 是否是安全邮箱. 0:否, 1:是
	 */
	public static String IS_SAFETY_EMAIL_1 = "1";

	/**
	 * 1:qq,2:微信,3新浪微博,4:手机短信
	 */
	public static String REGISTER_TYPE_1 = "1";

	/**
	 * 1:qq,2:微信,3新浪微博,4:手机短信
	 */
	public static String REGISTER_TYPE_2 = "2";

	/**
	 * 1:qq,2:微信,3新浪微博,4:手机短信
	 */
	public static String REGISTER_TYPE_3 = "3";

	/**
	 * 1:qq,2:微信,3新浪微博,4:手机短信
	 */
	public static String REGISTER_TYPE_4 = "4";

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIdAc() {
		return idAc;
	}

	public void setIdAc(String idAc) {
		this.idAc = idAc;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getIsSafetyPhone() {
		return isSafetyPhone;
	}

	public void setIsSafetyPhone(String isSafetyPhone) {
		this.isSafetyPhone = isSafetyPhone;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIsSafetyEmail() {
		return isSafetyEmail;
	}

	public void setIsSafetyEmail(String isSafetyEmail) {
		this.isSafetyEmail = isSafetyEmail;
	}

	public String getRegisterType() {
		return registerType;
	}

	public void setRegisterType(String registerType) {
		this.registerType = registerType;
	}

	public String getQq4UserId() {
		return qq4UserId;
	}

	public void setQq4UserId(String qq4UserId) {
		this.qq4UserId = qq4UserId;
	}

	public String getWx4UserId() {
		return wx4UserId;
	}

	public void setWx4UserId(String wx4UserId) {
		this.wx4UserId = wx4UserId;
	}

	public String getXlwb4UserId() {
		return xlwb4UserId;
	}

	public void setXlwb4UserId(String xlwb4UserId) {
		this.xlwb4UserId = xlwb4UserId;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
