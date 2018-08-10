package com.yls.projects.robot.dao;

import java.util.List;
import java.util.Map;

import com.yls.projects.robot.entity.RobotClientUseLog;

/**
 * 机器人终端使用日志表 Dao
 * 
 * @author 陈俊
 * @date 2018年6月9日
 */
public interface RobotClientUseLogDao {

	/**
	 * 根据机器人终端id查询信息
	 * 
	 * @param robotClientUseLog
	 * @return
	 */
	List<Map<String, Object>> getListByIdCu(RobotClientUseLog robotClientUseLog);

	/**
	 * 获取总数
	 * 
	 * @param robotClientUseLog
	 * @return
	 */
	Integer getCount(RobotClientUseLog robotClientUseLog);
}
