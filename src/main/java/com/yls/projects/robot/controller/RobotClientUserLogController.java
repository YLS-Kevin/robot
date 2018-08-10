package com.yls.projects.robot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yls.frame.common.web.Result;
import com.yls.projects.common.controller.BaseController;
import com.yls.projects.robot.entity.RobotClientUseLog;
import com.yls.projects.robot.service.RobotClientUserLogService;

/**
 * 机器人终端使用日志表 Controller
 * 
 * @author 陈俊
 * @date 2018年6月9日
 */
@RestController
public class RobotClientUserLogController extends BaseController {

	@Autowired
	private RobotClientUserLogService robotClientUserLogService;

	/**
	 * 日志列表
	 * 
	 * @param robotClientUseLog
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping("/listChatLog")
	public Result listChatLog(RobotClientUseLog robotClientUseLog) {
		return robotClientUserLogService.listChatLog(robotClientUseLog);
	}
}
