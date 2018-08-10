package com.yls.projects.robot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yls.projects.common.controller.BaseController;

/**
 * 
 * 首页Controller
 * @author 陈华湛
 * @date 2018年5月3日下午4:53:47
 */
@Controller
@RequestMapping("/index")
public class IndexController extends BaseController{

	/**
	 * 返回首页
	 * @return
	 */
	@RequestMapping("")
	public ModelAndView index(){
		ModelAndView modelAndView = new ModelAndView("/index");
		return modelAndView;
	}
}
