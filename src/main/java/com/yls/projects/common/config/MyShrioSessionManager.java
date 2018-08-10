package com.yls.projects.common.config;

import java.io.Serializable;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yls.frame.common.utils.StringUtils;

/**
 * 
 * Shrio SessionManager
 * 
 * 为了实现前后端分离session问题,重写Shiro SessionManager获取SessionId的方法
 * 
 * @author Alex 李璐
 * @version
 * @since 2018年5月25日
 */
public class MyShrioSessionManager extends DefaultWebSessionManager {

	private static final Logger logger = LoggerFactory.getLogger(DefaultWebSessionManager.class);
	private static final String AUTHORIZATION = "Authorization";
	private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";

	/**
	 * 重写获取sessionId的方法调用当前Manager的获取方法
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@Override
	protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
		return this.getReferencedSessionId(request, response);
	}

	/**
	 * 获取sessionId从请求中
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	private Serializable getReferencedSessionId(ServletRequest request, ServletResponse response) {
		String id = WebUtils.toHttp(request).getHeader(AUTHORIZATION);
		// 如果请求头中有 Authorization 则其值为sessionId
		if (!StringUtils.isEmpty(id)) {
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
			return id;
		} else {
			// 否则按默认规则从cookie取sessionId
			return super.getSessionId(request, response);
		}
	}
}
