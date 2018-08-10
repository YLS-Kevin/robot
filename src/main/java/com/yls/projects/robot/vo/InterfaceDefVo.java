package com.yls.projects.robot.vo;

/**
 * 接口对话后台接收实体类
 * 
 * @author 陈俊
 * @date 2018年5月14日
 */
public class InterfaceDefVo {

	/** id */
	private String id;

	/** 账号id */
	private String idAc;

	/** 人机对话id */
	private String idD;

	/** 对话库类别id */
	private String idDt;

	/** 数据接口id */
	private String idDi;

	/** 人说的话 */
	private String awords;

	/**
	 * 关键词(wkey)+url参数(wpara)+动态词类型(wdyna) + 关键词类型(wtype)+同义词(near)
	 * 关键词类型。1-固定，2-变化 如果为变化关键词则无同义词\ 关键词
	 */
	private String keywords;

	/** 如果是固定答案,多个回答逗号相隔;如果是调用接口则返回数据接口id */
	private String anwers;

	/** 是否返回脚本 */
	private String stype;

	/** 返回脚本数组 */
	private String script;

	/** 匹配类型。1-模糊匹配，2-关键词匹配 */
	private String aptype;

	public String getIdD() {
		return idD;
	}

	public void setIdD(String idD) {
		this.idD = idD;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdDi() {
		return idDi;
	}

	public void setIdDi(String idDi) {
		this.idDi = idDi;
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

}
