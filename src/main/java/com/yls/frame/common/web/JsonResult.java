package com.yls.frame.common.web;

public class JsonResult {

	private String ret;
	private Object returnData;
	private String info;

	public final static String SUCCESS = "0";
	private final static String ERROR = "1";
	private final static String FAILED = "2";
	private final static String INVAILD = "3";
	private final static String UnAuthorized = "4";

	public JsonResult() {
		setRet(ERROR);
		setInfo("默认错误消息！");
		setReturnData(new String[] {});
	}

	public void setSuccess(Object result) {
		setReturnData(result);
		setRet(SUCCESS);
		setInfo("成功！");
	}

	public void setError(String errorMsg) {
		setRet(ERROR);
		setInfo(errorMsg);
	}

	public String getRet() {
		return ret;
	}

	public void setRet(String ret) {
		this.ret = ret;
	}

	public Object getReturnData() {
		return returnData;
	}

	public void setReturnData(Object returnData) {
		this.returnData = returnData;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

}
