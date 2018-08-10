package com.yls.projects.common.config.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.servlet.AdviceFilter;

import com.alibaba.fastjson.JSONObject;
import com.yls.frame.common.enums.ResultEnum;
import com.yls.frame.common.utils.ResultUtil;
import com.yls.frame.common.utils.StringUtils;
import com.yls.projects.robot.entity.DialogUser;

public class ShiroLoginFilter extends AdviceFilter {

    /**
     * 在访问controller前判断是否登录，返回json，不进行重定向。
     * @param request
     * @param response
     * @return true-继续往下执行，false-该filter过滤器已经处理，不继续执行其他过滤器
     * @throws Exception
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        DialogUser sysUser = (DialogUser) httpServletRequest.getSession().getAttribute("dialogUser");
        
        System.out.println(httpServletRequest.getHeader("Request Method"));
        
        if (null == sysUser && !StringUtils.contains(httpServletRequest.getRequestURI(), "/loginByPhone")) {
            String requestedWith = httpServletRequest.getHeader("Access-Control-Request-Headers");
            String requestPostWith = httpServletRequest.getHeader("X-Requested-With");
            if ((StringUtils.isNotEmpty(requestedWith) || StringUtils.isNotEmpty(requestPostWith)) && (StringUtils.equals(requestedWith, "x-requested-with") || StringUtils.equals(requestPostWith, "XMLHttpRequest"))) {//如果是ajax返回指定数据
                httpServletResponse.setCharacterEncoding("UTF-8");
                httpServletResponse.setContentType("application/json");
                httpServletResponse.getWriter().write(JSONObject.toJSONString(ResultUtil.error(ResultEnum.LOGIN_EXPIRE.getCode(), ResultEnum.LOGIN_EXPIRE.getInfo())));
                return false;
            } else {//不是ajax进行重定向处理
                httpServletResponse.sendRedirect("/loginPage");
                return false;
            }
        }
        return true;
    }

}
