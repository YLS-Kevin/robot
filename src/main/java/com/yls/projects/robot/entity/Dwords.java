package com.yls.projects.robot.entity;

/**
 * 动态词表 实体类
 * 
 * @author 陈俊
 * @date 2018年5月21日
 */
public class Dwords {

	/** id。UUID */
	private String id;

	/** 账户id。UUID */
	private String idAc;

	/** 动态词组id。UUID */
	private String idDwg;

	/** 动态词名 */
	private String dwname;

	/** 状态。1-启用。2-禁用。 */
	private String state;

	/** 排序 */
	private Integer sort;

	/**
	 * 状态。1-启用。2-禁用。
	 */
	public static final String STATE_1 = "1";

	/**
	 * 状态。1-启用。2-禁用。
	 */
	public static final String STATE_2 = "2";

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

	public String getIdDwg() {
		return idDwg;
	}

	public void setIdDwg(String idDwg) {
		this.idDwg = idDwg == null ? null : idDwg.trim();
	}

	public String getDwname() {
		return dwname;
	}

	public void setDwname(String dwname) {
		this.dwname = dwname == null ? null : dwname.trim();
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
}