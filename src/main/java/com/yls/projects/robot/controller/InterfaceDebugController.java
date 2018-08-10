package com.yls.projects.robot.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yls.frame.common.web.Result;
import com.yls.projects.common.controller.BaseController;
import com.yls.projects.robot.service.InterfaceDebugService;
import com.yls.projects.robot.vo.InterfaceDebugVo;

/**
 * 
 * 对话接口调试
 * @author 陈华湛
 * @date 2018年6月15日下午6:20:01
 */
@Controller
public class InterfaceDebugController extends BaseController{
	
	@Autowired
	private InterfaceDebugService interfaceDebugService;
	
	@RequestMapping("/interfaceDebug")
	@ResponseBody
	public Result InterfaceDebug(HttpServletRequest request,InterfaceDebugVo interfaceDebugVo){
		return interfaceDebugService.InterfaceDebug(request,interfaceDebugVo);
	}
	
}
