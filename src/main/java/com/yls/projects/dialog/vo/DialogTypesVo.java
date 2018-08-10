package com.yls.projects.dialog.vo;

import com.yls.projects.dialog.entity.DialogTypes;

public class DialogTypesVo extends DialogTypes {

	private Integer page;

	private Integer size;

	private Integer total;

	private Integer startIndex;

	private Integer totalSingle;

	private Integer startIndexSingle;

	private Integer pageSingle;

	private Integer sizeSingle;

	private Integer totalMul;

	private Integer startIndexMul;

	private Integer totalMul2;

	private Integer startIndexMul2;

	private Integer pageMul;

	private Integer sizeMul;

	private Integer pageMul2;

	private Integer sizeMul2;

	private String idDt;

	private String idAc;
	
	private String idAp;
	
	public String getIdAp() {
		return idAp;
	}

	public void setIdAp(String idAp) {
		this.idAp = idAp;
	}

	/** 关键词 */
	private String keyword;

	/** 是否是接口 */
	private String isI;

	/** 单轮排序：升序或降序 */
	private String descSingle;

	/** 多轮排序：升序或降序 */
	private String descMul;

	/** 单轮排序字段 */
	private String descSingleCol;

	/** 多轮排序字段 */
	private String descMulCol;

	private String descMul2;

	private String descMulCol2;

	/** 对话类别 */
	private String atype;

	/** 对话状态 */
	private String stateDialog;

	/** 多轮对话主题类型 */
	private String mulDialogType;

	public String getMulDialogType() {
		return mulDialogType;
	}

	public void setMulDialogType(String mulDialogType) {
		this.mulDialogType = mulDialogType;
	}

	public String getStateDialog() {
		return stateDialog;
	}

	public void setStateDialog(String stateDialog) {
		this.stateDialog = stateDialog;
	}

	/**
	 * 对话类别 4-多轮对话主题入口
	 */
	public final static String atype_4 = "4";

	/**
	 * 对话类别 5-多轮对话中
	 */
	public final static String atype_5 = "5";

	public String getAtype() {
		return atype;
	}

	public void setAtype(String atype) {
		this.atype = atype;
	}

	public Integer getTotalMul2() {
		return totalMul2;
	}

	public void setTotalMul2(Integer totalMul2) {
		this.totalMul2 = totalMul2;
	}

	public Integer getStartIndexMul2() {
		return startIndexMul2;
	}

	public void setStartIndexMul2(Integer startIndexMul2) {
		this.startIndexMul2 = startIndexMul2;
	}

	public Integer getPageMul2() {
		return pageMul2;
	}

	public void setPageMul2(Integer pageMul2) {
		this.pageMul2 = pageMul2;
	}

	public Integer getSizeMul2() {
		return sizeMul2;
	}

	public void setSizeMul2(Integer sizeMul2) {
		this.sizeMul2 = sizeMul2;
	}

	public String getIdAc() {
		return idAc;
	}

	public void setIdAc(String idAc) {
		this.idAc = idAc;
	}

	public String getDescMul2() {
		return descMul2;
	}

	public void setDescMul2(String descMul2) {
		this.descMul2 = descMul2;
	}

	public String getDescMulCol2() {
		return descMulCol2;
	}

	public void setDescMulCol2(String descMulCol2) {
		this.descMulCol2 = descMulCol2;
	}

	/**
	 * 是否是接口 2-固定应答
	 */
	public final static String isI_2 = "2";
	/**
	 * 是否是接口 3-接口应答
	 */
	public final static String isI_3 = "3";

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

	public String getDescMul() {
		return descMul;
	}

	public void setDescMul(String descMul) {
		this.descMul = descMul;
	}

	public String getDescSingleCol() {
		return descSingleCol;
	}

	public void setDescSingleCol(String descSingleCol) {
		this.descSingleCol = descSingleCol;
	}

	public String getDescMulCol() {
		return descMulCol;
	}

	public void setDescMulCol(String descMulCol) {
		this.descMulCol = descMulCol;
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

	public String getIdDt() {
		return idDt;
	}

	public void setIdDt(String idDt) {
		this.idDt = idDt;
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

	public Integer getPageMul() {
		return pageMul;
	}

	public void setPageMul(Integer pageMul) {
		this.pageMul = pageMul;
	}

	public Integer getSizeMul() {
		return sizeMul;
	}

	public void setSizeMul(Integer sizeMul) {
		this.sizeMul = sizeMul;
	}

	public Integer getTotalSingle() {
		return totalSingle;
	}

	public void setTotalSingle(Integer totalSingle) {
		this.totalSingle = totalSingle;
	}

	public Integer getStartIndexSingle() {
		return startIndexSingle;
	}

	public void setStartIndexSingle(Integer startIndexSingle) {
		this.startIndexSingle = startIndexSingle;
	}

	public Integer getTotalMul() {
		return totalMul;
	}

	public void setTotalMul(Integer totalMul) {
		this.totalMul = totalMul;
	}

	public Integer getStartIndexMul() {
		return startIndexMul;
	}

	public void setStartIndexMul(Integer startIndexMul) {
		this.startIndexMul = startIndexMul;
	}

}
