package com.yls.projects.robot.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @Author:Alex
 * @Description: 父实体
 * @Date: 11:00 2018/5/31
 */
public class BaseEntity {

	/** 第几页,默认第一页 */
	@JsonIgnore
	private Integer page;

	/** 一页显示多少条 */
	@JsonIgnore
	private Integer size;

	/** 总条数 */
	@JsonIgnore
	private Integer total;

	/** 从第几条开始 */
	@JsonIgnore
	private Integer startIndex;

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

}
