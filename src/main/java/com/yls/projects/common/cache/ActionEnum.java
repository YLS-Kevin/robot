package com.yls.projects.common.cache;

/**
 * 操作枚举类
 * 
 * @author Alex Lee 李璐
 * @date 2018年5月3日下午5:16:00
 */
public enum ActionEnum {

	ADD_DIALOG("1", "增加对话"),
	UPDATE_DIALOG("2", "修改对话"),
	DELETE_DIALOG("3", "删除对话"),
	
	ADD_DIALOG_TYPE("4", "为机器人添加能力"),
	UPDATE_DIALOG_TYPE("5", "为机器人选择能力"),
	
	ADD_DIALOG_EXP("6", "新增配置异常回答"),
	UPDATE_DIALOG_EXP("7", "更新配置异常回答"),
	
	ADD_DIALOG_MODEL("8", "添加情景模块时调用"),
	DELETE_DIALOG_MODEL("9", "删除情景模块时调用"),
	
	UPDATE_INTERFACE("10", "更新我的数据接口");
	
	

	private String code;

	private String info;

	ActionEnum(String code, String info) {
		this.code = code;
		this.info = info;
	}

	public String getCode() {
		return code;
	}

	public String getInfo() {
		return info;
	}

}
