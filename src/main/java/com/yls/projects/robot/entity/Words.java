package com.yls.projects.robot.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 词库表实体类
 * 
 * @author 陈俊
 * @date 2018年5月31日
 */
public class Words {

	/** id。UUID */
	private String id;

	/** 动态词组id。UUID */
	private String idDwg;

	/** 账户id。UUID */
	private String idAc;

	/** 词名 */
	private String wname;

	/** 词性 */
	private String wx;

	/** 词性2 */
	private String wx2;

	/** 词频 */
	private Integer wften;

	/** 词频2 */
	private Integer wften2;

	/** 自动插入。1-是，2-否（默认）。 */
	private String autoin;

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

	/** 状态。1-启用。2-禁用。 */
	public static final String STATE_1 = "1";

	/** 状态。1-启用。2-禁用。 */
	public static final String STATE_2 = "2";

	/** 自动插入。1-是，2-否（默认）。 */
	public static final String AUTOIN_1 = "1";

	/** 自动插入。1-是，2-否（默认）。 */
	public static final String AUTOIN_2 = "2";

	/** 词性 */
	public static final String WX_n = "n";

	public String getIdAc() {
		return idAc;
	}

	public void setIdAc(String idAc) {
		this.idAc = idAc;
	}

	public String getWname() {
		return wname;
	}

	public void setWname(String wname) {
		this.wname = wname;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getIdDwg() {
		return idDwg;
	}

	public void setIdDwg(String idDwg) {
		this.idDwg = idDwg == null ? null : idDwg.trim();
	}

	public String getWx() {
		return wx;
	}

	public void setWx(String wx) {
		this.wx = wx == null ? null : wx.trim();
	}

	public String getWx2() {
		return wx2;
	}

	public void setWx2(String wx2) {
		this.wx2 = wx2 == null ? null : wx2.trim();
	}

	public Integer getWften() {
		return wften;
	}

	public void setWften(Integer wften) {
		this.wften = wften;
	}

	public Integer getWften2() {
		return wften2;
	}

	public void setWften2(Integer wften2) {
		this.wften2 = wften2;
	}

	public String getAutoin() {
		return autoin;
	}

	public void setAutoin(String autoin) {
		this.autoin = autoin;
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
}