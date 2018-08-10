package com.yls.projects.robot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yls.projects.robot.entity.DialogRobotStatic;

/**
 * 
 * 机器固定应答
 * 
 * @author 陈华湛
 * @date 2018年5月7日下午5:36:33
 */
public interface DialogRobotStaticDao {

	/**
	 * 插入数据
	 * 
	 * @param dialogRobotStatic
	 */
	void insert(DialogRobotStatic dialogRobotStatic);

	/**
	 * 更新数据
	 * 
	 * @param dialogRobotStatic
	 */
	void update(DialogRobotStatic dialogRobotStatic);

	/**
	 * 根据对话id获取对应数据
	 * 
	 * @param dialogId
	 * @return
	 */
	List<DialogRobotStatic> getDialogRobotStaticByIdD(String dialogId);

	/**
	 * 删除固定应答的记录通过对话id
	 * 
	 * @param dialogId
	 * @return
	 */
	void deleteByDialogId(@Param(value = "dialogId") String dialogId);

}
