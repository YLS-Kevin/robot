package com.yls.projects.robot.service;

import com.yls.frame.common.web.Result;
import com.yls.projects.robot.entity.Robots;
import com.yls.projects.robot.vo.RobotsVo;

/**
 * 机器人Service
 * 
 * @author 陈俊
 * @date 2018年5月17日
 */
public interface RobotsService {

	/**
	 * 创建机器人
	 * 
	 * @param robots
	 * @return
	 */
	Result createdRobot(Robots robots);

	/**
	 * 修改机器人(应用)表
	 * 
	 * @param robots
	 * @return
	 */
	Result modifyRobot(Robots robots);

	/**
	 * 获取机器人列表
	 * 
	 * @param robots
	 * @return
	 */
	Result listRobot(RobotsVo robotsVo);
	
	/**
	 * 配置机器人
	 * @param robotsVo
	 * @return
	 */
	Result configRobot(RobotsVo robotsVo);

	/**
	 * 聊天日志
	 * @param robotsVo
	 * @return
	 */
	Result selectMulSkill(RobotsVo robotsVo);

	/**
	 * 查询机器人模块 信息
	 * @param robotsVo
	 * @return
	 */
	Result getRobotInfoByIdAndMid(RobotsVo robotsVo);
	
	/**
	 * 删除机器人情景模块信息
	 * @param robotsVo
	 * @return
	 */
	Result delRobotModelById(RobotsVo robotsVo);
	
	/**
	 * 新增机器人情景模块信息
	 * @param robotsVo
	 * @return
	 */
	Result addRobotModel(RobotsVo robotsVo);
	
	/**
	 * 查出共享的能力并且自己账户的全部能力
	 * @param robotsVo
	 * @return
	 */
	Result listShareAbility(RobotsVo robotsVo);
	
	/**
	 * 修改机器人情景模块信息
	 * @param robotsVo
	 * @return
	 */
	Result modifyRobotModelById(RobotsVo robotsVo);
	
	/**
	 * 获取机器人情景模块信息名称与触发关键词
	 * @param robotsVo
	 * @return
	 */
	Result getRobotModelById(RobotsVo robotsVo);
	
	/**
	 * 删除机器人
	 * 
	 * @param robots
	 * @return
	 */
	Result delRobotById(Robots robots);
}
