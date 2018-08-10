package com.yls.projects.robot.dao;

import java.util.List;
import java.util.Map;

import com.yls.projects.robot.entity.Robots;
import com.yls.projects.robot.vo.RobotsVo;

/**
 * 机器人(应用)表 Dao
 * 
 * @author 陈俊
 * @date 2018年5月21日
 */
public interface RobotsDao {

	/**
	 * 添加机器人(应用)表
	 * 
	 * @param dialog
	 */
	void insert(Robots robots);

	/**
	 * 更新机器人(应用)表
	 * 
	 * @param dialog
	 */
	void update(Robots robots);

	/**
	 * 根据账号ID查询列表
	 * 
	 * @param idAc
	 * @return
	 */
	List<Map<String, Object>> getListByIdAc(RobotsVo robotsVo);

	/**
	 * 获取总数
	 * 
	 * @param robotsVo
	 * @return
	 */
	Integer getCount(RobotsVo robotsVo);
	
	/**
	 * 获取单个机器人信息
	 * 
	 * @param robotsVo
	 * @return
	 */
	Robots getEntityById(RobotsVo robotsVo);
	
	/**
	 * 通过模块iD获取单个机器人信息
	 * 
	 * @param robotsVo
	 * @return
	 */
	Robots getEntityByIdM(RobotsVo robotsVo);
	
	/**
	 * 删除机器人(应用)表
	 * 
	 * @param robots
	 */
	void delRobotById(Robots robots);
	
	/**
	 * 根据机器名称查询数量
	 * 
	 * @param robotsVo
	 * @return
	 */
	Integer getCountByRobotName(Robots robots);
}
