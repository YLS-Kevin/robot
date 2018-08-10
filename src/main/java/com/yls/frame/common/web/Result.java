package com.yls.frame.common.web;

/**
 * http请求返回的最外层对象
 * 
 * @author Alex Lee 李璐
 * @date 2018年5月3日下午4:49:00
 */
public class Result<T> {

	/** 错误码. */
	private String ret;

	/** 提示信息. */
	private String info;

	/** 具体的内容. */
	private T returnData;

	public String getRet() {
		return ret;
	}

	public void setRet(String ret) {
		this.ret = ret;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public T getReturnData() {
		return returnData;
	}

	public void setReturnData(T returnData) {
		this.returnData = returnData;
	}

}
