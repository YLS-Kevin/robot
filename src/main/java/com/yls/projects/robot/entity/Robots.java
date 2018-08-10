package com.yls.projects.robot.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 机器人(应用)表 实体类
 * 
 * @author 陈俊
 * @date 2018年5月21日
 */
public class Robots {

	/** 机器人id。UUID */
	private String id;

	/** 账户id。UUID */
	private String idAc;

	/** 机器人名 */
	private String cname;

	/** 机器人昵称。 */
	private String nname;

	/** 所属行业 */
	private String intrade;

	/** 图标url */
	private String iconurl;

	/** 接入方式。如：WEBAPI，ANDROID，IOS */
	private String accessWay;

	/** APPID */
	private String appid;

	/** 秘钥 */
	private String secret;

	/** 状态。1-启用。2-禁用。 */
	private String state;

	/** 排序 */
	private Integer sort;

	/** 功能描述 */
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
	
	/** 机器人模块id。UUID */
	private String cidM;

	/**
	 * 状态。1-启用。2-禁用。
	 */
	public static final String STATE_1 = "1";

	/**
	 * 状态。1-启用。2-禁用。
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
		this.id = id == null ? null : id.trim();
	}

	public String getIdAc() {
		return idAc;
	}

	public void setIdAc(String idAc) {
		this.idAc = idAc == null ? null : idAc.trim();
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname == null ? null : cname.trim();
	}

	public String getNname() {
		return nname;
	}

	public void setNname(String nname) {
		this.nname = nname == null ? null : nname.trim();
	}

	public String getIntrade() {
		return intrade;
	}

	public void setIntrade(String intrade) {
		this.intrade = intrade == null ? null : intrade.trim();
	}

	public String getIconurl() {
		return iconurl;
	}

	public void setIconurl(String iconurl) {
		this.iconurl = iconurl == null ? null : iconurl.trim();
	}

	public String getAccessWay() {
		return accessWay;
	}

	public void setAccessWay(String accessWay) {
		this.accessWay = accessWay == null ? null : accessWay.trim();
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid == null ? null : appid.trim();
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret == null ? null : secret.trim();
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state == null ? null : state.trim();
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

	public String getCidM() {
		return cidM;
	}

	public void setCidM(String cidM) {
		this.cidM = cidM;
	}
}