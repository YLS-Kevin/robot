package com.yls.frame.common.Exception;

import com.yls.frame.common.enums.ResultEnum;

/**
 * 父自定义异常类
 * 
 * @author Alex Lee 李璐
 * @date 2018年5月3日下午4:49:00
 */
public class RobotException extends RuntimeException {

	/** 错误码. */
	private String code;

	public RobotException(ResultEnum resultEnum) {
		super(resultEnum.getInfo());
		this.code = resultEnum.getCode();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
