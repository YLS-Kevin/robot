package com.yls.projects.robot.entity;

import java.util.List;

/**
 * 
 * 人机对话实体类
 * 
 * @author 陈华湛
 * @date 2018年5月5日下午4:29:46
 */
public class Dialog {

	/**
	 * id。UUID
	 */
	private String id;

	/**
	 * 账户id。UUID
	 */
	private String idAc;

	/**
	 * 类别id。UUID
	 */
	private String idDt;
	
	/** 通用情景模块id-对应的对话库id */
	private String cidMIdDt;

	/**
	 * 对话类别。1-未知（暂不用），2-固定应答，3-接口应答，
	 */
	private String atype;

	/** 4-多轮对话主题入口，5-多轮对话中。 */
	private String mulDialogType;

	/**
	 * 主题id。多轮对话时才有。单轮对话时，该字段为空。
	 */
	private String idAp;

	/**
	 * 组别id
	 */
	private String teamId;

	/**
	 * 状态。1-启用。2-禁用。
	 */
	private String state;

	/**
	 * 排序
	 */
	private Integer sort;

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
	 * 对话类别。1-未知（暂不用），2-固定应答，3-接口应答，4-多轮对话主题入口，5-多轮对话中。
	 */
	public static final String ATYPE_1 = "1";
	/**
	 * 对话类别。1-未知（暂不用），2-固定应答，3-接口应答，4-多轮对话主题入口，5-多轮对话中。
	 */
	public static final String ATYPE_2 = "2";
	/**
	 * 对话类别。1-未知（暂不用），2-固定应答，3-接口应答，4-多轮对话主题入口，5-多轮对话中。
	 */
	public static final String ATYPE_3 = "3";
	/**
	 * 对话类别。1-未知（暂不用），2-固定应答，3-接口应答，4-多轮对话主题入口，5-多轮对话中。
	 */
	public static final String ATYPE_4 = "4";
	/**
	 * 对话类别。1-未知（暂不用），2-固定应答，3-接口应答，4-多轮对话主题入口，5-多轮对话中。
	 */
	public static final String ATYPE_5 = "5";

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

	private List<DialogMan> dialogMans;

	public String getMulDialogType() {
		return mulDialogType;
	}

	public void setMulDialogType(String mulDialogType) {
		this.mulDialogType = mulDialogType;
	}

	public List<DialogMan> getDialogMans() {
		return dialogMans;
	}

	public void setDialogMans(List<DialogMan> dialogMans) {
		this.dialogMans = dialogMans;
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

	public String getIdDt() {
		return idDt;
	}

	public void setIdDt(String idDt) {
		this.idDt = idDt;
	}

	public String getAtype() {
		return atype;
	}

	public void setAtype(String atype) {
		this.atype = atype;
	}

	public String getIdAp() {
		return idAp;
	}

	public void setIdAp(String idAp) {
		this.idAp = idAp;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
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

	public String getCidMIdDt() {
		return cidMIdDt;
	}

	public void setCidMIdDt(String cidMIdDt) {
		this.cidMIdDt = cidMIdDt;
	}

}
