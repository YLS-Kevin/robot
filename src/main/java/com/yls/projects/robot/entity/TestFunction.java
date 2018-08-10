package com.yls.projects.robot.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class TestFunction {

	/** 测url */
	@NotNull(message = "url字段必传")
    @NotBlank(message = "url字段必传")
	private String url;
	
	/** 发送type get, post*/
	@NotNull(message = "sendType字段必传")
    @NotBlank(message = "sendType字段必传")
	private String sendType;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
}
