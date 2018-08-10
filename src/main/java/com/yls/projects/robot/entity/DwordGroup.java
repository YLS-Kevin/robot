package com.yls.projects.robot.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 动态词组表 实体类
 * 
 * @author 陈俊
 * @date 2018年5月21日
 */
public class DwordGroup {

	/** 动态词组id。UUID */
	private String id;

	/** 账户id。UUID */
	private String idAc;

	/** 动态词组名。英文 */
	private String groupName;

	/** 动态词组中文名 */
	private String groupCnName;

	/** 是否共享.0-默认不共享, 1-共享 */
	private String isShare;
	
	/** 是否是默认添加  1:是, 2否 */
	private String isDefault;

	/** 状态。1-启用。2-禁用。 */
	private String state;

	/** 排序 */
	private Integer sort;

	/** 备注 */
	private String remarks;

	/** 创建者 */
	private String createBy;

	/** 创建时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createDate;

	/** 更新者 */
	private String updateBy;

	/** 更新时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date updateDate;

	/**
	 * 状态。1-启用。2-禁用。
	 */
	public static final String STATE_1 = "1";

	/**
	 * 状态。1-启用。2-禁用。
	 */
	public static final String STATE_2 = "2";
	
	/**
	 * 默认。1-是。2-否。
	 */
	public static final String DEFAULT_YES = "1";

	/**
	 * 状态。1-启用。2-禁用。
	 */
	public static final String DEFAULT_NO = "2";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getIdAc() {
		return idAc;
	}

	public void setIdAc(String idAc) {
		this.idAc = idAc == null ? null : idAc.trim();
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName == null ? null : groupName.trim();
	}

	public String getGroupCnName() {
		return groupCnName;
	}

	public void setGroupCnName(String groupCnName) {
		this.groupCnName = groupCnName == null ? null : groupCnName.trim();
	}

	public String getIsShare() {
		return isShare;
	}

	public void setIsShare(String isShare) {
		this.isShare = isShare == null ? null : isShare.trim();
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks == null ? null : remarks.trim();
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy == null ? null : createBy.trim();
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy == null ? null : updateBy.trim();
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
}