package com.yls.projects.robot.entity;

/**
 * 
 * 会员实体
 * @author 陈华湛
 * @date 2018年5月3日下午2:27:16
 */
public class DialogVip {

	/**
	 * vip主键
	 */
	private String vipId;

	/**
	 * vip名称
	 */
	private String vipLevelName;
	
	/**
	 * vip有效天数
	 */
	private Integer vipValidDay;
	
	/**
	 * vip价格
	 */
	private Double vipPrice;
	
	/**
	 * 备注
	 */
	private String remark;
	
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
	 * 删除标记。0-正常，1-已删除
	 */
	public static final String DEL_FLAG_0 = "0";
	
	/**
	 * 删除标记。0-正常，1-已删除
	 */
	public static final String DEL_FLAG_1 = "1";
	
	

	public String getVipId() {
		return vipId;
	}

	public void setVipId(String vipId) {
		this.vipId = vipId;
	}

	public String getVipLevelName() {
		return vipLevelName;
	}

	public void setVipLevelName(String vipLevelName) {
		this.vipLevelName = vipLevelName;
	}

	public Integer getVipValidDay() {
		return vipValidDay;
	}

	public void setVipValidDay(Integer vipValidDay) {
		this.vipValidDay = vipValidDay;
	}

	public Double getVipPrice() {
		return vipPrice;
	}

	public void setVipPrice(Double vipPrice) {
		this.vipPrice = vipPrice;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
