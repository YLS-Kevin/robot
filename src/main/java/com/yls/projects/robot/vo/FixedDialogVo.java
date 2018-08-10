package com.yls.projects.robot.vo;

/**
 * 固定对话接收实体类
 * 
 * @author 陈俊
 * @date 2018年5月16日
 */
public class FixedDialogVo {

	/** 账号id */
	private String idAc;

	/** 对话库类别id */
	private String idDt;

	/** 人说的话 */
	private String awords;

	/** 关键字+同义词 */
	private String keywords;

	/** 回答 */
	private String anwers;

	/** 脚本 */
	private String script;

	/** 匹配类型。1-模糊匹配，2-关键词匹配 */
	private String aptype;

	/** 人机对话id */
	private String idD;

	/** 脚本id */
	private String scriptId;

	/** 人机对话id */
	private String ids;
	
	/** 通用情景模块id-对应的对话库id */
	private String cidMIdDt;

	/** 状态。1-启用。2-禁用。 */
	private String state;

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getScriptId() {
		return scriptId;
	}

	public void setScriptId(String scriptId) {
		this.scriptId = scriptId;
	}

	public String getIdD() {
		return idD;
	}

	public void setIdD(String idD) {
		this.idD = idD;
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

	public String getAwords() {
		return awords;
	}

	public void setAwords(String awords) {
		this.awords = awords;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getAnwers() {
		return anwers;
	}

	public void setAnwers(String anwers) {
		this.anwers = anwers;
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

	public String getCidMIdDt() {
		return cidMIdDt;
	}

	public void setCidMIdDt(String cidMIdDt) {
		this.cidMIdDt = cidMIdDt;
	}
}
