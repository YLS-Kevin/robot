package com.yls.projects.common.config.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * 跨域过滤器
 * 
 * @author Alex 李璐
 * @version
 * @since 2018年5月21日
 */
@Component
public class CorsFilter implements Filter {

	private static Logger logger = LoggerFactory.getLogger(CorsFilter.class);

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;

		String[] allowDomain = {"http://127.0.0.1:8020", "http://127.0.0.1:8080", "http://localhost:8080"};
		Set allowedOrigins = new HashSet(Arrays.asList(allowDomain));
		String originHeader = ((HttpServletRequest) req).getHeader("Origin");
		//logger.info(originHeader);
		if (allowedOrigins.contains(originHeader) || originHeader == null) {
			response.setHeader("Access-Control-Allow-Credentials", "true");
			response.setHeader("Access-Control-Allow-Origin", originHeader);
			response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
			response.setHeader("Access-Control-Max-Age", "3600");
			response.setHeader("Access-Control-Allow-Headers",
					"Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With, requestr");
			// sresponse.setHeader("Access-Control-Allow-Headers", "*");
			// logger.info("*********************************过滤器被使用**************************");
			chain.doFilter(req, res);
		}
	}

	public void init(FilterConfig filterConfig) {
	}

	public void destroy() {
	}
}