package com.yls.projects.robot.vo;

import com.yls.projects.robot.entity.InterData;

/**
 * @Author:Alex
 * @Description: 我的数据接口
 * @Date: 11:00 2018/5/31
 */
public class InterDataVo extends InterData {

	/* 多个主键id逗号相隔 */
	private String ids;

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

}
