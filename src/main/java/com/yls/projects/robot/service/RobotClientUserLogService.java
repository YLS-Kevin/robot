package com.yls.projects.robot.service;

import com.yls.frame.common.web.Result;
import com.yls.projects.robot.entity.RobotClientUseLog;

/**
 * 机器人终端使用日志表 Service
 * 
 * @author 陈俊
 * @date 2018年6月9日
 */
public interface RobotClientUserLogService {

	/**
	 * 日志列表
	 * 
	 * @param robotClientUseLog
	 * @return
	 */
	Result listChatLog(RobotClientUseLog robotClientUseLog);
}
