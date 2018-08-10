package com.yls.projects.common.config.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;

import com.yls.frame.common.utils.StringUtils;

public class ShiroLogoutFilter extends LogoutFilter {

	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		// 在这里执行退出系统前需要清空的数据
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		Subject subject = getSubject(request, response);
		String redirectUrl = getRedirectUrl(request, response, subject);
		// 如果是OPTIONS 请求直接放行
		String method = httpServletRequest.getMethod();
		StringBuffer requestUri = httpServletRequest.getRequestURL();
		if ("OPTIONS".equals(method)) {
			return true;
		}
		if (requestUri.toString().toLowerCase().contains("logout")) {
			String requestPostWith = httpServletRequest.getHeader("X-Requested-With");
			if (StringUtils.isNotEmpty(requestPostWith) && StringUtils.equals(requestPostWith, "XMLHttpRequest")) {
				try {
					subject.logout();
				} catch (SessionException ise) {
					ise.printStackTrace();
				}
				return false;
			} else {
				issueRedirect(request, response, redirectUrl);
				return false;
			}
		} else {
			return true;
		}
	}
}
