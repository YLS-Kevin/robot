package com.yls.projects.dialog.entity;

import java.util.Date;

public class DialogMore {
	/** 主题id。UUID */
	private String id;

	/** 账户id。UUID */
	private String idAc;
	
	/** 对话库id */
	private String idDt;

	/** 主题名。如：问天气 */
	private String tname;

	/** 状态。1-启用。2-禁用。 */
	private String state;

	/** 排序 */
	private Integer sort;

	/** 备注 */
	private String remarks;

	/** 创建者 */
	private String createBy;

	/** 创建时间 */
	private Date createDate;

	/** 更新者 */
	private String updateBy;

	/** 更新时间 */
	private Date updateDate;

	/** 删除标记。0-正常，1-已删除 */
	private String delFlag;

	public final static String ID = "id";
	/**
	 * 状态。1-启用
	 */
	public final static String STATE_1 = "1";
	/**
	 * 状态。2-禁用
	 */
	public final static String STATE_2 = "2";

	/**
	 * 删除标记。0-正常，
	 */
	public final static String DEL_FLAG_0 = "0";
	/**
	 * 删除标记。1-已删除
	 */
	public final static String DEL_FLAG_1 = "1";

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
		this.idAc = idAc;
	}

	public String getIdDt() {
		return idDt;
	}

	public void setIdDt(String idDt) {
		this.idDt = idDt;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname == null ? null : tname.trim();
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

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag == null ? null : delFlag.trim();
	}
}