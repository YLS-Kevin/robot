package com.yls.projects.robot.vo;

/**
 * 用户表 返回实体
 * 
 * @author 陈俊
 * @date 2018年6月4日
 */
public class DialogUserVo extends BaseEntity {

	/** 账户名称 */
	private String acountName;

	/** 该用户所对应的超级用户id */
	private String superUserId;

	/** 会员过期时间 */
	private String vipExpireTime;

	/** 会员等级 */
	private String vipLevelName;

	/** 用户昵称 */
	private String nickName;

	/** 用户id */
	private String userId;

	/** 用户电话 */
	private String telphone;

	/** 是否是安全电话 */
	private String isSavetyPhone;

	/** 用户登录的密码 */
	private String pwd;

	/** 邮箱 */
	private String email;

	/** 是否是安全邮箱 */
	private String isSavetyEmail;

	/** 注册方式 */
	private String registerType;

	/** qq登录的openID */
	private String qq4UserId;

	/** 微信登录的openID */
	private String wx4UserId;

	/** 新浪微博登录的openID */
	private String xlwb4UserId;

	/** 账号ID */
	private String idAc;

	public String getIdAc() {
		return idAc;
	}

	public void setIdAc(String idAc) {
		this.idAc = idAc;
	}

	public String getAcountName() {
		return acountName;
	}

	public void setAcountName(String acountName) {
		this.acountName = acountName;
	}

	public String getSuperUserId() {
		return superUserId;
	}

	public void setSuperUserId(String superUserId) {
		this.superUserId = superUserId;
	}

	public String getVipExpireTime() {
		return vipExpireTime;
	}

	public void setVipExpireTime(String vipExpireTime) {
		this.vipExpireTime = vipExpireTime;
	}

	public String getVipLevelName() {
		return vipLevelName;
	}

	public void setVipLevelName(String vipLevelName) {
		this.vipLevelName = vipLevelName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getIsSavetyPhone() {
		return isSavetyPhone;
	}

	public void setIsSavetyPhone(String isSavetyPhone) {
		this.isSavetyPhone = isSavetyPhone;
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

	public String getIsSavetyEmail() {
		return isSavetyEmail;
	}

	public void setIsSavetyEmail(String isSavetyEmail) {
		this.isSavetyEmail = isSavetyEmail;
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

}
