package com.yls.projects.robot.service;

import javax.servlet.http.HttpServletRequest;

import com.yls.frame.common.web.Result;
import com.yls.projects.robot.vo.InterfaceDebugVo;

public interface InterfaceDebugService {

	Result InterfaceDebug(HttpServletRequest request,InterfaceDebugVo interfaceDebugVo);
}
