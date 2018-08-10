package com.yls.projects.robot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 机器接口应答实体类
 * 
 * @author 陈俊
 * @date 2018年5月14日
 */
public class DialogRobotInter {

	/** id。UUID */
	private String id;

	/** 账户id。UUID */
	@JsonIgnore
	private String idAc;

	/** 人机对话id */
	@JsonIgnore
	private String idD;

	/** 数据接口id */
	private String idDi;

	/** 创建者 */
	@JsonIgnore
	private String createBy;

	/** 创建时间 */
	@JsonIgnore
	private String createDate;

	/** 更新者 */
	@JsonIgnore
	private String updateBy;

	/** 更新时间 */
	@JsonIgnore
	private String updateDate;
	
	/** 接口名*/
	private String explains;
	
	/** url */
	private String url;
	
	/** 参数名 */
	private String paramName;
	
	public String getExplains() {
		return explains;
	}

	public void setExplains(String explains) {
		this.explains = explains;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdAc() {
		return idAc;
	}

	public void setIdAc(String idAc) {
		this.idAc = idAc;
	}

	public String getIdD() {
		return idD;
	}

	public void setIdD(String idD) {
		this.idD = idD;
	}

	public String getIdDi() {
		return idDi;
	}

	public void setIdDi(String idDi) {
		this.idDi = idDi;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public String toString() {
		return "DialogRobotInter [id=" + id + ", idAc=" + idAc + ", idD=" + idD + ", idDi=" + idDi + ", createBy="
				+ createBy + ", createDate=" + createDate + ", updateBy=" + updateBy + ", updateDate=" + updateDate
				+ "]";
	}

}
