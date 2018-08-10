package com.yls.projects.robot.entity;


/**
 * 
 * 账号实体
 * @author 陈华湛
 * @date 2018年5月3日上午9:55:33
 */
public class Account {
	
	/**
	 * 账户id。UUID
	 */
	private String id;
	
	/**
	 * 账户昵称
	 */
	private String accountName;
	
	/**
	 * 超级用户id
	 */
	private String superUserId;
	
	/**
	 * 会员等级id
	 */
	private String vipId;
	
	/**
	 * 会员过期时间，免费会员：永不过期
	 */
	private String vipExpireTime;
	
	/**
	 * 类型。1-个人，2-企业，3-单位
	 */
	private String atype;
	
	/**
	 * 是否认证。1-是，2-否
	 */
	private String isauth;
	
	/**
	 * 认证时间
	 */
	private String authtime;
	
	/**
	 * 状态。1-正常。2-禁用。
	 */
	private String state;
	
	/**
	 * 备注
	 */
	private String remarks;
	
	/**
	 * 创建者
	 */
	private String createBy;
	
	/**
	 * 创建时间
	 */
	private String createDate;
	
	/**
	 * 更新者
	 */
	private String updateBy;
	
	/**
	 * 更新时间
	 */
	private String updateDate;
	
	/**
	 * 删除标记。0-正常，1-已删除
	 */
	private String delFlag;
	
	
	/**
	 * 类型。1-个人，2-企业，3-单位
	 */
	public static String ATYPE_1 = "1";
	
	/**
	 * 类型。1-个人，2-企业，3-单位
	 */
	public static String ATYPE_2 = "2";
	
	/**
	 * 类型。1-个人，2-企业，3-单位
	 */
	public static String ATYPE_3 = "3";
	
	/**
	 * 是否认证。1-是，2-否
	 */
	public static String IS_AUTH_1 = "1";
	
	/**
	 * 是否认证。1-是，2-否
	 */
	public static String IS_AUTH_2 = "2";
	
	/**
	 * 状态。1-启用。2-禁用
	 */
	public static final String STATE_1 = "1";
	
	/**
	 * 状态。1-启用。2-禁用
	 */
	public static final String STATE_2 = "2";
	
	
	/**
	 * 删除标记。0-正常，1-已删除
	 */
	public static final String DEL_FLAG_0 = "0";
	
	/**
	 * 删除标记。0-正常，1-已删除
	 */
	public static final String DEL_FLAG_1 = "1";
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getSuperUserId() {
		return superUserId;
	}

	public void setSuperUserId(String superUserId) {
		this.superUserId = superUserId;
	}

	public String getVipId() {
		return vipId;
	}

	public void setVipId(String vipId) {
		this.vipId = vipId;
	}

	public String getVipExpireTime() {
		return vipExpireTime;
	}

	public void setVipExpireTime(String vipExpireTime) {
		this.vipExpireTime = vipExpireTime;
	}

	public String getAtype() {
		return atype;
	}

	public void setAtype(String atype) {
		this.atype = atype;
	}

	public String getIsauth() {
		return isauth;
	}

	public void setIsauth(String isauth) {
		this.isauth = isauth;
	}

	public String getAuthtime() {
		return authtime;
	}

	public void setAuthtime(String authtime) {
		this.authtime = authtime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	

	
	
	
	
}
