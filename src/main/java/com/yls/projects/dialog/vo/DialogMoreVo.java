package com.yls.projects.dialog.vo;

import com.yls.projects.dialog.entity.DialogMore;

public class DialogMoreVo extends DialogMore {
	private Integer page;

	private Integer size;

	private Integer total;

	private Integer startIndex;

	private String idAps;

	public String getIdAps() {
		return idAps;
	}

	public void setIdAps(String idAps) {
		this.idAps = idAps;
	}

	public Integer getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
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
}
