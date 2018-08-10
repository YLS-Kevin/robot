package com.yls.projects.robot.vo;

import com.yls.projects.robot.entity.Words;

/**
 * 词库表 接收类
 * 
 * @author 陈俊
 * @date 2018年5月31日
 */
public class WordsVo extends Words {

	/** 第几页,默认第一页 */
	private Integer page;

	/** 一页显示多少条 */
	private Integer size;

	/** 总条数 */
	private Integer total;

	/** 从第几条开始 */
	private Integer startIndex;

	private String ids;

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
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

}
