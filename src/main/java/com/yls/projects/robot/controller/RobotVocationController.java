package com.yls.projects.robot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yls.frame.common.web.Result;
import com.yls.projects.common.controller.BaseController;
import com.yls.projects.robot.service.RobotVocationService;

/**
 * 
 * 行业Controller
 * @author 陈华湛
 * @date 2018年5月23日下午6:21:04
 */
@RestController
public class RobotVocationController extends BaseController{
	
	@Autowired
	private RobotVocationService robotVocationService;

	/**
	 * 查出全部行情
	 * @return
	 */
	@RequestMapping("/findAllVocation")
	public Result findAllVocation(){
		return robotVocationService.findAllVocation();
	}
}
