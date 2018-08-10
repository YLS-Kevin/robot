package com.yls.projects.robot.vo;

/**
 * 多轮对话接收实体类
 * 
 * @author 陈俊
 * @date 2018年5月17日
 */
public class MulDialogVo {

	/** 参数 */
	private String keywords;

	/** 问题 */
	private String awords;

	/** 是否返回脚本 */
	private String stype;

	/** 返回脚本数组 */
	private String script;

	/** 匹配类型。1-模糊匹配，2-关键词匹配 */
	private String aptype;

	/** 对话类别。4-多轮对话主题入口，5-多轮对话中 */
	private String atype;

	/** 主题id */
	private String idAp;

	/** 账号id */
	private String idAc;

	/** 对话库类别id */
	private String idDt;

	/** 数据接口id */
	private String idDi;

	/** 人机对话id */
	private String idD;

	/** 回答 */
	private String anwers;

	/** 接口应答id */
	private String id;

	/** 对话类别。1-固定回答 2-接口回答 */
	private String type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdD() {
		return idD;
	}

	public void setIdD(String idD) {
		this.idD = idD;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getAwords() {
		return awords;
	}

	public void setAwords(String awords) {
		this.awords = awords;
	}

	public String getStype() {
		return stype;
	}

	public void setStype(String stype) {
		this.stype = stype;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public String getAptype() {
		return aptype;
	}

	public void setAptype(String aptype) {
		this.aptype = aptype;
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

	public String getIdDi() {
		return idDi;
	}

	public void setIdDi(String idDi) {
		this.idDi = idDi;
	}

	public String getAnwers() {
		return anwers;
	}

	public void setAnwers(String anwers) {
		this.anwers = anwers;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
