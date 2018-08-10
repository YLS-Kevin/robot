package com.yls.projects.common.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.alibaba.fastjson.JSONObject;
import com.yls.frame.common.enums.ResultEnum;
import com.yls.frame.common.utils.ResultUtil;
import com.yls.frame.common.utils.Utils;
import com.yls.frame.common.web.Result;

/**
 * BaseController
 *
 * @author Alex
 * @create 2018年5月31日
 */
public abstract class BaseController {

    /**
     * 登录认证异常
     */
    @ExceptionHandler({ UnauthenticatedException.class })
    public String authenticationException(HttpServletRequest request, HttpServletResponse response) {
        if (Utils.isAjaxRequest(request)) {
            // 输出JSON
        	writeJson(ResultUtil.error(ResultEnum.LOGIN_EXPIRE.getCode(), ResultEnum.LOGIN_EXPIRE.getInfo()), response);
            return null;
        } else {
            return "redirect:/system/login";
        }
    }

    /**
     * 权限异常
     */
    @ExceptionHandler({ UnauthorizedException.class, AuthorizationException.class })
    public String authorizationException(HttpServletRequest request, HttpServletResponse response) {
        if (Utils.isAjaxRequest(request)) {
            // 输出JSON
            writeJson(ResultUtil.error(ResultEnum.NO_AUTHORITY.getCode(), ResultEnum.NO_AUTHORITY.getInfo()), response);
            return null;
        } else {
            return "redirect:/system/403";
        }
    }

    /**
     * 输出JSON
     *
     * @param response
     * @author Alex
     * @create 2018年5月31日
     */
    private void writeJson(Result result, HttpServletResponse response) {
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            out = response.getWriter();
            out.write(JSONObject.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}