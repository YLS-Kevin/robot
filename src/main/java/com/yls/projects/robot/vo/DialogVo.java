package com.yls.projects.robot.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 对话库列表接收类
 * 
 * @author 陈俊
 * @date 2018年5月29日
 */
public class DialogVo {

	/** 主键id */
	private String id;

	/**
	 * 匹配类型。1-模糊匹配，2-关键词匹配
	 */
	private String aptype;

	/**
	 * 人说的话
	 */
	private String aword;

	/**
	 * 关键词拼接(包括5个关键词)
	 */
	private String keyWord;

	/** 机器应答内容 */
	private String answer;

	/** 是否是接口应答 */
	private String isI;

	/** 主题名称 */
	private String tname;

	/** 多轮对话个数 */
	private String nums;

	/** 状态 */
	private String state;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 更新时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date updateDate;

	public String getNums() {
		return nums;
	}

	public void setNums(String nums) {
		this.nums = nums;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public String getAptype() {
		return aptype;
	}

	public void setAptype(String aptype) {
		this.aptype = aptype;
	}

	public String getAword() {
		return aword;
	}

	public void setAword(String aword) {
		this.aword = aword;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getIsI() {
		return isI;
	}

	public void setIsI(String isI) {
		this.isI = isI;
	}

}
