package com.yls.frame.common.enums;

/**
 * 返回结果枚举类
 * 
 * @author Alex Lee 李璐
 * @date 2018年5月3日下午5:16:00
 */
public enum ResultEnum {

	UNKONW_ERROR("-1", "未知错误"), SUCCESS("0", "成功"), FAIL("1", "失败"), VALID_FAIL("3", "验证码错误"), VALID_EXPIRED("4", "验证码过期"), NO_AUTHORITY("20001",
			"对不起您没有权限访问该请求,请联系管理员!"), LOGIN_FAIL("20002", "用户名或密码不正确！"), LOGIN_EXPIRE("20003", "登录超时,请重新登录!"),

	PARAMETER_INCOMPLETE_ERROR("10001", "请求参数:参数不完整或参数不能为空!"), PARAMETER_IS_NULL_ERROR("10002",
			"请求参数:参数不能为空！"), PARAMETER_PRIMARYKEY_ERROR("10003", "请求参数:实体主键不能为空"),

	PAGING_SIZE_IS_NULL_ERROR("10003", "请求参数:分页参数不能为空"), PAGING_SIZE_IS_LESSTHAN0_ERROR("10004",
			"请求参数:分页参数必须大于0"), SESSION_INVALIDATION_ERROR("10005", "未登录或会话失效，无法获取用户信息！"), WORDS_FAIL("10001",
					"请查看该词是否已经存在!"), FILE_ERROR("10001", "文件上传失败!"), WORDS_GOUNP_FAIL("10001",
							"请先删除该词组下的动态词!"), USER_FAIL("10001",
									"该用户是超级用户，不能删除!"), USER_TELPHONE_ERROR("20001", "该号码已注册！"),

	EXISTS_DIALOG_ERROR("30001", "请先删除该实体下的问答信息!"), EXISTS_SAME_DIALOG_MODEL("30002", "已存在同名实体!"), EXISTS_SAME_DWORD(
			"30003", "已存在同名英文名!"), EXISTS_SAME_DCN_WORD("30004", "已存在同名中文名!"), ENTITY_ALREADY_USE("30005",
					"该能力已经被机器人所引用!"), WORD_ALREADY_EXISTS("30006", "该词已存在与其它动态词!"), ROBOT_ALREADY_EXISTS("30007",
							"已存在同名机器人!"), INTERDATA_ALREADY_EXISTS("30008", "已存在同名数据接口!"), ABILITY_ALREADY_EXISTS(
									"30009", "该能力已存在!"), THEME_ALREADY_EXISTS("30009", "该主题已存在!"), USER_NOT_FIND("30010", "未找到该手机号!");

	private String code;

	private String info;

	ResultEnum(String code, String info) {
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
