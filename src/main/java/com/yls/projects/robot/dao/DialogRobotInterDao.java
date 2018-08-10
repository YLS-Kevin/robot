package com.yls.projects.robot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yls.projects.robot.entity.DialogRobotInter;

/**
 * 机器接口应答Dao
 * 
 * @author 陈俊
 * @date 2018年5月14日
 */
public interface DialogRobotInterDao {

	/**
	 * 添加接口应答表
	 * 
	 * @param dialogRobotInter
	 */
	void insert(DialogRobotInter dialogRobotInter);

	/**
	 * 更新接口应答表
	 * 
	 * @param dialogRobotInter
	 */
	void update(DialogRobotInter dialogRobotInter);

	/**
	 * 根据人机对话Id查询详情List
	 * 
	 * @param dialogId
	 * @return
	 */
	List<DialogRobotInter> getListByIdD(@Param(value = "dialogId") String dialogId);

}
