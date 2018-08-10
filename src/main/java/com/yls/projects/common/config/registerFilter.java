package com.yls.projects.common.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Maps;
import com.yls.projects.common.config.filter.ShiroSessionFilter;

//@Configuration
public class registerFilter {

	@Value("${server.session.timeout}")
	private String serverSessionTimeout;
	
	@Bean
	public FilterRegistrationBean shiroSessionFilterRegistrationBean() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new ShiroSessionFilter());
		filterRegistrationBean.setOrder(FilterRegistrationBean.LOWEST_PRECEDENCE);
		filterRegistrationBean.setEnabled(true);
		filterRegistrationBean.addUrlPatterns("/*");
		Map<String, String> initParameters = Maps.newHashMap();
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~ " + serverSessionTimeout);
		initParameters.put("serverSessionTimeout", serverSessionTimeout);
		initParameters.put("excludes", "/favicon.ico,/img/*,/js/*,/css/*");
		filterRegistrationBean.setInitParameters(initParameters);
		return filterRegistrationBean;
	}
}
