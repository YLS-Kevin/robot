package com.yls.projects.common.config.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.yls.frame.common.enums.ResultEnum;
import com.yls.frame.common.utils.ResultUtil;
import com.yls.frame.common.utils.StringUtils;
import com.yls.projects.robot.entity.DialogUser;

public class ShiroExpiredFilter extends FormAuthenticationFilter {
	private static Logger logger = LoggerFactory.getLogger(ShiroExpiredFilter.class);

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		if (this.isLoginRequest(request, response)) {
			if (this.isLoginSubmission(request, response)) {
				return this.executeLogin(request, response);
			} else {
				return true;
			}
		} else {
			// 如果是OPTIONS 请求直接放行
			String method = httpServletRequest.getMethod();
			StringBuffer requestUri = httpServletRequest.getRequestURL();
			if ("OPTIONS".equals(method)) {
				return false;
			}

			if (requestUri.toString().toLowerCase().contains("login")
					|| requestUri.toString().toLowerCase().contains("register")
					|| requestUri.toString().toLowerCase().contains("fogetpwd")) {
				return true;
			}
			// String requestedWith =
			// httpServletRequest.getHeader("Access-Control-Request-Headers");
			String requestPostWith = httpServletRequest.getHeader("X-Requested-With");
			// if ((StringUtils.isNotEmpty(requestedWith) ||
			// StringUtils.isNotEmpty(requestPostWith))
			// && (StringUtils.equals(requestedWith, "x-requested-with")
			// || StringUtils.equals(requestPostWith, "XMLHttpRequest"))) {//
			// 如果是ajax返回指定数据
			if (StringUtils.isNotEmpty(requestPostWith) && StringUtils.equals(requestPostWith, "XMLHttpRequest")) {
				onLoginFail(response);
				return false;
			} else {
				return true;
			}
		}
	}

	// session过期给403状态码
	private void onLoginFail(ServletResponse response) throws IOException {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.setCharacterEncoding("UTF-8");
		httpResponse.setContentType("application/json");
		// httpResponse.setStatus(403);
		httpResponse.getWriter().write(JSONObject
				.toJSONString(ResultUtil.error(ResultEnum.LOGIN_EXPIRE.getCode(), ResultEnum.LOGIN_EXPIRE.getInfo())));
	}

}
