package com.yls.projects.robot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yls.frame.common.web.Result;
import com.yls.projects.common.controller.BaseController;
import com.yls.projects.robot.entity.Robots;
import com.yls.projects.robot.service.RobotsService;
import com.yls.projects.robot.vo.RobotsVo;

/**
 * 机器人Controller
 * 
 * @author 陈俊
 * @date 2018年5月21日
 */
@RestController
public class RobotsController extends BaseController{

	@Autowired
	private RobotsService robotsService;

	/**
	 * 创建机器人
	 * 
	 * @param robots
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/createdRobot")
	public Result createdRobot(Robots robots) {
		return robotsService.createdRobot(robots);
	}

	/**
	 * 修改机器人
	 * 
	 * @param robots
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/modifyRobot")
	public Result modifyRobot(Robots robots) {
		return robotsService.modifyRobot(robots);
	}

	/**
	 * 获取机器人列表
	 * 
	 * @param robots
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/listRobot")
	public Result listRobot(RobotsVo robotsVo) {
		return robotsService.listRobot(robotsVo);
	}
	
	/**
	 * 配置机器人
	 * @param robotsVo
	 * @return
	 */
	@RequestMapping("/configRobot")
	public Result configRobot(RobotsVo robotsVo){
		return robotsService.configRobot(robotsVo);
	}
	
	/**
	 * 为机器人选择技能
	 * @param robotsVo
	 * @return
	 */
	@RequestMapping("/selectMulSkill")
	public Result selectMulSkill(RobotsVo robotsVo){
		return robotsService.selectMulSkill(robotsVo);
	}
	
	/**
	 * 查询机器人模块 信息
	 * @param robotsVo
	 * @return
	 */
	@GetMapping("/getRobotInfoByIdAndMid")
	public Result getRobotInfoByIdAndMid(RobotsVo robotsVo){
		return robotsService.getRobotInfoByIdAndMid(robotsVo);
	}
	
	/**
	 * 删除机器人情景模块信息
	 * @param robotsVo
	 * @return
	 */
	@PostMapping("/delRobotModelById")
	public Result delRobotModelById(RobotsVo robotsVo){
		return robotsService.delRobotModelById(robotsVo);
	}
	
	/**
	 * 增加机器人情景模块信息
	 * @param robotsVo
	 * @return
	 */
	@PostMapping("/addRobotModel")
	public Result addRobotModel(RobotsVo robotsVo){
		return robotsService.addRobotModel(robotsVo);
	}
	
	/**
	 * 增加机器人情景模块信息
	 * @param robotsVo
	 * @return
	 */
	@GetMapping("/listShareAbility")
	public Result listShareAbility(RobotsVo robotsVo){
		return robotsService.listShareAbility(robotsVo);
	}
	
	/**
	 * 修改机器人情景模块信息
	 * @param robotsVo
	 * @return
	 */
	@PostMapping("/modifyRobotModelById")
	public Result modifyRobotModelById(RobotsVo robotsVo){
		return robotsService.modifyRobotModelById(robotsVo);
	}
	
	/**
	 * 获取机器人情景模块信息名称与触发关键词
	 * @param robotsVo
	 * @return
	 */
	@GetMapping("/getRobotModelById")
	public Result getRobotModelById(RobotsVo robotsVo){
		return robotsService.getRobotModelById(robotsVo);
	}
	
	/**
	 * 创建机器人
	 * 
	 * @param robots
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/delRobotById")
	public Result delRobotById(Robots robots) {
		return robotsService.delRobotById(robots);
	}
}
