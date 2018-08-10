package com.yls.projects.robot.vo;

import com.yls.projects.robot.entity.Robots;

/**
 * 机器人分页实体类
 * 
 * @author 陈俊
 * @date 2018年5月21日
 */
public class RobotsVo extends Robots {

	/** 第几页,默认第一页 */
	private Integer page;

	/** 一页显示多少条 */
	private Integer size;

	/** 总条数 */
	private Integer total;

	/** 从第几条开始 */
	private Integer startIndex;
	
	/** 机器人id*/
	private String cid;
	
	/** 技能id(人机对话库id) */
	private String idDt;
	
	/** 问题 */
	private String question;
	
	/** 机器应答内容 */
	private String answer;
	
	/** 类型。1-无答案时，2-接口异常时，3-系统出错时 */
	private String stype;
	
	/** 机器人模块id。UUID */
	private String idM;
	
	/** 模块名*/
	private String mname;
	
	/** 触发关键词*/
	private String dokey;
	
	/** 是否是通用模块。1-是。2-否。*/
	private String iscommon;
	
	/** 功能描述 */
	private String remarks;
	
	/**
	 * 获取机器人模块信息的参数
	 */
	
	/** 起始页 */
	private Integer startIndexSingle;
	
	/** 当前页 */
	private Integer pageSingle;

	/** 一页显示都是条 */
	private Integer sizeSingle;

	/** 关键词 */
	private String keyword;

	/** 是否是接口 */
	private String isI;

	/** 单轮排序：升序或降序 */
	private String descSingle;
	
	/** 单轮排序字段 */
	private String descSingleCol;
	
	/** 对话状态 */
	private String stateDialog;
	
	/** 能力名称  */
	private String atname;
	
	private String isModifyDoKey;
	
	/** 通用情景模块id-对应的对话库id */
	private String cidMIdDt;
	
	/** 问答id */
	private String dialogId;
	
	public String getDialogId() {
		return dialogId;
	}

	public void setDialogId(String dialogId) {
		this.dialogId = dialogId;
	}

	public String getCidMIdDt() {
		return cidMIdDt;
	}

	public void setCidMIdDt(String cidMIdDt) {
		this.cidMIdDt = cidMIdDt;
	}

	public String getIsModifyDoKey() {
		return isModifyDoKey;
	}

	public void setIsModifyDoKey(String isModifyDoKey) {
		this.isModifyDoKey = isModifyDoKey;
	}

	public String getAtname() {
		return atname;
	}

	public void setAtname(String atname) {
		this.atname = atname;
	}

	public Integer getStartIndexSingle() {
		return startIndexSingle;
	}

	public void setStartIndexSingle(Integer startIndexSingle) {
		this.startIndexSingle = startIndexSingle;
	}

	public Integer getPageSingle() {
		return pageSingle;
	}

	public void setPageSingle(Integer pageSingle) {
		this.pageSingle = pageSingle;
	}

	public Integer getSizeSingle() {
		return sizeSingle;
	}

	public void setSizeSingle(Integer sizeSingle) {
		this.sizeSingle = sizeSingle;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getIsI() {
		return isI;
	}

	public void setIsI(String isI) {
		this.isI = isI;
	}

	public String getDescSingle() {
		return descSingle;
	}

	public void setDescSingle(String descSingle) {
		this.descSingle = descSingle;
	}

	public String getDescSingleCol() {
		return descSingleCol;
	}

	public void setDescSingleCol(String descSingleCol) {
		this.descSingleCol = descSingleCol;
	}

	public String getStateDialog() {
		return stateDialog;
	}

	public void setStateDialog(String stateDialog) {
		this.stateDialog = stateDialog;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getStype() {
		return stype;
	}

	public void setStype(String stype) {
		this.stype = stype;
	}

	public String getIdM() {
		return idM;
	}

	public void setIdM(String idM) {
		this.idM = idM;
	}

	public String getMname() {
		return mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	public String getDokey() {
		return dokey;
	}

	public void setDokey(String dokey) {
		this.dokey = dokey;
	}

	public String getIscommon() {
		return iscommon;
	}

	public void setIscommon(String iscommon) {
		this.iscommon = iscommon;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}
	
	
	
	

	public String getIdDt() {
		return idDt;
	}

	public void setIdDt(String idDt) {
		this.idDt = idDt;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	@Override
	public String toString() {
		return "RobotsVo [page=" + page + ", size=" + size + ", total=" + total + ", startIndex=" + startIndex
				+ ", cid=" + cid + ", answer=" + answer + ", stype=" + stype + ", idM=" + idM + ", mname=" + mname
				+ ", dokey=" + dokey + ", iscommon=" + iscommon + ", remarks=" + remarks + "]";
	}
	

	
	
	
}
